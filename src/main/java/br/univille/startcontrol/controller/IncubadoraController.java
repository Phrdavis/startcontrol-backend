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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.univille.startcontrol.dto.IncubadoraDTO;
import br.univille.startcontrol.model.Incubadora;
import br.univille.startcontrol.service.IncubadoraService;

@RestController
@RequestMapping("/api/incubadoras")
public class IncubadoraController {

    private final IncubadoraService incubadoraService;   

    public IncubadoraController(IncubadoraService incubadoraService) {
        this.incubadoraService = incubadoraService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody IncubadoraDTO incubadoraDTO) {
        return incubadoraService.criar(incubadoraDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return incubadoraService.buscarPorId(id);
    }

    @GetMapping
    public ResponseEntity<List<Incubadora>> buscarTodos() {
        List<Incubadora> incubadoras = incubadoraService.buscarTodos();

        return ResponseEntity.ok(incubadoras);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody IncubadoraDTO incubadoraDTO) {
        return incubadoraService.atualizar(id, incubadoraDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return incubadoraService.deletar(id);
    }

    @PostMapping("/multiplas")
    public ResponseEntity<?> criarMultiplas(@RequestBody List<IncubadoraDTO> incubadoraDTOs) {
        return incubadoraService.criarMultiplas(incubadoraDTOs);
    }

}
