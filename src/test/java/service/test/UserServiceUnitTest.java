package service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.development.projetoes.dto.UserDto;
import com.development.projetoes.entity.UserEntity;
import com.development.projetoes.repository.IUserRepository;
import com.development.projetoes.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserServiceUnitTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static UserEntity userEntity;

    @BeforeAll
    public static void init() {

        userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setCpf("02324726530");
        userEntity.setEmail("test@email.com");
        userEntity.setIsAdmin(true);
        userEntity.setNome("Test Testador");
        userEntity.setSenha("3214");

    }

    /*
     *
     * CENARIOS DE SUCESSO
     *
     */

    @Test
    public void testListarSucesso() {
        List<UserEntity> listUser = new ArrayList<>();
        listUser.add(userEntity);

        Mockito.when(this.userRepository.findAll()).thenReturn(listUser);

        List<UserDto> listUserDto = this.userService.listar();

        assertNotNull(listUserDto);
        assertEquals("02324726530", listUserDto.get(0).getCpf());
        assertEquals(1, listUserDto.get(0).getUserId());
        assertEquals(1, listUserDto.size());

        Mockito.verify(this.userRepository, times(1)).findAll();

    }

    @Test
    public void testConsultarSucesso() {
        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        UserDto userDto = this.userService.consultar(1L);

        assertNotNull(userDto);
        assertEquals("02324726530", userDto.getCpf());
        assertEquals(1, userDto.getUserId());

        Mockito.verify(this.userRepository, times(1)).findById(1L);
    }

//    @Test
//    public void testCadastrarSucesso() {
//
//        UserDto userDto = new UserDto();
//        userDto.setCpf("72954485000");
//        userDto.setEmail("testando2@email.com");
//        userDto.setIsAdmin(true);
//        userDto.setNome("Testando Test");
//        userDto.setSenha("3214");
//
//        userEntity.setUserId(null);
//
//        Mockito.when(this.userRepository.findByCpf("72954485000")).thenReturn(null);
//        Mockito.when(this.userRepository.save(userEntity)).thenReturn(userEntity);
//
//        Boolean sucesso = this.userService.cadastrar(userDto);
//
//        assertTrue(sucesso);
//
//        Mockito.verify(this.userRepository, times(1)).findByCpf("45565246016");
//        Mockito.verify(this.userRepository, times(1)).save(userEntity);
//
//        userEntity.setUserId(1L);
//
//    }
//
//    @Test
//    public void testAtualizarSucesso() {
//
//        UserDto userDto = new UserDto();
//        userDto.setCpf("72954485000");
//        userDto.setEmail("testando2@email.com");
//        userDto.setIsAdmin(false);
//        userDto.setNome("Testando2 Test");
//        userDto.setSenha("3214");
//
//
//        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
//        Mockito.when(this.userRepository.save(userEntity)).thenReturn(userEntity);
//
//        Boolean sucesso = this.userService.atualizar(userDto);
//
//        assertTrue(sucesso);
//
//        Mockito.verify(this.userRepository, times(0)).findByCpf("72954485000");
//        Mockito.verify(this.userRepository, times(1)).findById(1L);
//        Mockito.verify(this.userRepository, times(1)).save(userEntity);
//    }

    @Test
    public void testExcluirSucesso() {

        Mockito.when(this.userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        Boolean sucesso = this.userService.excluir(1L);

        assertTrue(sucesso);

        Mockito.verify(this.userRepository, times(0)).findByCpf("02324726530");
        Mockito.verify(this.userRepository, times(1)).findById(1L);
        Mockito.verify(this.userRepository, times(1)).deleteById(1L);
        Mockito.verify(this.userRepository, times(0)).save(userEntity);
    }

}
