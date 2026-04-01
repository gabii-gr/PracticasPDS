package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "action_trace")
public class ActionTraceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUsuario;
    private String accion;
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    public ActionTraceEntity() {}

    public ActionTraceEntity(String emailUsuario, String accion, LocalDateTime fecha, BoardEntity board) {
        this.emailUsuario = emailUsuario;
        this.accion = accion;
        this.fecha = fecha;
        this.board = board;
    }

    public Long getId() { return id; }
    public String getEmailUsuario() { return emailUsuario; }
    public String getAccion() { return accion; }
    public LocalDateTime getFecha() { return fecha; }
    public BoardEntity getBoard() { return board; }
}
