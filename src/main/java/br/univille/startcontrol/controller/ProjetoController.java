package br.univille.startcontrol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import br.univille.startcontrol.dto.ProjetoDTO;
import br.univille.startcontrol.model.Projeto;
import br.univille.startcontrol.service.ProjetoService;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService){
        this.projetoService = projetoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ProjetoDTO projetoDTO) {
        return projetoService.criar(projetoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id);
    }

    @GetMapping
    public List<Projeto> buscarTodos() {
        return projetoService.buscarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
        return projetoService.atualizar(id, projetoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return projetoService.deletar(id);
    }
    
    @PostMapping("/multiplos")
    public ResponseEntity<?> criarMultiplos(@RequestBody List<ProjetoDTO> projetosDTO) {
        return projetoService.criarMultiplos(projetosDTO);
    }
}
