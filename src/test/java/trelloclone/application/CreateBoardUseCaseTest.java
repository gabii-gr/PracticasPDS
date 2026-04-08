package trelloclone.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import trelloclone.domain.model.Board;
import trelloclone.domain.port.BoardRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateBoardUseCaseTest {

    private BoardRepository simuladorRepositorio;
    private CreateBoardUseCase casoDeUsoCrearTablero;

    @BeforeEach
    void configurarPrueba() {
        simuladorRepositorio = mock(BoardRepository.class);
        casoDeUsoCrearTablero = new CreateBoardUseCase(simuladorRepositorio);
    }

    @Test
    void deberiaCrearYGuardarNuevoTablero() {
        // Preparar
        String tituloTablero = "Tablero PDS Principal";
        String correoUsuario = "alumno@unizar.es";

        // Ejecutar
        Board resultado = casoDeUsoCrearTablero.execute(tituloTablero, correoUsuario);

        // Comprobar
        assertThat(resultado).isNotNull();
        assertThat(resultado.getTitle()).isEqualTo(tituloTablero);
        assertThat(resultado.getOwner().getEmail()).isEqualTo(correoUsuario);
        
        // Comprobar que se llamó a la base de datos para guardar el nuevo tablero
        ArgumentCaptor<Board> capturadorTablero = ArgumentCaptor.forClass(Board.class);
        verify(simuladorRepositorio).save(capturadorTablero.capture());
        
        Board tableroGuardado = capturadorTablero.getValue();
        assertThat(tableroGuardado.getTitle()).isEqualTo(tituloTablero);
    }

    @Test
    void deberiaLanzarExcepcionCuandoElCorreoEsInvalido() {
        // Preparar
        String tituloTablero = "Tablero Erroneo";
        String correoInvalido = "correo-sin-arroba-ni-formato";

        // Ejecutar y Comprobar que lanza la excepción esperada en español
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            casoDeUsoCrearTablero.execute(tituloTablero, correoInvalido);
        });
        
        assertThat(excepcion.getMessage()).isEqualTo("Email inválido");
        verify(simuladorRepositorio, never()).save(any()); // Asegura que no se guarda en BD
    }
}
