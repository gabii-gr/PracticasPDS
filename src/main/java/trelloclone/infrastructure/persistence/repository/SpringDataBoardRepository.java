package trelloclone.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trelloclone.infrastructure.persistence.entity.BoardEntity;

import java.util.Optional;

public interface SpringDataBoardRepository extends JpaRepository<BoardEntity, String> {
    Optional<BoardEntity> findByUrlHash(String urlHash);
    Optional<BoardEntity> findFirstByOwnerEmail(String ownerEmail);
}
