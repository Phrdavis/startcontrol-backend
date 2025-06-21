package br.univille.startcontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.Startup;
import br.univille.startcontrol.model.Usuario;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {

    List<Startup> findByResponsavel(Usuario responsavel);

    @Query("SELECT aus.usuarioId FROM AssociacaoUsuarioStartup aus WHERE aus.startupId.responsavel = :responsavel")
    List<Usuario> findUsuariosByResponsavel(@Param("responsavel") Usuario responsavel);

    @Query("SELECT DISTINCT aus2.usuarioId FROM AssociacaoUsuarioStartup aus1 JOIN AssociacaoUsuarioStartup aus2 ON aus1.startupId = aus2.startupId WHERE aus1.usuarioId = :usuarioId")
    List<Usuario> findUsuariosAssociadosMesmasStartups(@Param("usuarioId") Usuario usuarioId);

}
