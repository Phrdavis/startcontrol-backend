package br.univille.startcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.Projeto;


@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

} 
