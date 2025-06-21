package br.univille.startcontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.AssociacaoUsuarioStartup;
import br.univille.startcontrol.model.Startup;
import br.univille.startcontrol.model.Usuario;

@Repository
public interface AssociacaoUsuarioStartupRepository extends JpaRepository<AssociacaoUsuarioStartup, Long> {

    @Query("SELECT a.id, a.usuarioId FROM AssociacaoUsuarioStartup a WHERE a.startupId.id = :id")
    List<Object[]> buscarUsersStartup(@Param("id") Long id);

    @Query("SELECT a.startupId FROM AssociacaoUsuarioStartup a WHERE a.usuarioId = :usuarioId")
    List<Startup> findByUsuarioId(@Param("usuarioId") Usuario usuarioId);

}
