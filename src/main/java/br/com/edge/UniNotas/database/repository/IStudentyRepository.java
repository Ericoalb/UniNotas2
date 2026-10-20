package br.com.edge.UniNotas.database.repository;

import br.com.edge.UniNotas.database.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentyRepository extends JpaRepository<StudentEntity, Integer>
{
    StudentEntity findByEmail(String email);
}
