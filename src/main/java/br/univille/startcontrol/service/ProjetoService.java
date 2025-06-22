package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.univille.startcontrol.dto.ProjetoDTO;
import br.univille.startcontrol.dto.ProjetoDTO;
import br.univille.startcontrol.model.Projeto;
import br.univille.startcontrol.model.Projeto;
import br.univille.startcontrol.repository.ProjetoRepository;
import br.univille.startcontrol.repository.StartupRepository;
import br.univille.startcontrol.repository.UsuarioRepository;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final StartupRepository startupRepository;

    public ProjetoService(ProjetoRepository projetoRepository, UsuarioRepository usuarioRepository, StartupRepository startupRepository){

        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.startupRepository = startupRepository;

    }

    public ResponseEntity<?> criar(ProjetoDTO projeto){
        Projeto newProjeto = new Projeto();
        newProjeto.setNome(projeto.getNome());
        if (projeto.getResponsavel() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Responsável não informado"));
        }
        var responsavel = usuarioRepository.findById(projeto.getResponsavel().getId()).orElse(null);
        if (responsavel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Responsável não encontrado"));
        }
        if (projeto.getStartup() != null){
            var startup = startupRepository.findById(projeto.getStartup().getId()).orElse(null);
            if (startup == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Startup não encontrada"));
            }
            newProjeto.setStartup(projeto.getStartup());
        }
        newProjeto.setResponsavel(projeto.getResponsavel());
        newProjeto.setDescricao(projeto.getDescricao());
        newProjeto.setStatus(projeto.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoRepository.save(newProjeto));
    }

    public ResponseEntity<?> criarMultiplos(List<ProjetoDTO> projetos) {
        if (projetos == null || projetos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de projetos vazia"));
        }
        List<Projeto> novosProjetos = new java.util.ArrayList<>();
        for (ProjetoDTO dto : projetos) {
            if (dto.getResponsavel() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Responsável não informado para um dos projetos"));
            }
            var responsavel = usuarioRepository.findById(dto.getResponsavel().getId()).orElse(null);
            if (responsavel == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Responsável não encontrado para um dos projetos"));
            }
            Projeto novoProjeto = new Projeto();
            novoProjeto.setNome(dto.getNome());
            novoProjeto.setDescricao(dto.getDescricao());
            novoProjeto.setStatus(dto.getStatus());
            novoProjeto.setResponsavel(dto.getResponsavel());
            if (dto.getStartup() != null) {
                var startup = startupRepository.findById(dto.getStartup().getId()).orElse(null);
                if (startup == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(java.util.Collections.singletonMap("erro", "Startup não encontrada para um dos projetos"));
                }
                novoProjeto.setStartup(dto.getStartup());
            }
            novosProjetos.add(novoProjeto);
        }
        List<Projeto> salvos = projetoRepository.saveAll(novosProjetos);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        if (projeto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Projeto não encontrado"));
        }
        return ResponseEntity.ok(projeto);
    }

    public List<Projeto> buscarTodos() {
        return projetoRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, ProjetoDTO projetoDTO) {
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        if (projeto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Projeto não encontrado"));
        }
        if(projetoDTO.getNome() != null){
            projeto.setNome(projetoDTO.getNome());
        }
        if (projetoDTO.getDescricao() != null) {
            projeto.setDescricao(projetoDTO.getDescricao());
        }
        if (projetoDTO.getResponsavel() != null) {
            var responsavel = usuarioRepository.findById(projetoDTO.getResponsavel().getId()).orElse(null);
            if (responsavel == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Responsável não encontrado"));
            }
            projeto.setResponsavel(projetoDTO.getResponsavel());
        }
        if (projetoDTO.getStartup() != null){
            var startup = startupRepository.findById(projetoDTO.getStartup().getId()).orElse(null);
            if (startup == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(java.util.Collections.singletonMap("erro", "Startup não encontrada"));
            }
            projeto.setStartup(projetoDTO.getStartup());
        }
        if (projetoDTO.getStatus() != null) {
            projeto.setStatus(projetoDTO.getStatus());
        }
        return ResponseEntity.ok(projetoRepository.save(projeto));
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!projetoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Projeto não encontrado"));
        }
        projetoRepository.deleteById(id);
        return ResponseEntity.ok("Projeto deletada com sucesso");
    }
    
}
