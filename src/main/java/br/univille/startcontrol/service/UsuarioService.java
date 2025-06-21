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
        user.setAtivo(true);
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

        if (usuarioDTO.getNome() != null) {
            usuario.setNome(usuarioDTO.getNome());
        }
        if (usuarioDTO.getEmail() != null) {
            usuario.setEmail(usuarioDTO.getEmail());
        }
        if (usuarioDTO.getTipo() != null) {
            usuario.setTipo(usuarioDTO.getTipo());
        }
        if (usuarioDTO.getAtivo() != null) {
            usuario.setAtivo(usuarioDTO.getAtivo());
        }
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            String senhaCriptografada = PasswordUtils.encryptPassword(usuarioDTO.getSenha());
            usuario.setSenha(senhaCriptografada);
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    public ResponseEntity<?> deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(java.util.Collections.singletonMap("erro", "Usuário não encontrado"));
        }

        // Verifica se o usuário está vinculado como responsável em alguma startup
        boolean isResponsavel = usuarioRepository.existsUsuarioAsResponsavelInStartup(id);
        if (isResponsavel) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(java.util.Collections.singletonMap("erro", "Usuário está vinculado como responsável em uma startup e não pode ser deletado"));
        }

        // Verifica se o usuário está vinculado em alguma associação
        boolean isAssociado = usuarioRepository.existsUsuarioAsAssociadoInAssociacao(id);
        if (isAssociado) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(java.util.Collections.singletonMap("erro", "Usuário está vinculado em uma associação e não pode ser deletado"));
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    public String authenticate(UsuarioDTO usuario) {
        Usuario user_found = usuarioRepository.findByEmail(usuario.getEmail());

        if (user_found != null && PasswordUtils.matches(usuario.getSenha(), user_found.getSenha())) {

            if(user_found.getAtivo() == false){
                throw new RuntimeException("Usuário Inativo!");
            }

            long id = user_found.getId();
            String token = jwtUtils.generateJwtToken(user_found);

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

    public ResponseEntity<?> criarMultiplos(List<UsuarioDTO> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Lista de usuários vazia"));
        }
        List<Usuario> novosUsuarios = new java.util.ArrayList<>();
        for (UsuarioDTO dto : usuarios) {
            if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(java.util.Collections.singletonMap("erro", "Email não informado para um dos usuários"));
            }
            if (usuarioRepository.findByEmail(dto.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(java.util.Collections.singletonMap("erro", "Usuário já cadastrado: " + dto.getEmail()));
            }
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(dto.getNome());
            novoUsuario.setEmail(dto.getEmail());
            novoUsuario.setTipo(dto.getTipo());
            novoUsuario.setAtivo(true);
            String senhaCriptografada = PasswordUtils.encryptPassword(dto.getSenha());
            novoUsuario.setSenha(senhaCriptografada);
            novosUsuarios.add(novoUsuario);
        }
        List<Usuario> salvos = usuarioRepository.saveAll(novosUsuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvos);
    }

    public ResponseEntity<?> trashPasswordRecovery(UsuarioDTO usuarioDTO) {
        String email = usuarioDTO.getEmail();
        String novaSenha = usuarioDTO.getSenha();
        if (email == null || email.isEmpty() || novaSenha == null || novaSenha.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Collections.singletonMap("erro", "Email e nova senha devem ser informados"));
        }

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(java.util.Collections.singletonMap("erro", "E-mail não cadastrado"));
        }

        String senhaCriptografada = PasswordUtils.encryptPassword(novaSenha);
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(java.util.Collections.singletonMap("mensagem", "Senha alterada com sucesso"));
    }
}
