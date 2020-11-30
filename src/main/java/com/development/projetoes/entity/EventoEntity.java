package com.development.projetoes.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Entity
@Table(name = "tb_evento")
@Data
@NoArgsConstructor
public class EventoEntity implements Serializable {

	private static final long serialVersionUID = 702051841961317525L;
	
	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="evento_id")
	private Long eventoId;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="nome")
	private String nome;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name="dt_criacao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtCriacao;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name="dt_encerramento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtEncerramento;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name="informacoes")
	private String informacoes;

	@JsonInclude(Include.NON_NULL)
	@JoinColumn(name="user_id")
	@ManyToOne
	private UserEntity user;

	@ManyToMany(mappedBy = "eventos", cascade = CascadeType.ALL)
	private List<UserEntity> users = new ArrayList<UserEntity>();

}
