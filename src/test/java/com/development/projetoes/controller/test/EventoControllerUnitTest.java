package com.development.projetoes.controller.test;

import com.development.projetoes.dto.EventoDto;
import com.development.projetoes.model.Response;
import com.development.projetoes.service.IEventoService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class EventoControllerUnitTest {

    @LocalServerPort
    private int port;

    @MockBean
    private IEventoService eventoService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static EventoDto eventoDto;

    @BeforeAll
    public static void init() {
        eventoDto = new EventoDto();
        eventoDto.setEventoId(1L);
        eventoDto.setNome("Reconcitec 1");
        eventoDto.setDtCriacao(LocalDate.now());
        eventoDto.setDtEncerramento(LocalDate.now());
        eventoDto.setInformacoes("Evento do Reconcitec 1");

    }

    @Test
    public void testListarEventos() {
        Mockito.when(this.eventoService.listar()).thenReturn(new ArrayList<EventoDto>());

        ResponseEntity<Response<List<EventoDto>>> eventos = restTemplate
                .exchange("http://localhost:" + this.port + "/evento", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<EventoDto>>>() {
                        });
        assertNotNull(eventos.getBody().getData());
        assertEquals(200, eventos.getBody().getStatusCode());
    }

    @Test
    public void testConsultarEventos() {
        Mockito.when(this.eventoService.consultar(1L)).thenReturn(eventoDto);

        ResponseEntity<Response<EventoDto>> eventos = restTemplate
                .exchange("http://localhost:" + this.port + "/evento/1", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<EventoDto>>() {
                        });
        assertNotNull(eventos.getBody().getData());
        assertEquals(200, eventos.getBody().getStatusCode());
    }

    @Test
    public void testDeletarEventos() {
        Mockito.when(this.eventoService.excluir(1L)).thenReturn(Boolean.TRUE);

        ResponseEntity<Response<Boolean>> eventos = restTemplate
                .exchange("http://localhost:" + this.port + "/evento/1", HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        assertNotNull(eventos.getBody().getData());
        assertEquals(200, eventos.getBody().getStatusCode());
    }

//    @Test
//    public void testCadastrarEventos() {
//        Mockito.when(this.eventoService.cadastrar(eventoDto)).thenReturn(Boolean.TRUE);
//
//        HttpEntity<EventoDto> request = new HttpEntity<EventoDto>(eventoDto);
//
//        ResponseEntity<Response<Boolean>> eventos = restTemplate
//                .exchange("http://localhost:" + this.port + "/evento/", HttpMethod.POST, request,
//                        new ParameterizedTypeReference<Response<Boolean>>() {
//                        });
//        assertNotNull(eventos.getBody().getData());
//        assertEquals(201, eventos.getBody().getStatusCode());
//    }

}
