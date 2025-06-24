package br.univille.startcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.Incubadora;

@Repository
public interface IncubadoraRepository extends JpaRepository<Incubadora, Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Startup s WHERE s.incubadora = :id")
    Boolean existsIncubadoraVinculadaStartup(@Param("id") Long id);

    @Query("SELECT COUNT(s) FROM Startup s WHERE s.incubadora.id = :id")
    long countStartupsByIncubadora(@Param("id") Long id);

}
