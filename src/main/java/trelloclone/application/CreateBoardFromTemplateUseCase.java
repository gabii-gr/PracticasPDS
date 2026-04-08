package trelloclone.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import trelloclone.application.template.YamlBoardTemplate;
import trelloclone.domain.model.Board;
import trelloclone.domain.model.Card;
import trelloclone.domain.model.ChecklistCard;
import trelloclone.domain.model.TaskCard;
import trelloclone.domain.model.TaskList;
import trelloclone.domain.port.BoardRepository;

import java.io.InputStream;

public class CreateBoardFromTemplateUseCase {
    private final BoardRepository repository;
    private final CreateBoardUseCase createBoardUseCase;

    public CreateBoardFromTemplateUseCase(BoardRepository repository, CreateBoardUseCase createBoardUseCase) {
        this.repository = repository;
        this.createBoardUseCase = createBoardUseCase;
    }

    public Board execute(InputStream yamlStream, String ownerEmail) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            YamlBoardTemplate template = mapper.readValue(yamlStream, YamlBoardTemplate.class);

            Board newBoard = createBoardUseCase.execute(template.getTitle(), ownerEmail);

            if (template.getLists() != null) {
                for (YamlBoardTemplate.YamlListTemplate listTemplate : template.getLists()) {
                    TaskList newList = new TaskList(listTemplate.getName());
                    newBoard.addList(newList, newBoard.getOwner());

                    if (listTemplate.getCards() != null) {
                        for (YamlBoardTemplate.YamlCardTemplate cardTemplate : listTemplate.getCards()) {
                            Card card;
                            if ("CHECKLIST".equalsIgnoreCase(cardTemplate.getType())) {
                                card = new ChecklistCard(cardTemplate.getTitle(), cardTemplate.getDescription());
                            } else {
                                card = new TaskCard(cardTemplate.getTitle(), cardTemplate.getDescription());
                            }
                            newBoard.addCardToList(card, newList, newBoard.getOwner());
                        }
                    }
                }
            }

            repository.save(newBoard);
            return newBoard;
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la plantilla YAML para crear el tablero", e);
        }
    }
}
