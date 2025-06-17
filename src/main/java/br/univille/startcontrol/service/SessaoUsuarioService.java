package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.univille.startcontrol.dto.SessaoUsuarioDTO;
import br.univille.startcontrol.model.SessaoUsuario;
import br.univille.startcontrol.repository.SessaoUsuarioRepository;

@Service
public class SessaoUsuarioService {

    private final SessaoUsuarioRepository sessaoUsuarioRepository;

    public SessaoUsuarioService(SessaoUsuarioRepository sessaoUsuarioRepository) {
        this.sessaoUsuarioRepository = sessaoUsuarioRepository;
    }

    public ResponseEntity<?> criar(SessaoUsuarioDTO sessaoUsuarioDTO) {
        SessaoUsuario sessaoUsuario = new SessaoUsuario();
        sessaoUsuario.setUsuarioId(sessaoUsuarioDTO.getUsuarioId());
        sessaoUsuario.setToken(sessaoUsuarioDTO.getToken());
        sessaoUsuario.setIp(sessaoUsuarioDTO.getIp());
        sessaoUsuario.setDataExpiracao(sessaoUsuarioDTO.getDataExpiracao());
        sessaoUsuario.setAtivo(sessaoUsuarioDTO.isAtivo());
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoUsuarioRepository.save(sessaoUsuario));
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        SessaoUsuario sessaoUsuario = sessaoUsuarioRepository.findById(id).orElse(null);
        if (sessaoUsuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sessão de usuário não encontrada");
        }
        return ResponseEntity.ok(sessaoUsuario);
    }

    public List<SessaoUsuario> buscarTodos() {
        return sessaoUsuarioRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, SessaoUsuarioDTO sessaoUsuarioDTO) {
        SessaoUsuario sessaoUsuario = sessaoUsuarioRepository.findById(id).orElse(null);
        if (sessaoUsuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sessão de usuário não encontrada");
        }
        sessaoUsuario.setToken(sessaoUsuarioDTO.getToken());
        sessaoUsuario.setIp(sessaoUsuarioDTO.getIp());
        sessaoUsuario.setDataExpiracao(sessaoUsuarioDTO.getDataExpiracao());
        sessaoUsuario.setAtivo(sessaoUsuarioDTO.isAtivo());
        return ResponseEntity.ok(sessaoUsuarioRepository.save(sessaoUsuario));
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!sessaoUsuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sessão de usuário não encontrada");
        }
        sessaoUsuarioRepository.deleteById(id);
        return ResponseEntity.ok("Sessão de usuário deletada com sucesso");
    }

}
