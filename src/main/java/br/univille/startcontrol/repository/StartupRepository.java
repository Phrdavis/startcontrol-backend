package br.univille.startcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.Startup;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {}
