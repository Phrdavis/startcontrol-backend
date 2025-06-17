package br.univille.startcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.SessaoUsuario;

@Repository
public interface SessaoUsuarioRepository extends JpaRepository<SessaoUsuario, Long> {}
