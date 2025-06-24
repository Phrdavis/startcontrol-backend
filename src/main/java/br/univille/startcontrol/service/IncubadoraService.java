package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.univille.startcontrol.dto.IncubadoraDTO;
import br.univille.startcontrol.model.Incubadora;
import br.univille.startcontrol.repository.IncubadoraRepository;

@Service
public class IncubadoraService {

    private final IncubadoraRepository incubadoraRepository;

    public IncubadoraService(IncubadoraRepository incubadoraRepository) {
        this.incubadoraRepository = incubadoraRepository;
    }

    public ResponseEntity<?> criarMultiplas(List<IncubadoraDTO> incubadoras) {
        if (incubadoras == null || incubadoras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de Incubadoras vazia"));
        }
        List<Incubadora> novasIncubadoras = new java.util.ArrayList<>();
        for (IncubadoraDTO dto : incubadoras) {
            Incubadora novaIncubadora = new Incubadora();
            novaIncubadora.setNome(dto.getNome());
            novaIncubadora.setDescricao(dto.getDescricao());
            novaIncubadora.setTag(dto.getTag());
            novaIncubadora.setColor(dto.getColor());
            novaIncubadora.setIcon(dto.getIcon());
            novaIncubadora.setAtivo(true);
            novasIncubadoras.add(novaIncubadora);
        }
        List<Incubadora> salvas = incubadoraRepository.saveAll(novasIncubadoras);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvas);
    }

    public ResponseEntity<?> criar(IncubadoraDTO incubadora) {
        Incubadora novaIncubadora = new Incubadora();
        novaIncubadora.setNome(incubadora.getNome());
        novaIncubadora.setDescricao(incubadora.getDescricao());
        novaIncubadora.setTag(incubadora.getTag());
        novaIncubadora.setColor(incubadora.getColor());
        novaIncubadora.setIcon(incubadora.getIcon());
        novaIncubadora.setAtivo(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(incubadoraRepository.save(novaIncubadora));
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Incubadora incubadora = incubadoraRepository.findById(id).orElse(null);
        if (incubadora == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Incubadora não encontrada"));
        }
        return ResponseEntity.ok(incubadora);
    }

    public List<Incubadora> buscarTodos() {
        return incubadoraRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, IncubadoraDTO incubadoraDTO) {
        Incubadora novaIncubadora = incubadoraRepository.findById(id).orElse(null);
        if (novaIncubadora == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Incubadora não encontrada"));
        }
        novaIncubadora.setNome(incubadoraDTO.getNome());
        novaIncubadora.setDescricao(incubadoraDTO.getDescricao());
        novaIncubadora.setTag(incubadoraDTO.getTag());
        novaIncubadora.setColor(incubadoraDTO.getColor());
        novaIncubadora.setIcon(incubadoraDTO.getIcon());
        novaIncubadora.setAtivo(incubadoraDTO.isAtivo());
        return ResponseEntity.ok(incubadoraRepository.save(novaIncubadora));
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!incubadoraRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Incubadora não encontrada"));
        }

        boolean isIncubadoraVinculadaStartup = incubadoraRepository.existsIncubadoraVinculadaStartup(id);
        if (isIncubadoraVinculadaStartup) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(java.util.Collections.singletonMap("erro", "Incubadora está vinculada a uma Startup e não pode ser deletada"));
        }

        incubadoraRepository.deleteById(id);
        return ResponseEntity.ok("Incubadora deletada com sucesso");
    }

}
