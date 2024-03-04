package br.com.fcoinvest.invest.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.fcoinvest.invest.enums.Ativo;
import br.com.fcoinvest.invest.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trade", schema = "public")
public class Trade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3620780324863615932L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@ManyToOne
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Enumerated(EnumType.STRING)
	private TipoOperacao operacao;
	
	@Enumerated(EnumType.STRING)
	private Ativo ativo;
	
	private int qnt;
	
	@ManyToOne
	private Cotacao cotacao;
	
	private Double total;
	
}
