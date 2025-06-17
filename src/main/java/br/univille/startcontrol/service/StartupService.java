package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.univille.startcontrol.dto.StartupDTO;
import br.univille.startcontrol.model.Startup;
import br.univille.startcontrol.repository.StartupRepository;

@Service
public class StartupService {

    private final StartupRepository startupRepository;

    public StartupService(StartupRepository startupRepository) {
        this.startupRepository = startupRepository;
    }

    public ResponseEntity<?> criar(StartupDTO startup) {
        Startup novaStartup = new Startup();
        novaStartup.setNome(startup.getNome());
        novaStartup.setCnpj(startup.getCnpj());
        novaStartup.setAreaAtuacao(startup.getAreaAtuacao());
        novaStartup.setResponsavel(startup.getResponsavel());
        return ResponseEntity.status(HttpStatus.CREATED).body(startupRepository.save(novaStartup));
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Startup startup = startupRepository.findById(id).orElse(null);
        if (startup == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Startup não encontrada");
        }
        return ResponseEntity.ok(startup);
    }

    public List<Startup> buscarTodos() {
        return startupRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, StartupDTO startupDTO) {
        Startup novaStartup = startupRepository.findById(id).orElse(null);
        if (novaStartup == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Startup não encontrada");
        }
        novaStartup.setNome(startupDTO.getNome());
        novaStartup.setCnpj(startupDTO.getCnpj());
        novaStartup.setAreaAtuacao(startupDTO.getAreaAtuacao());
        novaStartup.setResponsavel(startupDTO.getResponsavel());
        return ResponseEntity.ok(startupRepository.save(novaStartup));
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!startupRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Startup não encontrada");
        }
        startupRepository.deleteById(id);
        return ResponseEntity.ok("Startup deletada com sucesso");
    }

}
