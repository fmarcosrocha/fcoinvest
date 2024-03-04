package br.com.fcoinvest.invest.bo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fcoinvest.invest.dtos.UsuarioDTO;
import br.com.fcoinvest.invest.models.Usuario;
import br.com.fcoinvest.invest.repositories.UsuarioRepository;
import br.com.fcoinvest.invest.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioBO implements UsuarioService{

	@Autowired
	private final UsuarioRepository usuarioRepository;

	@Override
	public List<UsuarioDTO> getAll() {
		return UsuarioDTO.convert(usuarioRepository.findAll());
	}

	@Override
	public Usuario getById(Long id) {
		Optional<Usuario> u = usuarioRepository.findById(id);
		if (u.isPresent()) {
			return u.get();
		}
		return null;
	}

	@Override
	public List<Usuario> getAllEntities() {
		return usuarioRepository.findAll();
	}
	
}
