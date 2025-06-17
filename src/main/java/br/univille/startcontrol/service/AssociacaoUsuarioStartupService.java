package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.univille.startcontrol.dto.AssociacaoUsuarioStartupDTO;
import br.univille.startcontrol.model.AssociacaoUsuarioStartup;
import br.univille.startcontrol.repository.AssociacaoUsuarioStartupRepository;

@Service
public class AssociacaoUsuarioStartupService {

    private final AssociacaoUsuarioStartupRepository associacaoRepository;

    public AssociacaoUsuarioStartupService(AssociacaoUsuarioStartupRepository associacaoRepository) {
        this.associacaoRepository = associacaoRepository;
    }

    public ResponseEntity<?> criar(AssociacaoUsuarioStartupDTO associacaoDTO) {
        AssociacaoUsuarioStartup associacao = new AssociacaoUsuarioStartup();
        associacao.setUsuarioId(associacaoDTO.getUsuarioId());
        associacao.setStartupId(associacaoDTO.getStartupId());
        return ResponseEntity.status(HttpStatus.CREATED).body(associacaoRepository.save(associacao));
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        AssociacaoUsuarioStartup associacao = associacaoRepository.findById(id).orElse(null);
        if (associacao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associação não encontrada");
        }
        return ResponseEntity.ok(associacao);
    }

    public List<AssociacaoUsuarioStartup> buscarTodos() {
        return associacaoRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, AssociacaoUsuarioStartupDTO associacaoDTO) {
        AssociacaoUsuarioStartup associacao = associacaoRepository.findById(id).orElse(null);
        if (associacao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associação não encontrada");
        }
        associacao.setUsuarioId(associacaoDTO.getUsuarioId());
        associacao.setStartupId(associacaoDTO.getStartupId());
        return ResponseEntity.ok(associacaoRepository.save(associacao));
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!associacaoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associação não encontrada");
        }
        associacaoRepository.deleteById(id);
        return ResponseEntity.ok("Associação deletada com sucesso");
    }

}
