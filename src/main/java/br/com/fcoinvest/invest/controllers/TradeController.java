package br.com.fcoinvest.invest.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fcoinvest.invest.bo.TradeBO;
import br.com.fcoinvest.invest.dtos.FormTradeAddDTO;
import br.com.fcoinvest.invest.dtos.PortifolioAtivoDTO;
import br.com.fcoinvest.invest.dtos.PortifolioCarteiraDTO;
import br.com.fcoinvest.invest.dtos.PortifolioLucrosEPerdasDTO;
import br.com.fcoinvest.invest.dtos.TradeDTO;
import br.com.fcoinvest.invest.dtos.UsuarioDTO;
import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.enums.TipoOperacao;
import br.com.fcoinvest.invest.models.Cotacao;
import br.com.fcoinvest.invest.models.Trade;
import br.com.fcoinvest.invest.models.Usuario;
import br.com.fcoinvest.invest.services.CotacaoService;
import br.com.fcoinvest.invest.services.TradeService;
import br.com.fcoinvest.invest.services.UsuarioService;
import br.com.fcoinvest.invest.utils.Utils;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -7121174799086602706L;

	@Autowired
	private final UsuarioService usuarioService;
	
	@Autowired
	private final CotacaoService cotacaoService;
	
	@Autowired
	private final TradeService tradeService;
	
	
	@GetMapping("/testeTrades")
	public ResponseEntity<List<TradeDTO>> teste() {
		return ResponseEntity.ok().body(tradeService.getAllByUsuarioEAtivo(Ativo.VVAR3F, 1L));
	}
	
	@GetMapping("/testePortifolioQtd")
	public int testePortifolioQtd() {
		return tradeService.portifolioQuantidade(Ativo.ITUB4F, tradeService.getAllByUsuarioEAtivo(Ativo.ITUB4F, 1L));
	}
	
	@GetMapping("/testePortifolioRentabilidade")
	public Double testePortifolioRentabilidade() {
		return tradeService.portifolioRentabilidade(Ativo.ITUB4F, tradeService.getAllByUsuarioEAtivo(Ativo.ITUB4F, 1L));
	}
	
	@GetMapping
	public ResponseEntity<FormTradeAddDTO> index() {
		FormTradeAddDTO form = new FormTradeAddDTO();
		form.setUsuarios(usuarioService.getAll());
		form.setCotacoes(cotacaoService.getAll());
		List<String> anos = new ArrayList<String>(List.of("2017", "2018", "2019", "2020"));
		form.setAnos(anos);
		List<String> meses = new ArrayList<String>(List.of("01","02","03","04","05","06","07","08","09","10","11","12"));
		form.setMeses(meses);
		return ResponseEntity.ok().body(form);
	}
	
	@PostMapping("/novo")
	public ResponseEntity<TradeDTO> novoTrade(@RequestBody @Valid TradeDTO trade) {
		Usuario u = usuarioService.getById(trade.getUsuario().getId());
		Cotacao c = cotacaoService.getById(trade.getCotacao().getId());
		TipoOperacao t = TipoOperacao.valueOf(trade.getTipoOperacao().toString());
		Ativo a = Ativo.valueOf(trade.getAtivo().toString());
		Date d = trade.getCotacao().getData();
		int qtd = trade.getQtd();
		Double valorAtivo = trade.getCotacao().getValor();
		Double total = qtd*valorAtivo;
		
		Trade tradeEntity = new Trade(null, u, d, t, a, qtd, c, total);
		tradeService.salvar(tradeEntity);
		trade = TradeDTO.convert(tradeEntity);
		return ResponseEntity.ok().body(trade);
	}
	
	@GetMapping("/novo-aleatorio")
	public ResponseEntity<TradeDTO> aleatorioNovoTrade() {
		Trade tradeEntity = new Trade();
		
		List<Usuario> listUsuarios = usuarioService.getAllEntities();
		List<Cotacao> listCotacoes = cotacaoService.getAllEntities();
		
		Random r = new Random();
		
		tradeEntity.setUsuario(listUsuarios.get(r.nextInt(0, listUsuarios.size())));
		tradeEntity.setCotacao(listCotacoes.get(r.nextInt(0, listCotacoes.size())));
		tradeEntity.setAtivo(tradeEntity.getCotacao().getAtivo());
		tradeEntity.setData(tradeEntity.getCotacao().getData());
		tradeEntity.setQnt(r.nextInt(1,11));
		tradeEntity.setTotal(tradeEntity.getQnt()*tradeEntity.getCotacao().getValor());
		
		//se a quantidade for maior do que a que já existe na carteira, a operação será de obrigatoriamente de compra.
		List<TradeDTO> listTrades = tradeService.getAllByUsuarioEAtivo
				(tradeEntity.getAtivo(), tradeEntity.getUsuario().getId());
		
		int qtd = tradeService.portifolioQuantidade(
				tradeEntity.getAtivo(), listTrades);
		
		if (qtd<tradeEntity.getQnt()) {
			tradeEntity.setOperacao(TipoOperacao.COMPRA);
		}else {
			tradeEntity.setOperacao(TipoOperacao.random());
		}
		//
		
		tradeService.salvar(tradeEntity);
		TradeDTO trade = TradeDTO.convert(tradeEntity);
		return ResponseEntity.ok().body(trade);
	}
	
	@GetMapping("/novo-aleatorio/{qtd}")
	public ResponseEntity<List<TradeDTO>> aleatoriosNovoTrade(@PathVariable int qtd) {
		
		List<TradeDTO> listTrades = new ArrayList<TradeDTO>();
		
		if (qtd > 0) {
			for (int i = 0; i < qtd; i++) {
				Trade tradeEntity = new Trade();
				List<Usuario> listUsuarios = usuarioService.getAllEntities();
				List<Cotacao> listCotacoes = cotacaoService.getAllEntities();
				Random r = new Random();
				tradeEntity.setUsuario(listUsuarios.get(r.nextInt(0, listUsuarios.size())));
				tradeEntity.setCotacao(listCotacoes.get(r.nextInt(0, listCotacoes.size())));
				tradeEntity.setAtivo(tradeEntity.getCotacao().getAtivo());
				tradeEntity.setData(tradeEntity.getCotacao().getData());
				tradeEntity.setQnt(r.nextInt(1,11));
				tradeEntity.setTotal(tradeEntity.getQnt()*tradeEntity.getCotacao().getValor());
		
				//se a quantidade for maior do que a que já existe na carteira, a operação será de obrigatoriamente de compra.
				List<TradeDTO> listTradesAux = tradeService.getAllByUsuarioEAtivo
						(tradeEntity.getAtivo(), tradeEntity.getUsuario().getId());
				
				int qtd_aux = tradeService.portifolioQuantidade(
						tradeEntity.getAtivo(), listTradesAux);
				
				if (qtd_aux<tradeEntity.getQnt()) {
					tradeEntity.setOperacao(TipoOperacao.COMPRA);
				}else {
					tradeEntity.setOperacao(TipoOperacao.random());
				}
				//
				
				tradeService.salvar(tradeEntity);
				TradeDTO trade = TradeDTO.convert(tradeEntity);
				
				listTrades.add(trade);
			}
		}	
		return ResponseEntity.ok().body(listTrades);
	}
	
	@GetMapping("/rendimento/{idPessoa}/{dia}/{ativo}")
	public ResponseEntity<PortifolioAtivoDTO> rendimentoNoDia(
			@PathVariable Long idPessoa, 
			@PathVariable("dia") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia, 
			@PathVariable String ativo) {
		
		List<TradeDTO> trades = tradeService.getAllByDataBeforeAndUsuarioAndAtivo(Utils.addDias(dia, 1), Ativo.valueOf(ativo), idPessoa);
		return ResponseEntity.ok().body(calculaPortifolio(trades, idPessoa, ativo, null, dia));
	}
	
	@GetMapping("/rendimento-total/{idPessoa}/{dia}")
	public ResponseEntity<PortifolioCarteiraDTO> rendimentoTotalNoDia(
			@PathVariable Long idPessoa, 
			@PathVariable("dia") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia) {
		
		
		PortifolioCarteiraDTO carteira = new PortifolioCarteiraDTO(); 
		List<PortifolioAtivoDTO> list = new ArrayList<PortifolioAtivoDTO>();
		List<TradeDTO> trades = new ArrayList<TradeDTO>();
		
		trades = tradeService.getAllByDataBeforeAndUsuario(Utils.addDias(dia, 1), idPessoa);
		
		if (trades == null || trades.isEmpty()) {
			carteira.setMsgErro("Trades não encontrados.");
			return ResponseEntity.ok().body(carteira);
		}
		
		Double rendTotal = 0.0;
		List<TradeDTO> list_aux = trades;
		for (Ativo a : Ativo.values()) {
			List<TradeDTO> l_aux = TradeBO.filtraPorUsuariosEAtivo(list_aux, idPessoa, a.toString());
			PortifolioAtivoDTO p = calculaPortifolio(l_aux, idPessoa, a.toString(), null, dia);
			list.add(p);
			if (p.getRendimento() != null) {
				rendTotal = rendTotal + p.getRendimento();
			}
			
		}
		Double renTotalPorc = rendTotal/tradeService.patrimonioLiquidoAtual(list_aux);
		
		carteira.setDataFim(dia);
		carteira.setPortAtivo(list);
		carteira.setRendTotal(rendTotal);
		carteira.setRendTotalMask(Utils.toRealMask(rendTotal));
		carteira.setRendTotalPorc(renTotalPorc);
		carteira.setRendTotalPorcMask(Utils.toPorcentMask(renTotalPorc));
		return ResponseEntity.ok().body(carteira);
		
	}
	
	@GetMapping("/rendimento-total-por-periodo/{idPessoa}/{dataInicio}/{dataFim}")
	public ResponseEntity<PortifolioCarteiraDTO> rendimentoTotalPorPeriodo(
			@PathVariable Long idPessoa, 
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim) {
		
		
		PortifolioCarteiraDTO carteira = new PortifolioCarteiraDTO(); 
		List<PortifolioAtivoDTO> list = new ArrayList<PortifolioAtivoDTO>();
		List<TradeDTO> trades = new ArrayList<TradeDTO>();
		
		trades = tradeService.getAllByDataBetwween(Utils.addDias(dataInicio, -1),Utils.addDias(dataFim, 1), idPessoa);
		
		if (trades == null || trades.isEmpty()) {
			carteira.setMsgErro("Trades não encontrados.");
			return ResponseEntity.ok().body(carteira);
		}
		
		Double rendTotal = 0.0;
		List<TradeDTO> list_aux = trades;
		for (Ativo a : Ativo.values()) {
			List<TradeDTO> l_aux = TradeBO.filtraPorUsuariosEAtivo(list_aux, idPessoa, a.toString());
			PortifolioAtivoDTO p = calculaPortifolio(l_aux, idPessoa, a.toString(),dataInicio, dataFim);
			list.add(p);
			if (p.getRendimento() != null) {
				rendTotal = rendTotal + p.getRendimento();
			}
			
		}
		Double renTotalPorc = rendTotal/tradeService.patrimonioLiquidoAtual(list_aux);
		
		carteira.setDataInicio(dataInicio);
		carteira.setDataFim(dataFim);
		carteira.setPortAtivo(list);
		carteira.setRendTotal(rendTotal);
		carteira.setRendTotalMask(Utils.toRealMask(rendTotal));
		carteira.setRendTotalPorc(renTotalPorc);
		carteira.setRendTotalPorcMask(Utils.toPorcentMask(renTotalPorc));
		return ResponseEntity.ok().body(carteira);
		
	}
	
	@GetMapping("/rendimento-total-por-periodo-detalhado/{idPessoa}/{dataInicio}/{dataFim}")
	public ResponseEntity<PortifolioLucrosEPerdasDTO> rendimentoTotalPorPeriodoDetalhado(
			@PathVariable Long idPessoa, 
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim) {
		
		PortifolioLucrosEPerdasDTO carteiraDetalhada = new PortifolioLucrosEPerdasDTO();
		
//		PortifolioCarteiraDTO carteira = new PortifolioCarteiraDTO(); 
		List<PortifolioAtivoDTO> listLucro = new ArrayList<PortifolioAtivoDTO>();
		List<PortifolioAtivoDTO> listPerda = new ArrayList<PortifolioAtivoDTO>();
		List<TradeDTO> trades = new ArrayList<TradeDTO>();
		
		trades = tradeService.getAllByDataBetwween(Utils.addDias(dataInicio, -1),Utils.addDias(dataFim, 1), idPessoa);
		
		if (trades == null || trades.isEmpty()) {
			carteiraDetalhada.setMsgErro("Trades não encontrados.");
			return ResponseEntity.ok().body(carteiraDetalhada);
		}
		
		Double rendTotal = 0.0;
		List<TradeDTO> list_aux = trades;
		Double patrimonioTotal = tradeService.patrimonioLiquidoAtual(list_aux);
		carteiraDetalhada.setPatrimonio(new HashMap<String, String>());
		Double total_lucro = 0.0;
		Double total_perda = 0.0;
		for (Ativo a : Ativo.values()) {
			List<TradeDTO> l_aux = TradeBO.filtraPorUsuariosEAtivo(list_aux, idPessoa, a.toString());
			PortifolioAtivoDTO p = calculaPortifolio(l_aux, idPessoa, a.toString(),dataInicio, dataFim);
			Double r_aux = p.getRendimento();
			if (r_aux != null) {
				rendTotal = rendTotal + p.getRendimento();
				Double pat_acao = tradeService.patrimonioLiquidoAtual(l_aux);
				Double pat_carteira_porc = pat_acao/patrimonioTotal;
				carteiraDetalhada.getPatrimonio().put(a.toString(), Utils.toPorcentMask(pat_carteira_porc));
				if(r_aux>0) {
					total_lucro = total_lucro + r_aux;
					listLucro.add(p);
				}else {
					total_perda = total_perda - Math.abs(r_aux);
					listPerda.add(p);
				}
			}
			
		}
		Double renTotalPorc = rendTotal/patrimonioTotal;
		
		carteiraDetalhada.setDataInicio(dataInicio);
		carteiraDetalhada.setDataFim(dataFim);
		carteiraDetalhada.setAtivosLucro(listLucro);
		carteiraDetalhada.setAtivosPerda(listPerda);
		carteiraDetalhada.setTotalLucro(total_lucro);
		carteiraDetalhada.setTotalLucroMask(Utils.toRealMask(total_lucro));
		carteiraDetalhada.setTotalPerda(total_perda);
		carteiraDetalhada.setTotalPerdaMask(Utils.toRealMask(total_perda));
		//carteiraDetalhada.setPortAtivo(list);
		carteiraDetalhada.setRendTotal(rendTotal);
		carteiraDetalhada.setRendTotalMask(Utils.toRealMask(rendTotal));
		carteiraDetalhada.setRendTotalPorc(renTotalPorc);
		carteiraDetalhada.setRendTotalPorcMask(Utils.toPorcentMask(renTotalPorc));
		return ResponseEntity.ok().body(carteiraDetalhada);
		
	}
	
	private PortifolioAtivoDTO calculaPortifolio(List<TradeDTO> trades, Long idUsuario, String ativo, Date inicio, Date fim) {
		
		PortifolioAtivoDTO portifolio = new PortifolioAtivoDTO();
		if (trades != null && !trades.isEmpty()) {
			portifolio.setUsuario(UsuarioDTO.convert(usuarioService.getById(idUsuario)));
			portifolio.setAtivo(ativo);
			portifolio.setDataInicio(inicio);
			portifolio.setDataFim(fim);
			portifolio.setQnt(tradeService.patrimonioQtd(trades));
			Double i = tradeService.investimento(trades);
			Double r = tradeService.rendimentoLiquido(trades, Utils.addDias(fim, 1));
			Double rp = tradeService.rendimentoPorcento(trades, Utils.addDias(fim, 1));
			portifolio.setInvestimentoTotal(i);
			portifolio.setInvestimentoTotalMask(Utils.toRealMask(i));
			portifolio.setRendimento(r);
			portifolio.setRendimentoMask(Utils.toRealMask(r));
			portifolio.setRendimentoPorcentagem(rp);
			portifolio.setRendimentoPorcentagemMask(Utils.toPorcentMask(rp));
		}else {
			portifolio.setMsgErro("Não foram encontrados trades para o cálculo de rendimentos.");
		}
	
		return portifolio;
	}
	
	
}
