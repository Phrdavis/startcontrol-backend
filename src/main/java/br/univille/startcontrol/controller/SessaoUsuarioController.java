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

import br.univille.startcontrol.dto.SessaoUsuarioDTO;
import br.univille.startcontrol.model.SessaoUsuario;
import br.univille.startcontrol.service.SessaoUsuarioService;

@RestController
@RequestMapping("/api/sessoes-usuarios")
public class SessaoUsuarioController {

    private final SessaoUsuarioService sessaoUsuarioService;

    public SessaoUsuarioController(SessaoUsuarioService sessaoUsuarioService) {
        this.sessaoUsuarioService = sessaoUsuarioService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody SessaoUsuarioDTO sessaoUsuarioDTO) {
        return sessaoUsuarioService.criar(sessaoUsuarioDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return sessaoUsuarioService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<SessaoUsuario>> buscarTodos() {
        List<SessaoUsuario> sessoesUsuarios = sessaoUsuarioService.buscarTodos();
        return ResponseEntity.ok(sessoesUsuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody SessaoUsuarioDTO sessaoUsuarioDTO) {
        return sessaoUsuarioService.atualizar(id, sessaoUsuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sessaoUsuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
