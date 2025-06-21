package br.univille.startcontrol.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.univille.startcontrol.dto.UsuarioDTO;
import br.univille.startcontrol.service.UsuarioService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception{

        String emailAdmin = "admin@startcontrol.com";

        UsuarioDTO admin = new UsuarioDTO();
        admin.setNome("Administrador");
        admin.setEmail(emailAdmin);
        admin.setSenha("admin123");
        admin.setTipo("ADMIN");
        admin.setAtivo(true);

        usuarioService.criar(admin);
        System.out.println("Usuário admin criado com sucesso!");

    }
    
}