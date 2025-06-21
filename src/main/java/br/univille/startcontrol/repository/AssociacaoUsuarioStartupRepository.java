package br.univille.startcontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.AssociacaoUsuarioStartup;

@Repository
public interface AssociacaoUsuarioStartupRepository extends JpaRepository<AssociacaoUsuarioStartup, Long> {

    @Query("SELECT a.id, a.usuarioId FROM AssociacaoUsuarioStartup a WHERE a.startupId.id = :id")
    List<Object[]> buscarUsersStartup(@Param("id") Long id);

}
