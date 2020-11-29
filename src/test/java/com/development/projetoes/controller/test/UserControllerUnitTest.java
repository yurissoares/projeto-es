package com.development.projetoes.controller.test;

import com.development.projetoes.dto.UserDto;
import com.development.projetoes.model.Response;
import com.development.projetoes.service.IUserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class UserControllerUnitTest {

    @LocalServerPort
    private int port;

    @MockBean
    private IUserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static UserDto userDto;

    @BeforeAll
    public static void init() {
        userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setCpf("02324726530");
        userDto.setEmail("teste@email.com");
        userDto.setIsAdmin(false);
        userDto.setNome("Teste Testador");
        userDto.setSenha("3214");

    }

    @Test
    public void testListarUsers() {
        Mockito.when(this.userService.listar()).thenReturn(new ArrayList<UserDto>());

        ResponseEntity<Response<List<UserDto>>> users = restTemplate
                .exchange("http://localhost:" + this.port + "/user", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<UserDto>>>() {
                        });
        assertNotNull(users.getBody().getData());
        assertEquals(200, users.getBody().getStatusCode());
    }

    @Test
    public void testConsultarUsers() {
        Mockito.when(this.userService.consultar(1L)).thenReturn(userDto);

        ResponseEntity<Response<UserDto>> users = restTemplate
                .exchange("http://localhost:" + this.port + "/user/1", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<UserDto>>() {
                        });
        assertNotNull(users.getBody().getData());
        assertEquals(200, users.getBody().getStatusCode());
    }

    @Test
    public void testDeletarUsers() {
        Mockito.when(this.userService.excluir(1L)).thenReturn(Boolean.TRUE);

        ResponseEntity<Response<Boolean>> users = restTemplate
                .exchange("http://localhost:" + this.port + "/user/1", HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        assertNotNull(users.getBody().getData());
        assertEquals(200, users.getBody().getStatusCode());
    }

    @Test
    public void testCadastrarUsers() {
        Mockito.when(this.userService.cadastrar(userDto)).thenReturn(Boolean.TRUE);

        HttpEntity<UserDto> request = new HttpEntity<UserDto>(userDto);

        ResponseEntity<Response<Boolean>> users = restTemplate
                .exchange("http://localhost:" + this.port + "/user/", HttpMethod.POST, request,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        assertNotNull(users.getBody().getData());
        assertEquals(201, users.getBody().getStatusCode());
    }

}
