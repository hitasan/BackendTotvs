package com.totvs.accountspayable.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.totvs.accountspayable.application.enums.SituacaoConta;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class AccountsPayable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private LocalDate dataemissao;

	@Column(nullable = false)
	private LocalDate datavencimento;

	@Column
	private LocalDate datapagamento;

	@Column(nullable = false, precision = 10, scale = 2) // Define precisão e escala
    private BigDecimal valor; // Alterado para BigDecimal
	
	@Column(nullable = false)
	private String fornecedor;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING) // Mapeando o ENUM como varchar no banco de dados
	private SituacaoConta situacao;

}