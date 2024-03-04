package br.com.fcoinvest.invest.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.models.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long>{
	List<Trade> findAll();
	Optional<List<Trade>> findByAtivo(Ativo a);

	List<Trade> findByDataBeforeOrderByDataAsc(Date data);
	List<Trade> findAllByDataBetween(Date dataInicio, Date dataFim);
}
