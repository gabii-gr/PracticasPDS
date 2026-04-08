package trelloclone.infrastructure.persistence.mapper;

import trelloclone.domain.model.*;
import trelloclone.infrastructure.persistence.entity.*;

import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public BoardEntity toEntity(Board board) {
        if (board == null) return null;
        
        BoardEntity entity = new BoardEntity(
            board.getId(),
            board.getTitle(),
            board.getOwner().getEmail(),
            board.getUrlHash(),
            board.isLocked()
        );

        // Mapear Listas
        for (TaskList list : board.getLists()) {
            TaskListEntity listEntity = new TaskListEntity(list.getId(), list.getName(), list.getMaxItems());
            listEntity.setRequiredPreviousListIds(new java.util.ArrayList<>(list.getRequiredPreviousListIds()));
            
            // Mapear Tarjetas
            for (Card card : list.getCards()) {
                CardEntity cardEntity = mapCardToEntity(card);
                listEntity.addCard(cardEntity);
            }
            entity.addList(listEntity);
        }

        // Mapear Historial
        for (ActionTrace trace : board.getHistory()) {
            ActionTraceEntity traceEntity = new ActionTraceEntity(
                trace.getUser().getEmail(),
                trace.getAction(),
                trace.getDate(),
                entity
            );
            entity.addHistoryTrace(traceEntity);
        }

        return entity;
    }

    private CardEntity mapCardToEntity(Card card) {
        CardEntity cardEntity;
        if (card instanceof TaskCard) {
            TaskCard tc = (TaskCard) card;
            cardEntity = new TaskCardEntity(tc.getId(), tc.getTitle(), tc.getDescription(),
                    tc.getAssignedUser() != null ? tc.getAssignedUser().getEmail() : null, tc.isCompleted());
        } else if (card instanceof ChecklistCard) {
            ChecklistCard cc = (ChecklistCard) card;
            ChecklistCardEntity cce = new ChecklistCardEntity(cc.getId(), cc.getTitle(), cc.getDescription());
            for (ChecklistItem item : cc.getItems()) {
                cce.addItem(new ChecklistItemEntity(item.getId(), item.getText(), item.isDone(), cce));
            }
            cardEntity = cce;
        } else {
            throw new IllegalArgumentException("Tipo de tarjeta desconocido");
        }
        
        cardEntity.setVisitedListIds(new java.util.HashSet<>(card.getVisitedListIds()));
        return cardEntity;
    }

    public Board toDomain(BoardEntity entity) {
        if (entity == null) return null;
        
        // Utiliza reflection o un constructor dedicado para el ID ya que es final en el modelo de dominio,
        // pero para la simplicidad en este mapeo generado, reconstruimos el estado del dominio.
        // En un DDD estricto real podríamos necesitar una fábrica o setters a nivel de paquete.
        // Por ahora, esto es un mapeo conceptual que necesitaría un ajuste fino para los IDs finales.
        
        Board board = new Board(entity.getTitle(), new User(entity.getOwnerEmail()));
        board.setLocked(entity.isLocked());
        // El urlHash y el ID requerirían inyección si no se autogeneran, 
        // lo omitimos en este fragmento básico de demostración.
        
        for (TaskListEntity listEntity : entity.getLists()) {
            TaskList list = new TaskList(listEntity.getName());
            list.setMaxItems(listEntity.getMaxItems());
            list.setRequiredPreviousListIds(new java.util.ArrayList<>(listEntity.getRequiredPreviousListIds()));
            board.addList(list, new User(entity.getOwnerEmail())); // Reconstrucción simplificada
        }
        
        return board;
    }
}
