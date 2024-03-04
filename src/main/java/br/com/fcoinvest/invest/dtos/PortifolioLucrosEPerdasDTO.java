package br.com.fcoinvest.invest.dtos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortifolioLucrosEPerdasDTO {
	private List<PortifolioAtivoDTO> ativosLucro;
	private List<PortifolioAtivoDTO> ativosPerda;
	Map<String,String> patrimonio;
	private Double totalLucro;
	private String totalLucroMask;
	private Double totalPerda;
	private String totalPerdaMask;
	private Double rendTotal;
	private Double rendTotalPorc;
	private String rendTotalMask;
	private String rendTotalPorcMask;
	private String msgErro;
	private Date dataInicio;
	private Date dataFim;

}
