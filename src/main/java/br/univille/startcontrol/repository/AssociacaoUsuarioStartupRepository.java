package br.univille.startcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.AssociacaoUsuarioStartup;

@Repository
public interface AssociacaoUsuarioStartupRepository extends JpaRepository<AssociacaoUsuarioStartup, Long> {}
