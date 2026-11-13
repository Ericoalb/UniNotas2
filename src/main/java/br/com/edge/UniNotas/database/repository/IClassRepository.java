package br.com.edge.UniNotas.database.repository;

import br.com.edge.UniNotas.database.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<ClassEntity, Integer> {

    ClassEntity findByNameAndCourse(String name, String course);

    @Query("""
    SELECT e FROM ClassEntity e WHERE UPPER(e.name) = UPPER(:name)
    """)
    List<ClassEntity> findAllByNamejpql(@Param("name") String name);

    @Query("""
    SELECT c.course FROM ClassEntity c WHERE c.college.id = :collegeId
    """)
    List<String> findCoursesByCollegeId(@Param("collegeId") Integer collegeId);
}
