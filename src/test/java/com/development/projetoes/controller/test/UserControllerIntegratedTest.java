package com.development.projetoes.controller.test;

import com.development.projetoes.dto.UserDto;
import com.development.projetoes.entity.UserEntity;
import com.development.projetoes.model.Response;
import com.development.projetoes.repository.IUserRepository;
import com.development.projetoes.service.IUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class UserControllerIntegratedTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IUserRepository userRepository;

    @BeforeEach
    public void init() {
        this.montaBaseDeDados();
    }

    @AfterEach
    public void finish() {
        this.userRepository.deleteAll();
    }

    private void montaBaseDeDados() {
        UserEntity u1 = new UserEntity();
        u1.setUserId(1L);
        u1.setCpf("02324726530");
        u1.setEmail("test@email.com");
        u1.setIsAdmin(true);
        u1.setNome("Test Testador");
        u1.setSenha("3214");

        UserEntity u2 = new UserEntity();
        u2.setUserId(2L);
        u2.setCpf("22173803034");
        u2.setEmail("test2@email.com");
        u2.setIsAdmin(false);
        u2.setNome("Test2 Testador");
        u2.setSenha("3214");

        UserEntity u3 = new UserEntity();
        u3.setUserId(3L);
        u3.setCpf("32357473037");
        u3.setEmail("test3@email.com");
        u3.setIsAdmin(false);
        u3.setNome("Test3 Testador");
        u3.setSenha("3214");

        this.userRepository.saveAll(Arrays.asList(u1, u2, u3));
    }

    @Test
    public void testListarUsers() {
        ResponseEntity<Response<List<UserDto>>> users = restTemplate
                .exchange("http://localhost:" + this.port + "/user", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<UserDto>>>() {
                        });
        assertNotNull(users.getBody().getData());
        assertEquals(3, users.getBody().getData().size());
        assertEquals(200, users.getBody().getStatusCode());
    }

//    @Test
//    public void testConsultarUsers() {
//        Mockito.when(this.userService.consultar(1L)).thenReturn(userDto);
//
//        ResponseEntity<Response<UserDto>> users = restTemplate
//                .exchange("http://localhost:" + this.port + "/user/1", HttpMethod.GET, null,
//                        new ParameterizedTypeReference<Response<UserDto>>() {
//                        });
//        assertNotNull(users.getBody().getData());
//        assertEquals(200, users.getBody().getStatusCode());
//    }

//    @Test
//    public void testDeletarUsers() {
//        Mockito.when(this.userService.excluir(1L)).thenReturn(Boolean.TRUE);
//
//        ResponseEntity<Response<Boolean>> users = restTemplate
//                .exchange("http://localhost:" + this.port + "/user/1", HttpMethod.DELETE, null,
//                        new ParameterizedTypeReference<Response<Boolean>>() {
//                        });
//        assertNotNull(users.getBody().getData());
//        assertEquals(200, users.getBody().getStatusCode());
//    }
//
//    @Test
//    public void testCadastrarUsers() {
//        Mockito.when(this.userService.cadastrar(userDto)).thenReturn(Boolean.TRUE);
//
//        HttpEntity<UserDto> request = new HttpEntity<UserDto>(userDto);
//
//        ResponseEntity<Response<Boolean>> users = restTemplate
//                .exchange("http://localhost:" + this.port + "/user/", HttpMethod.POST, request,
//                        new ParameterizedTypeReference<Response<Boolean>>() {
//                        });
//        assertNotNull(users.getBody().getData());
//        assertEquals(201, users.getBody().getStatusCode());
//    }

}
