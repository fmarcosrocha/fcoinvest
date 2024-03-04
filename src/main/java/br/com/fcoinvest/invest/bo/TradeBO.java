package br.com.fcoinvest.invest.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fcoinvest.invest.dtos.TradeDTO;
import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.enums.TipoOperacao;
import br.com.fcoinvest.invest.models.Trade;
import br.com.fcoinvest.invest.repositories.TradeRepository;
import br.com.fcoinvest.invest.services.TradeService;
import lombok.RequiredArgsConstructor;

/**
 * @author Francisco
 *
 */
@Service
@RequiredArgsConstructor
public class TradeBO implements TradeService {

	@Autowired
	private final TradeRepository tradeRepository;
	
	@Autowired
	private final CotacaoBO cotacaoService;

	@Override
	public List<TradeDTO> getAll() {
		return TradeDTO.convert(tradeRepository.findAll());
	}

	@Override
	public void salvar(Trade trade) {
		tradeRepository.save(trade);
	}
	
	/**
	 * @author Francisco
	 * Retorna a rentabilidade da carteira
	 */
	@Override
	public Double portifolioRentabilidade(Ativo a, List<TradeDTO> trades) {
		Double var = 0.0;
		for (TradeDTO t : trades) {
			if(a.toString().equals((t.getCotacao().getAtivo()))) {
				if(t.getTipoOperacao().equals(TipoOperacao.COMPRA.toString())) {
					var = var + t.getTotal();
				}else {
					var = var - t.getTotal();
				}
			}
		}
		return var;
	}
	
	/**
	 * @author Francisco
	 * Retorna a quantidade de ações da carteira
	 */
	@Override
	public int portifolioQuantidade(Ativo a, List<TradeDTO> trades) {
		int var = 0;
		for (TradeDTO t : trades) {
			if(a.toString().equals((t.getCotacao().getAtivo()))) {
				if(t.getTipoOperacao().equals(TipoOperacao.COMPRA.toString())) {
					var = var + t.getQtd();
				}else {
					var = var - t.getQtd();
				}
			}
		}
		return var;
	}

	
	/**
	 * @author Francisco
	 * Retorna os trades do usuário e ativo passados por parâmetro
	 */
	@Override
	public List<TradeDTO> getAllByUsuarioEAtivo(Ativo a, Long idUsuario) {
		Optional<List<Trade>> list_opt = tradeRepository.findByAtivo(a);
		List<TradeDTO> list = new ArrayList<TradeDTO>();
		if(list_opt != null && list_opt.isPresent()) {
			list = TradeDTO.convert(list_opt.get());
			filtraPorUsuarios(list, idUsuario);
			return list;
		}
		return null;
	}

	//retira os trades que não são do usuário
	public static List<TradeDTO> filtraPorUsuarios(List<TradeDTO> trades, Long idUsuario) {
		List<TradeDTO> t = new ArrayList<TradeDTO>();
		Iterator<TradeDTO> iterator = trades.iterator();
		while (iterator.hasNext()) {
			TradeDTO dto = iterator.next();
			if (dto.getUsuario().getId().equals(idUsuario)) {
				t.add(dto);
			}
		}
		return t;
	}
	
	public static List<TradeDTO> filtraPorAtivo(List<TradeDTO> trades, String ativo) {
		List<TradeDTO> t = new ArrayList<TradeDTO>();
		Iterator<TradeDTO> iterator = trades.iterator();
		while (iterator.hasNext()) {
			TradeDTO dto = iterator.next();
			if (dto.getAtivo().equals(ativo)) {
				t.add(dto);
			}
		}
		return t;
	}

	
	//retira os trades que não são do usuário e não são do ativo
	public static List<TradeDTO> filtraPorUsuariosEAtivo(List<TradeDTO> trades, Long idUsuario, String ativo) {
		List<TradeDTO> t = new ArrayList<TradeDTO>();
		Iterator<TradeDTO> iterator = trades.iterator();
		while (iterator.hasNext()) {
			TradeDTO dto = iterator.next();
			if ((dto.getUsuario().getId().equals(idUsuario)) &&
					dto.getAtivo().equals(ativo)) {
				t.add(dto);
			}
		}
		return t;
	}
	
