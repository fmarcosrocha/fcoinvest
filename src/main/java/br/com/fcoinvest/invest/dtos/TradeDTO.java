package br.com.fcoinvest.invest.dtos;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fcoinvest.invest.models.Trade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeDTO {
	
	private Long id;
	private UsuarioDTO usuario;
	private Date data;
	private String tipoOperacao;
	private String ativo;
	private int qtd;
	private CotacaoDTO cotacao;
	private Double total;
	
	public static TradeDTO convert(Trade trade) {
		if(trade==null) return null;
		
		return TradeDTO.builder()
				.id(trade.getId())
				.usuario(UsuarioDTO.convert(trade.getUsuario()))
				.data(trade.getData())
				.tipoOperacao(trade.getOperacao().toString())
				.ativo(trade.getAtivo().toString())
				.qtd(trade.getQnt())
				.cotacao(CotacaoDTO.convert(trade.getCotacao()))
				.total(trade.getTotal())
				.build();
	}

	public static List<TradeDTO> convert(List<Trade> trade) {
		if (trade == null) {
			return null;
		}
		return trade.stream().map(TradeDTO::convert).collect(Collectors.toList());
	}
}
