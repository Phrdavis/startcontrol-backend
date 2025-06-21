package br.univille.startcontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.Startup;
import br.univille.startcontrol.model.Usuario;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {

    List<Startup> findByResponsavel(Usuario responsavel);

}
