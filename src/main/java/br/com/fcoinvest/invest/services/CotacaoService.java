package br.com.fcoinvest.invest.services;

import java.util.Date;
import java.util.List;

import br.com.fcoinvest.invest.dtos.CotacaoDTO;
import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.models.Cotacao;

public interface CotacaoService {
	List<CotacaoDTO> getAll();
	List<Cotacao> getAllEntities();
	List<CotacaoDTO> getAllByAnoMes(String ano, String mes);
	List<CotacaoDTO> getAllByData(Date data);
	List<CotacaoDTO> getAllByAtivo(String ativo);
	Cotacao getById(Long id);
	CotacaoDTO getLastByDataBeforeAndAtivo(Ativo ativo, Date data);
	Double getValorMaisAtual(Ativo ativo, Date data);
}
