package br.com.fcoinvest.invest.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fcoinvest.invest.services.CotacaoService;
import br.com.fcoinvest.invest.services.TradeService;
import br.com.fcoinvest.invest.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/pesquisa")
@RequiredArgsConstructor
public class PesquisaController implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 3607424818526687262L;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CotacaoService cotacaoService;
	
	@Autowired
	private TradeService tradeService;
	
	@GetMapping("/")
	public String index() {
		return "test";
	}

	@GetMapping("/usuarios")
	public String getUsuarios() {
		return usuarioService.getAll().toString();
	}
	
	@GetMapping("/cotacoes")
	public String getCotacoes() {
		return cotacaoService.getAll().toString();
	}
	
	@GetMapping("/trades")
	public String getTrades() {
		return tradeService.getAll().toString();
	}
	
}
