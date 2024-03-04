package br.com.fcoinvest.invest.controllers;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fcoinvest.invest.dtos.CotacaoDTO;
import br.com.fcoinvest.invest.services.CotacaoService;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/cotacao")
@RequiredArgsConstructor
public class CotacaoController implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 7031914900324366495L;

	@Autowired
	private CotacaoService cotacaoService;
	

	@GetMapping("/por-dia")
	public ResponseEntity<List<CotacaoDTO>> porDia(String d) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);

		String dateInString = d; 
		Date date = formatter.parse(dateInString);
		List<CotacaoDTO> list = cotacaoService.getAllByData(date);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/por-ativo")
	public ResponseEntity<List<CotacaoDTO>> porAtivo(String a) throws ParseException {
		List<CotacaoDTO> list = cotacaoService.getAllByAtivo(a);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/por-usuario")
	public ResponseEntity<List<CotacaoDTO>> porUsuario(String u) throws ParseException {
		List<CotacaoDTO> list = cotacaoService.getAllByAtivo(u);
		return ResponseEntity.ok(list);
	}
}
