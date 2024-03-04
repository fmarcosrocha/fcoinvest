package br.com.fcoinvest.invest.services;

import java.util.Date;
import java.util.List;

import br.com.fcoinvest.invest.dtos.TradeDTO;
import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.models.Trade;

public interface TradeService {
	List<TradeDTO> getAll();
	void salvar(Trade trade);
	Double portifolioRentabilidade(Ativo a, List<TradeDTO> trades);
	int portifolioQuantidade(Ativo a, List<TradeDTO> trades);
	List<TradeDTO> getAllByUsuarioEAtivo(Ativo a, Long idUsuario);
	List<TradeDTO> getAllByDataBeforeAndUsuarioAndAtivo(Date data, Ativo ativo, Long idUsuario);
	List<TradeDTO> getAllByDataBeforeAndUsuario(Date data, Long idUsuario);
	List<TradeDTO> getAllByDataBetwween(Date dataInicio, Date dataFim, Long idUsuario);
	
	Double investimento(List<TradeDTO> trades);
	Double realização(List<TradeDTO> trades);
	Double rendimentoPorcento(List<TradeDTO> trades, Date data);
	Double rendimentoLiquido(List<TradeDTO> trades, Date data);
	int patrimonioQtd(List<TradeDTO> trades);
	Double precoMedio(List<TradeDTO> trades);
	Double patrimonioLiquidoAtual(List<TradeDTO> trades);
}
