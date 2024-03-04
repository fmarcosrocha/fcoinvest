package br.com.fcoinvest.invest.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortifolioCarteiraDTO {
	private List<PortifolioAtivoDTO> portAtivo;
	private Double rendTotal;
	private Double rendTotalPorc;
	private String rendTotalMask;
	private String rendTotalPorcMask;
	private String msgErro;
	private Date dataInicio;
	private Date dataFim;
}
