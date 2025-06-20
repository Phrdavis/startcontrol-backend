package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.univille.startcontrol.dto.StartupDTO;
import br.univille.startcontrol.model.Startup;
import br.univille.startcontrol.repository.StartupRepository;
import br.univille.startcontrol.repository.UsuarioRepository;

@Service
public class StartupService {

    private final StartupRepository startupRepository;
    private final UsuarioRepository usuarioRepository;

    public StartupService(StartupRepository startupRepository, UsuarioRepository usuarioRepository) {
        this.startupRepository = startupRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<?> criarMultiplas(List<StartupDTO> startups) {
        if (startups == null || startups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de startups vazia"));
        }
        List<Startup> novasStartups = new java.util.ArrayList<>();
        for (StartupDTO dto : startups) {
            if (dto.getResponsavel() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Responsável não informado para uma das startups"));
            }
            var responsavel = usuarioRepository.findById(dto.getResponsavel().getId()).orElse(null);
            if (responsavel == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Responsável não encontrado para uma das startups"));
            }
            Startup novaStartup = new Startup();
            novaStartup.setId(dto.getId());
            novaStartup.setNome(dto.getNome());
            novaStartup.setCnpj(dto.getCnpj());
            novaStartup.setAreaAtuacao(dto.getAreaAtuacao());
            novaStartup.setResponsavel(responsavel);
            novaStartup.setAtivo(true);
            novasStartups.add(novaStartup);
        }
        List<Startup> salvas = startupRepository.saveAll(novasStartups);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvas);
    }

    public ResponseEntity<?> criar(StartupDTO startup) {
        Startup novaStartup = new Startup();
        novaStartup.setNome(startup.getNome());
        novaStartup.setCnpj(startup.getCnpj());
        novaStartup.setAreaAtuacao(startup.getAreaAtuacao());
        if (startup.getResponsavel() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Responsável não informado"));
        }
        var responsavel = usuarioRepository.findById(startup.getResponsavel().getId()).orElse(null);
        if (responsavel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Responsável não encontrado"));
        }
        novaStartup.setResponsavel(responsavel);
        novaStartup.setAtivo(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(startupRepository.save(novaStartup));
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Startup startup = startupRepository.findById(id).orElse(null);
        if (startup == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Startup não encontrada"));
        }
        return ResponseEntity.ok(startup);
    }

    public List<Startup> buscarTodos() {
        return startupRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, StartupDTO startupDTO) {
        Startup novaStartup = startupRepository.findById(id).orElse(null);
        if (novaStartup == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Startup não encontrada"));
        }
        novaStartup.setNome(startupDTO.getNome());
        novaStartup.setCnpj(startupDTO.getCnpj());
        novaStartup.setAreaAtuacao(startupDTO.getAreaAtuacao());
        novaStartup.setResponsavel(startupDTO.getResponsavel());
        novaStartup.setAtivo(startupDTO.isAtivo());
        return ResponseEntity.ok(startupRepository.save(novaStartup));
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!startupRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Startup não encontrada"));
        }
        startupRepository.deleteById(id);
        return ResponseEntity.ok("Startup deletada com sucesso");
    }

}
