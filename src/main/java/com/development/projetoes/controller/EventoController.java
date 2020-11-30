package com.development.projetoes.controller;

import java.util.List;

import javax.validation.Valid;

import com.development.projetoes.dto.EventoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.projetoes.model.Response;
import com.development.projetoes.service.IEventoService;

@RestController
@RequestMapping("/evento")
public class EventoController {

	@Autowired
	private IEventoService eventoService;

	@GetMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<List<EventoDto>>> listarEventos() {
		Response<List<EventoDto>> response = new Response<>();
		response.setData(this.eventoService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{eventoId}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<EventoDto>> consultarEvento(@PathVariable Long eventoId) {
		Response<EventoDto> response = new Response<>();
		EventoDto evento = this.eventoService.consultar(eventoId);
		response.setData(evento);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Boolean>> cadastrarEvento(@Valid @RequestBody EventoDto evento) {
		Response<Boolean> response = new Response<>();
		response.setData(this.eventoService.cadastrar(evento));
		response.setStatusCode(HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{eventoId}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Boolean>> excluirEvento(@PathVariable Long eventoId) {
		Response<Boolean> response = new Response<>();
		response.setData(this.eventoService.excluir(eventoId));
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Boolean>> atualizarEvento(@Valid @RequestBody EventoDto evento) {
		Response<Boolean> response = new Response<>();
		response.setData(this.eventoService.atualizar(evento));
		response.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
