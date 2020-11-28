package com.development.projetoes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_user_evento")
@Data
@NoArgsConstructor
public class CadastroUserEventoEntity implements Serializable  {

	private static final long serialVersionUID = -4431674448259205673L;

	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_evento_id")
	private Long userEventoId;

	@JsonInclude(Include.NON_NULL)
	@ManyToOne
	@JoinColumn(name="evento_id")
	private EventoEntity evento;

	@JsonInclude(Include.NON_NULL)
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity user;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name="dt_inscricao")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtInscricao;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name="concluido")
	private Boolean isConcluido;
	
}
