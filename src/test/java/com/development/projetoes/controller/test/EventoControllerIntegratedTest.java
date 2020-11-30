package com.development.projetoes.controller.test;

import com.development.projetoes.dto.EventoDto;
import com.development.projetoes.entity.EventoEntity;
import com.development.projetoes.model.Response;
import com.development.projetoes.repository.IEventoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class EventoControllerIntegratedTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IEventoRepository eventoRepository;

    @BeforeEach
    public void init() {
        this.montaBaseDeDados();
    }

    @AfterEach
    public void finish() {
        this.eventoRepository.deleteAll();
    }

    private void montaBaseDeDados() {
        EventoEntity e1 = new EventoEntity();
        e1.setEventoId(1L);
        e1.setNome("Reconcitec 1");
        e1.setDtCriacao(LocalDate.now());
        e1.setDtEncerramento(LocalDate.now());
        e1.setInformacoes("Evento do Reconcitec 1");

        EventoEntity e2 = new EventoEntity();
        e2.setEventoId(2L);
        e2.setNome("Reconcitec 2");
        e2.setDtCriacao(LocalDate.now());
        e2.setDtEncerramento(LocalDate.now());
        e2.setInformacoes("Evento do Reconcitec 2");

        EventoEntity e3 = new EventoEntity();
        e3.setEventoId(3L);
        e3.setNome("Reconcitec 3");
        e3.setDtCriacao(LocalDate.now());
        e3.setDtEncerramento(LocalDate.now());
        e3.setInformacoes("Evento do Reconcitec 3");

        this.eventoRepository.saveAll(Arrays.asList(e1, e2, e3));
    }

    @Test
    public void testListarEventos() {
        ResponseEntity<Response<List<EventoDto>>> eventos = restTemplate
                .exchange("http://localhost:" + this.port + "/evento", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<EventoDto>>>() {
                        });
        assertNotNull(eventos.getBody().getData());
        assertEquals(3, eventos.getBody().getData().size());
        assertEquals(200, eventos.getBody().getStatusCode());
    }

    @Test
    public void testConsultarEventoPorId() {
        ResponseEntity<Response<EventoDto>> eventos = restTemplate
                .exchange("http://localhost:" + this.port + "/evento/1", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<EventoDto>>() {
                        });
        assertNotNull(eventos.getBody().getData());
        assertEquals("Reconcitec 1", eventos.getBody().getData().getNome());
        assertEquals("Evento do Reconcitec 1", eventos.getBody().getData().getInformacoes());
        assertEquals(200, eventos.getBody().getStatusCode());
    }

    @Test
    public void testDeletarEventos() {
        List<EventoEntity> eventosList = this.eventoRepository.findAll();
        Long id = eventosList.get(0).getEventoId();

        ResponseEntity<Response<Boolean>> eventos = restTemplate
                .exchange("http://localhost:" + this.port + "/evento/" + id, HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });

        List<EventoEntity> listEventoAtualizado = this.eventoRepository.findAll();

        assertTrue(eventos.getBody().getData());
        assertEquals(2, listEventoAtualizado.size());
        assertEquals(200, eventos.getBody().getStatusCode());
    }

//    @Test
//    public void testAtualizarEventos() {
//        List<EventoEntity> eventosList = this.eventoRepository.findAll();
//        EventoEntity evento = eventosList.get(0);
//
//        evento.setNome("Evento de Teste");
//
//        HttpEntity<EventoEntity> request = new HttpEntity<>(evento);
//
//        ResponseEntity<Response<Boolean>> eventos = restTemplate
//                .exchange("http://localhost:" + this.port + "/evento/", HttpMethod.PUT, request,
//                        new ParameterizedTypeReference<Response<Boolean>>() {
//                        });
//
//        EventoEntity eventoAtualizado = this.eventoRepository.findById(evento.getEventoId()).get();
//
//        assertTrue(eventos.getBody().getData());
//        assertNotNull(eventos.getBody().getData());
//        assertEquals("Evento de Teste", eventoAtualizado.getNome());
//        assertEquals(200, eventos.getBody().getStatusCode());
//    }

//    @Test
//    public void testCadastrarEvento() {
//        EventoEntity evento = new EventoEntity();
////      evento.setEventoId(3L);
//        evento.setNome("Reconcitec 4");
//        evento.setDtCriacao(LocalDate.now());
//        evento.setDtEncerramento(LocalDate.now());
//        evento.setInformacoes("Evento do Reconcitec 4");
//
//        HttpEntity<EventoEntity> request = new HttpEntity<>(evento);
//
//        ResponseEntity<Response<Boolean>> eventos = restTemplate
//                .exchange("http://localhost:" + this.port + "/evento/", HttpMethod.POST, request,
//                        new ParameterizedTypeReference<Response<Boolean>>() {
//                        });
//
//        List<EventoEntity> listEventoAtualizado = this.eventoRepository.findAll();
//
//        assertTrue(eventos.getBody().getData());
//        assertEquals(4,listEventoAtualizado.size());
//        assertEquals(201, eventos.getBody().getStatusCode());
//    }

}
