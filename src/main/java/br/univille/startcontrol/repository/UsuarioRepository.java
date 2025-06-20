package br.univille.startcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.univille.startcontrol.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    // Verifica se existe alguma Startup com o usuário como responsável
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Startup s WHERE s.responsavel.id = :id")
    Boolean existsUsuarioAsResponsavelInStartup(@Param("id") long id);

} 
