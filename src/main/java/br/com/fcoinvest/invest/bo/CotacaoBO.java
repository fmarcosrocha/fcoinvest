package br.com.fcoinvest.invest.bo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fcoinvest.invest.dtos.CotacaoDTO;
import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.models.Cotacao;
import br.com.fcoinvest.invest.repositories.CotacaoRepository;
import br.com.fcoinvest.invest.services.CotacaoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CotacaoBO implements CotacaoService{
	
	@Autowired
	private final CotacaoRepository cotacaoRepository;
	
	@Override
	public List<CotacaoDTO> getAll() {
		return CotacaoDTO.convert(cotacaoRepository.findAll());
	}

	@Override
	public List<CotacaoDTO> getAllByAnoMes(String ano, String mes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CotacaoDTO> getAllByData(Date data) {
		return CotacaoDTO.convert(cotacaoRepository.findByData(data));
	}

	@Override
	public List<CotacaoDTO> getAllByAtivo(String ativo) {
		return CotacaoDTO.convert(cotacaoRepository.findByAtivo(Ativo.valueOf(ativo)));
	}

	@Override
	public Cotacao getById(Long id) {
		Optional<Cotacao> c = cotacaoRepository.findById(id);
		if(c.isPresent()) {
			return c.get();
		}
		return null;
	}

	@Override
	public List<Cotacao> getAllEntities() {
		return cotacaoRepository.findAll();
	}

	@Override
	public CotacaoDTO getLastByDataBeforeAndAtivo(Ativo ativo, Date data) {
		Optional<Cotacao> c = cotacaoRepository.findFirstByDataBeforeAndAtivoOrderByDataDesc(data, ativo);
		if (c != null && c.isPresent()) {
			return CotacaoDTO.convert(c.get());			
		}
		return null;
	}

	@Override
	public Double getValorMaisAtual(Ativo ativo, Date data) {
		CotacaoDTO c = getLastByDataBeforeAndAtivo(ativo, data);
		if (c != null) {
			return c.getValor();
		}
		return null;
	}

}
