package service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.development.projetoes.dto.EventoDto;
import com.development.projetoes.entity.EventoEntity;
import com.development.projetoes.entity.UserEntity;
import com.development.projetoes.exception.EventoException;
import com.development.projetoes.repository.IEventoRepository;
import com.development.projetoes.repository.IUserRepository;
import com.development.projetoes.service.EventoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class EventoServiceUnitTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IEventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    private static UserEntity userEntity;

    private static EventoEntity eventoEntity;

    @BeforeAll
    public static void init() {

        userEntity = new UserEntity();
        userEntity.setUserId(1L);
        userEntity.setCpf("02324726530");
        userEntity.setEmail("teste@email.com");
        userEntity.setIsAdmin(false);
        userEntity.setNome("Teste Testador");
        userEntity.setSenha("3214");

        List<UserEntity> listMat = new ArrayList<>();
        listMat.add(userEntity);

        eventoEntity = new EventoEntity();
        eventoEntity.setEventoId(1L);
        eventoEntity.setNome("Reconcitec 1");
        eventoEntity.setDtCriacao(LocalDate.now());
        eventoEntity.setDtEncerramento(LocalDate.now());
        eventoEntity.setInformacoes("Evento do Reconcitec 1");
        eventoEntity.setUser(userEntity);
        eventoEntity.setUsers(listMat);
    }

    /*
     *
     * CENARIOS DE SUCESSO
     *
     */

    @Test
    public void testListarSucesso() {
        List<EventoEntity> listEvento = new ArrayList<>();
        listEvento.add(eventoEntity);

        Mockito.when(this.eventoRepository.findAll()).thenReturn(listEvento);

        List<EventoDto> listEventoDto = this.eventoService.listar();

        assertNotNull(listEventoDto);
        assertEquals("Reconcitec 1", listEventoDto.get(0).getNome());
        assertEquals(1, listEventoDto.get(0).getEventoId());
        assertEquals(1, listEventoDto.size());

        Mockito.verify(this.eventoRepository, times(1)).findAll();

    }

    @Test
    public void testConsultarSucesso() {
        Mockito.when(this.eventoRepository.findById(1L)).thenReturn(Optional.ofNullable(eventoEntity));
        EventoDto eventoDto = this.eventoService.consultar(1L);

        assertNotNull(eventoDto);
        assertEquals("Reconcitec 1", eventoDto.getNome());
        assertEquals(1, eventoDto.getEventoId());

        Mockito.verify(this.eventoRepository, times(1)).findById(1L);
    }

    @Test
    public void testExcluirSucesso() {

        Mockito.when(this.eventoRepository.findById(1L)).thenReturn(Optional.of(eventoEntity));
        Boolean sucesso = this.eventoService.excluir(1L);

        assertTrue(sucesso);

        Mockito.verify(this.eventoRepository, times(1)).findById(1L);
        Mockito.verify(this.eventoRepository, times(1)).deleteById(1L);
    }

    /*
     *
     * CENARIOS DE THROW-EVENTO-EXCEPTION
     *
     */

    @Test
    public void testExcluirThrowEventoException() {

        Mockito.when(this.eventoRepository.findById(1L)).thenReturn(Optional.empty());

        EventoException eventoException;

        eventoException = assertThrows(EventoException.class, () -> {
            this.eventoService.excluir(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, eventoException.getHttpStatus());

        Mockito.verify(this.eventoRepository, times(1)).findById(1L);
        Mockito.verify(this.eventoRepository, times(0)).deleteById(1L);

    }

    /*
     *
     * CENARIOS DE THROW EXCEPTION
     *
     */

    @Test
    public void testListarThrowException() {

        Mockito.when(this.eventoRepository.findAll()).thenThrow(IllegalStateException.class);

        EventoException eventoException;

        eventoException = assertThrows(EventoException.class, ()->{
            this.eventoService.listar();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, eventoException.getHttpStatus());

        Mockito.verify(this.eventoRepository, times(1)).findAll();

    }


}
