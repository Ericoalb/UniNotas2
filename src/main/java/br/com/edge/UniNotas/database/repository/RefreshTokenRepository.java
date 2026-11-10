package br.com.edge.UniNotas.database.repository;

import br.com.edge.UniNotas.database.model.RefreshTokenEntity;
import br.com.edge.UniNotas.database.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByStudent(StudentEntity student);

    void deleteByStudent(StudentEntity student);
}