	@Override
	public List<TradeDTO> getAllByDataBeforeAndUsuarioAndAtivo(Date data, Ativo ativo, Long idUsuario) {

		List<Trade> list = tradeRepository.findByDataBeforeOrderByDataAsc(data);
		if (list != null && !list.isEmpty()) {
			List<TradeDTO> list_dto = TradeDTO.convert(list);
			return filtraPorUsuariosEAtivo(list_dto, idUsuario, ativo.toString());
		}
		
		return null;
	}

	@Override
	public Double investimento(List<TradeDTO> trades) {
		Double val = 0.0;
		for (TradeDTO t : trades) {
			if (t.getTipoOperacao().equals(TipoOperacao.COMPRA.toString())) {
				val = val + t.getTotal();
			}
		}
		return val;
	}

	@Override
	public Double realização(List<TradeDTO> trades) {
		Double val = 0.0;
		for (TradeDTO t : trades) {
			if (t.getTipoOperacao().equals(TipoOperacao.VENDA.toString())) {
				val = val + t.getTotal();
			}
		}
		return val;
	}

	@Override
	public Double rendimentoPorcento(List<TradeDTO> trades, Date data) {
		Double rendLiquido = rendimentoLiquido(trades, data);
		Double pm = precoMedio(trades);
		int qtd = patrimonioQtd(trades);
		return (rendLiquido)/(pm*qtd);
	}

	@Override
	public Double rendimentoLiquido(List<TradeDTO> trades, Date data) {
		int qtdAtual = patrimonioQtd(trades);
		Double pm = precoMedio(trades);
		Double valorMaisAtual = cotacaoService.getValorMaisAtual(Ativo.valueOf(trades.get(0).getAtivo()), data);
		return (qtdAtual*valorMaisAtual)-(qtdAtual*pm);
	}

	@Override
	public int patrimonioQtd(List<TradeDTO> trades) {
		int val = 0;
		for (TradeDTO t : trades) {
			if (t.getTipoOperacao().equals(TipoOperacao.COMPRA.toString())) {
				val = val + t.getQtd();
			}else{
				val = val - t.getQtd();
			}
		}
		return val;
		
	}

	@Override
	public Double precoMedio(List<TradeDTO> trades) {
		Double val = 0.0;
		int qtdPesos = 0;
		for (TradeDTO t : trades) {
			if (t.getTipoOperacao().equals(TipoOperacao.COMPRA.toString())) {
				val = val + t.getTotal();
				qtdPesos = qtdPesos + t.getQtd();
			}
		}
		
		return val/qtdPesos;
	}

	@Override
	public List<TradeDTO> getAllByDataBeforeAndUsuario(Date data, Long idUsuario) {
		List<Trade> list = tradeRepository.findByDataBeforeOrderByDataAsc(data);
		if (list != null && !list.isEmpty()) {
			List<TradeDTO> list_dto = TradeDTO.convert(list);
			return filtraPorUsuarios(list_dto, idUsuario);
		}
		
		return null;
	}

	@Override
	public Double patrimonioLiquidoAtual(List<TradeDTO> trades) {
		Double val = 0.0;
		Double pm_aux = 0.0;
		int qtd_aux = 0;
		List<TradeDTO> trades_aux;
		for (Ativo a : Ativo.values()) {
			trades_aux = filtraPorAtivo(trades, a.toString());
			if(trades_aux != null && !trades_aux.isEmpty()) {
				qtd_aux = patrimonioQtd(trades_aux);
				pm_aux = precoMedio(trades_aux);
				val = val + (qtd_aux*pm_aux);
			}
		}
		
		return val;
	}

	@Override
	public List<TradeDTO> getAllByDataBetwween(Date dataInicio, Date dataFim, Long idUsuario) {
		List<Trade> list = tradeRepository.findAllByDataBetween(dataInicio, dataFim);
		
		if (list != null && !list.isEmpty()) {
			List<TradeDTO> list_dto = TradeDTO.convert(list);
			return filtraPorUsuarios(list_dto, idUsuario);
		}
		
		return null;
	}

}
