package br.univille.startcontrol.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.startcontrol.dto.UsuarioDTO;
import br.univille.startcontrol.model.Usuario;
import br.univille.startcontrol.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.criar(usuarioDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.atualizar(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return usuarioService.deletar(id);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            String token = usuarioService.authenticate(usuarioDTO);
            return ResponseEntity.ok().body(
                java.util.Collections.singletonMap("token", token)
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(
                java.util.Collections.singletonMap("erro", ex.getMessage())
            );
        }
    }

    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<UsuarioDTO> usuarioDTOs) {
        return usuarioService.criarMultiplos(usuarioDTOs);
    }

    @PostMapping("/trash-password-recovery")
    public ResponseEntity<?> trashPasswordRecovery(@RequestBody UsuarioDTO usuarioDTOs) {
        return usuarioService.trashPasswordRecovery(usuarioDTOs);
    }

}
