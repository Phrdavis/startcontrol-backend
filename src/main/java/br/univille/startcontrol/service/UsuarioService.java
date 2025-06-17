package br.univille.startcontrol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.univille.startcontrol.components.JwtUtils;
import br.univille.startcontrol.components.PasswordUtils;
import br.univille.startcontrol.dto.UsuarioDTO;
import br.univille.startcontrol.model.SessaoUsuario;
import br.univille.startcontrol.model.Usuario;
import br.univille.startcontrol.repository.SessaoUsuarioRepository;
import br.univille.startcontrol.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final SessaoUsuarioRepository sessaoUsuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public UsuarioService(UsuarioRepository usuarioRepository, SessaoUsuarioRepository sessaoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessaoUsuarioRepository = sessaoUsuarioRepository;
    }

    public ResponseEntity<?> criar(UsuarioDTO usuario) {
        String email = usuario.getEmail();
        Usuario user_found = usuarioRepository.findByEmail(email);

        if (user_found != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(java.util.Collections.singletonMap("erro", "Usuário já cadastrado no sistema!"));
        }

        Usuario user = new Usuario();
        String senhaCriptografada = PasswordUtils.encryptPassword(usuario.getSenha());
        user.setSenha(senhaCriptografada);

        user.setNome(usuario.getNome());
        user.setEmail(usuario.getEmail());
        user.setTipo(usuario.getTipo());
        Usuario savedUser = usuarioRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    public ResponseEntity<?> buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Usuário não encontrado"));
        }
        return ResponseEntity.ok(usuario);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public ResponseEntity<?> atualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Usuário não encontrado"));
        }
        String senhaCriptografada = PasswordUtils.encryptPassword(usuarioDTO.getSenha());
        usuario.setSenha(senhaCriptografada);

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTipo(usuarioDTO.getTipo());
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Collections.singletonMap("erro", "Usuário não encontrado"));
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    public String authenticate(UsuarioDTO usuario) {
        Usuario user_found = usuarioRepository.findByEmail(usuario.getEmail());

        if (user_found != null && PasswordUtils.matches(usuario.getSenha(), user_found.getSenha())) {
            long id = user_found.getId();
            String token = jwtUtils.generateJwtToken(usuario.getEmail(), id);

            SessaoUsuario sessao = new SessaoUsuario();
            sessao.setToken(token);
            sessao.setUsuarioId(user_found);
            // Obtém o IP de origem da requisição atual
            String ip = "";
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                ip = request.getHeader("X-Forwarded-For");
                if (ip == null || ip.isEmpty()) {
                    ip = request.getRemoteAddr();
                }
            }
            sessao.setIp(ip);
            sessao.setDataExpiracao(java.time.LocalDateTime.now().plusHours(3));
            sessao.setAtivo(true);

            sessaoUsuarioRepository.save(sessao);

            return token;
        } else {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }
}
