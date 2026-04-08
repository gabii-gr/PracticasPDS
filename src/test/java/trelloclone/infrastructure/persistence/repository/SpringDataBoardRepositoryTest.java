package trelloclone.infrastructure.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import trelloclone.infrastructure.persistence.entity.BoardEntity;
import trelloclone.infrastructure.persistence.entity.TaskListEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class SpringDataBoardRepositoryTest {

    @Autowired
    private SpringDataBoardRepository repositorioTableros;

    @Test
    void deberiaGuardarYRecuperarTablero() {
        // Preparar
        BoardEntity entidadTablero = new BoardEntity("tablero-123", "Proyecto de Prueba PDS", "alumno@unizar.es", "abcdef123456", false);
        
        TaskListEntity entidadLista = new TaskListEntity("lista-1", "POR HACER", null);
        entidadLista.setBoard(entidadTablero);
        entidadTablero.addList(entidadLista);

        // Ejecutar
        repositorioTableros.save(entidadTablero);
        Optional<BoardEntity> tableroRecuperado = repositorioTableros.findById("tablero-123");

        // Comprobar
        assertThat(tableroRecuperado).isPresent();
        assertThat(tableroRecuperado.get().getTitle()).isEqualTo("Proyecto de Prueba PDS");
        assertThat(tableroRecuperado.get().getOwnerEmail()).isEqualTo("alumno@unizar.es");
        assertThat(tableroRecuperado.get().getLists()).hasSize(1);
        assertThat(tableroRecuperado.get().getLists().get(0).getName()).isEqualTo("POR HACER");
    }

    @Test
    void deberiaBorrarTableroCorrectamente() {
        // Preparar
        BoardEntity tableroParaBorrar = new BoardEntity("tablero-borrar", "Tablero a Borrar", "profesor@unizar.es", "xxxxxx", false);
        repositorioTableros.save(tableroParaBorrar);

        // Ejecutar el borrado
        repositorioTableros.deleteById("tablero-borrar");
        Optional<BoardEntity> comprobacion = repositorioTableros.findById("tablero-borrar");

        // Comprobar que ya no existe
        assertThat(comprobacion).isEmpty();
    }
}
