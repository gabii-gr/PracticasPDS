package trelloclone.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import trelloclone.domain.model.Board;
import trelloclone.domain.port.BoardRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryBoardRepository implements BoardRepository {
    // Repositorio temporal en memoria para Hito 1 (sin JPA aún para mantener foco)
    private final Map<String, Board> store = new HashMap<>();

    @Override
    public void save(Board board) {
        store.put(board.getId(), board);
    }

    @Override
    public Optional<Board> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Board> findByUrlHash(String urlHash) {
        return store.values().stream()
                .filter(b -> b.getUrlHash().equals(urlHash))
                .findFirst();
    }

	@Override
	public Optional<Board> findByOwnerEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
