package trelloclone;

import trelloclone.domain.model.Board;
import trelloclone.application.CreateBoardUseCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TrelloCloneApplicationTests {

    @Autowired
    private CreateBoardUseCase createBoardUseCase;

    @Test
    void contextLoads() {
        assertNotNull(createBoardUseCase);
    }
    
    @Test
    void canCreateBoardViaUseCase() {
        Board b =  createBoardUseCase.execute("PDS Test", "user@unizar.es");
        assertEquals("PDS Test", b.getTitle());
        assertEquals("user@unizar.es", b.getOwner().getEmail());
    }
}
