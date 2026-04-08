package trelloclone.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import trelloclone.application.AddCardUseCase;
import trelloclone.application.AddListUseCase;
import trelloclone.application.CompactBoardUseCase;
import trelloclone.application.CreateBoardFromTemplateUseCase;
import trelloclone.application.CreateBoardUseCase;
import trelloclone.application.FilterCardsUseCase;
import trelloclone.application.LockBoardUseCase;
import trelloclone.application.MoveCardUseCase;
import trelloclone.domain.port.BoardRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateBoardUseCase createBoardUseCase(BoardRepository boardRepository) {
        return new CreateBoardUseCase(boardRepository);
    }

    @Bean
    public AddListUseCase addListUseCase(BoardRepository boardRepository) {
        return new AddListUseCase(boardRepository);
    }

    @Bean
    public AddCardUseCase addCardUseCase(BoardRepository boardRepository) {
        return new AddCardUseCase(boardRepository);
    }

    @Bean
    public MoveCardUseCase moveCardUseCase(BoardRepository boardRepository) {
        return new MoveCardUseCase(boardRepository);
    }

    @Bean
    public LockBoardUseCase lockBoardUseCase(BoardRepository boardRepository) {
        return new LockBoardUseCase(boardRepository);
    }

    @Bean
    public FilterCardsUseCase filterCardsUseCase(BoardRepository boardRepository) {
        return new FilterCardsUseCase(boardRepository);
    }

    @Bean
    public CreateBoardFromTemplateUseCase createBoardFromTemplateUseCase(BoardRepository repository, CreateBoardUseCase createBoardUseCase) {
        return new CreateBoardFromTemplateUseCase(repository, createBoardUseCase);
    }

    @Bean
    public CompactBoardUseCase compactBoardUseCase(BoardRepository boardRepository) {
        return new CompactBoardUseCase(boardRepository);
    }
}
