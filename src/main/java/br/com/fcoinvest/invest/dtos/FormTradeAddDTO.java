package br.com.fcoinvest.invest.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormTradeAddDTO {
	List<CotacaoDTO> cotacoes;
	List<UsuarioDTO> usuarios;
	List<String> anos;
	List<String> meses;

}
