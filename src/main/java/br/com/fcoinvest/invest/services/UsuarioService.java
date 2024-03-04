package br.com.fcoinvest.invest.services;

import java.util.List;

import br.com.fcoinvest.invest.dtos.UsuarioDTO;
import br.com.fcoinvest.invest.models.Usuario;

public interface UsuarioService {
	List<UsuarioDTO> getAll();
	List<Usuario> getAllEntities();
	Usuario getById(Long id);
}
