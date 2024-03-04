package br.com.fcoinvest.invest.dtos;

import java.util.Date;
import java.util.List;

import br.com.fcoinvest.invest.enums.Ativo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortifolioDTO {
	private UsuarioDTO usuario;
	private List<Ativo> ativos;
	private List<Integer> qts;
	private List<Double> rendimentos;
	private Double investimentoTOtal;
	private Double rendimentoTotal;
	private Double rendimentoTotalPorcentagem;
	private Date dataInicio;
	private Date dataFim;

}
