package trelloclone.domain.port;

import java.util.Optional;

import trelloclone.domain.model.Board;

public interface BoardRepository {
    void save(Board board);
    Optional<Board> findById(String id);
    Optional<Board> findByUrlHash(String urlHash);
    Optional<Board> findByOwnerEmail(String email);
}
