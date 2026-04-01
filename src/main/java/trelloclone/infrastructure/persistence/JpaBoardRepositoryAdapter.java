package trelloclone.infrastructure.persistence;

import trelloclone.domain.model.Board;
import trelloclone.domain.port.BoardRepository;
import trelloclone.infrastructure.persistence.entity.BoardEntity;
import trelloclone.infrastructure.persistence.mapper.BoardMapper;
import trelloclone.infrastructure.persistence.repository.SpringDataBoardRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Primary
@Repository
@Transactional
public class JpaBoardRepositoryAdapter implements BoardRepository {

    private final SpringDataBoardRepository springDataRepository;
    private final BoardMapper mapper;

    public JpaBoardRepositoryAdapter(SpringDataBoardRepository springDataRepository, BoardMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Board board) {
        BoardEntity entity = mapper.toEntity(board);
        springDataRepository.save(entity);
    }

    @Override
    public Optional<Board> findById(String id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Board> findByUrlHash(String urlHash) {
        return springDataRepository.findByUrlHash(urlHash).map(mapper::toDomain);
    }

    @Override
    public Optional<Board> findByOwnerEmail(String email) {
        return springDataRepository.findFirstByOwnerEmail(email).map(mapper::toDomain);
    }
}
