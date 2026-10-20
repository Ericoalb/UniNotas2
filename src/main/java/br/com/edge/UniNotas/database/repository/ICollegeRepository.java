package br.com.edge.UniNotas.database.repository;

import br.com.edge.UniNotas.database.model.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public interface ICollegeRepository extends JpaRepository<CollegeEntity, Integer>
{


    CollegeEntity findByName(String name);

    @Query("""
    SELECT e FROM CollegeEntity e WHERE UPPER(e.name) = UPPER(:name)
""")
    List<CollegeEntity> findAllByNamejpql(@Param("name") String name);


    @NativeQuery("""
    SELECT e FROM college e WHERE UPPER(e.name) = UPPER(:name)
""")

    List<CollegeEntity> findAllByNative(@Param("name") String name);



}


