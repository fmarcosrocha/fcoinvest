package br.com.fcoinvest.invest.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortifolioAtivoDTO {
	private UsuarioDTO usuario;
	private String ativo;
	private int qnt;
	private String investimentoTotalMask;
	private String rendimentoMask;
	private String rendimentoPorcentagemMask;
	private Double investimentoTotal;
	private Double rendimento;
	private Double rendimentoPorcentagem;
	private Date dataInicio;
	private Date dataFim;
	private String msgErro;

}
