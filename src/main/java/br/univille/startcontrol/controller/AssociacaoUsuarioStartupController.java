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

import br.univille.startcontrol.dto.AssociacaoUsuarioStartupDTO;
import br.univille.startcontrol.model.AssociacaoUsuarioStartup;
import br.univille.startcontrol.service.AssociacaoUsuarioStartupService;

@RestController
@RequestMapping("/api/associacoes")
public class AssociacaoUsuarioStartupController {

    private final AssociacaoUsuarioStartupService associacaoUsuarioStartupService;

    public AssociacaoUsuarioStartupController(AssociacaoUsuarioStartupService associacaoUsuarioStartupService) {
        this.associacaoUsuarioStartupService = associacaoUsuarioStartupService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AssociacaoUsuarioStartupDTO associacaoDTO) {
        return associacaoUsuarioStartupService.criar(associacaoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return associacaoUsuarioStartupService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<AssociacaoUsuarioStartup>> buscarTodos() {
        List<AssociacaoUsuarioStartup> associacoes = associacaoUsuarioStartupService.buscarTodos();
        return ResponseEntity.ok(associacoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AssociacaoUsuarioStartupDTO associacaoDTO) {
        return associacaoUsuarioStartupService.atualizar(id, associacaoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        associacaoUsuarioStartupService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
