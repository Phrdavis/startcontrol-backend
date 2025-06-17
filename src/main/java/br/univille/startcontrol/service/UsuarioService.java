package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.univille.startcontrol.components.JwtUtils;
import br.univille.startcontrol.dto.UsuarioDTO;
import br.univille.startcontrol.model.Usuario;
import br.univille.startcontrol.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<?> criar(UsuarioDTO usuario) {
        String email = usuario.getEmail();
        Usuario user_found = usuarioRepository.findByEmail(email);

        if (user_found != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado no sistema!");
        }

        Usuario user = new Usuario();
        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());
        user.setSenha(usuario.getSenha());
        user.setTipo(usuario.getTipo());
        Usuario savedUser = usuarioRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuario);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTipo(usuarioDTO.getTipo());
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    public String authenticate(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(password)) {
            long id = usuario.getId();
            return jwtUtils.generateJwtToken(email, id);
        } else {
            throw new RuntimeException("E-mail e senha informados não conferem");
        }
    }
}
