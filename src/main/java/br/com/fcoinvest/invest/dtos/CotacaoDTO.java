package br.com.fcoinvest.invest.dtos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fcoinvest.invest.models.Cotacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CotacaoDTO {

	private Long id;
	private String ativo;
	private Double valor;
	private Date data;
	
	public static CotacaoDTO convert(Cotacao cotacao) {
		if(cotacao==null) return null;
		
		return CotacaoDTO.builder()
				.id(cotacao.getId())
				.ativo(cotacao.getAtivo().toString())
				.valor(cotacao.getValor())
				.data(cotacao.getData())
				.build();
	}

	public static List<CotacaoDTO> convert(List<Cotacao> cotacao) {
		if (cotacao == null) {
			return null;
		}
		return cotacao.stream().map(CotacaoDTO::convert).collect(Collectors.toList());
	}
	
}
