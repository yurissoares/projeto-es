package com.development.projetoes.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data
@NoArgsConstructor
public class UserEntity implements Serializable  {

	private static final long serialVersionUID = -4431674448259205673L;

	@JsonInclude(Include.NON_NULL)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="nome")
	private String nome;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="email")
	private String email;
	
	@JsonInclude(Include.NON_EMPTY)
	@Column(name="senha")
	private String senha;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name="cpf")
	private String cpf;

	@JsonInclude(Include.NON_EMPTY)
	@Column(name="admin")
	private Boolean isAdmin;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tb_user_evento",
		joinColumns = {@JoinColumn(name = "user_id")},
		inverseJoinColumns = {@JoinColumn(name = "evento_id")})
	private List<EventoEntity> eventos = new ArrayList<EventoEntity>();
	
}
