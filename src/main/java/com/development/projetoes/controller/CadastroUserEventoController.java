package com.development.projetoes.controller;

import com.development.projetoes.dto.CadastroUserEventoDto;
import com.development.projetoes.model.Response;
import com.development.projetoes.service.ICadastroUserEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cadastroUserEvento")
public class CadastroUserEventoController {

	@Autowired
	private ICadastroUserEventoService cadastroUserEventoService;

	@GetMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<List<CadastroUserEventoDto>>> listar() {
		Response<List<CadastroUserEventoDto>> response = new Response<>();
		response.setData(this.cadastroUserEventoService.listar());
		response.setStatusCode(HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{cadastroUserEventoId}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<CadastroUserEventoDto>> consultar(@PathVariable Long cadastroUserEventoId) {
		Response<CadastroUserEventoDto> response = new Response<>();
		CadastroUserEventoDto cadastroUserEvento = this.cadastroUserEventoService.consultar(cadastroUserEventoId);
		response.setData(cadastroUserEvento);
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Boolean>> cadastrar(@Valid @RequestBody CadastroUserEventoDto cadastroUserEvento) {
		Response<Boolean> response = new Response<>();
		response.setData(this.cadastroUserEventoService.cadastrar(cadastroUserEvento));
		response.setStatusCode(HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/{cadastroUserEventoId}")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Boolean>> excluir(@PathVariable Long cadastroUserEventoId) {
		Response<Boolean> response = new Response<>();
		response.setData(this.cadastroUserEventoService.excluir(cadastroUserEventoId));
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response<Boolean>> atualizar(@Valid @RequestBody CadastroUserEventoDto cadastroUserEvento) {
		Response<Boolean> response = new Response<>();
		response.setData(this.cadastroUserEventoService.atualizar(cadastroUserEvento));
		response.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
