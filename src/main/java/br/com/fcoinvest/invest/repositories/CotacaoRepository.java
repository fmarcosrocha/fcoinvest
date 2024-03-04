package br.com.fcoinvest.invest.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.models.Cotacao;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long>{
	List<Cotacao> findAll();
	List<Cotacao> findByData(Date data);
	List<Cotacao> findByAtivo(Ativo ativo);
	Optional<Cotacao> findFirstByDataBeforeAndAtivoOrderByDataDesc(Date data, Ativo ativo);
}
