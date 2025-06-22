package br.univille.startcontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.Projeto;


@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("SELECT p.status, COUNT(p) FROM Projeto p GROUP BY p.status")
    List<Object[]> countProjetosByStatus();

} 
