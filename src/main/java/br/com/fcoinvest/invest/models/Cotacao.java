package br.com.fcoinvest.invest.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.fcoinvest.invest.enums.Ativo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cotacao", schema = "public")
public class Cotacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4108488734122194373L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Enumerated(EnumType.STRING)
	private Ativo ativo;
	
	private Double valor;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	

}
