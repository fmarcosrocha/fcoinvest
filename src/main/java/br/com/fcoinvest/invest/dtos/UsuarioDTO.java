package br.com.fcoinvest.invest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import br.com.fcoinvest.invest.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

	private Long id;
	private String nome;
	
	public static UsuarioDTO convert(Usuario usuario) {
		if(usuario==null) return null;
		
		return UsuarioDTO.builder()
				.id(usuario.getId())
				.nome(usuario.getNome())
				.build();
	}

	public static List<UsuarioDTO> convert(List<Usuario> usuario) {
		if (usuario == null) {
			return null;
		}
		return usuario.stream().map(UsuarioDTO::convert).collect(Collectors.toList());
	}
	
	
}
