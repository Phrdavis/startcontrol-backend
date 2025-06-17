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

import br.univille.startcontrol.dto.StartupDTO;
import br.univille.startcontrol.model.Startup;
import br.univille.startcontrol.service.StartupService;

@RestController
@RequestMapping("/api/startups")
public class StartupController {

    private final StartupService startupService;   

    public StartupController(StartupService startupService) {
        this.startupService = startupService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody StartupDTO startupDTO) {
        return startupService.criar(startupDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return startupService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Startup>> buscarTodos() {
        List<Startup> startups = startupService.buscarTodos();
        return ResponseEntity.ok(startups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody StartupDTO startupDTO) {
        return startupService.atualizar(id, startupDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        startupService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/multiplas")
    public ResponseEntity<?> criarMultiplas(@RequestBody List<StartupDTO> startupDTOs) {
        return startupService.criarMultiplas(startupDTOs);
    }

}
