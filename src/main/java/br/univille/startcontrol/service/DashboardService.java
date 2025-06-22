package br.univille.startcontrol.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.univille.startcontrol.repository.UsuarioRepository;
import br.univille.startcontrol.repository.ProjetoRepository;
import br.univille.startcontrol.repository.StartupRepository;

@Service
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final StartupRepository startupRepository;

    public DashboardService(
        UsuarioRepository usuarioRepository,
        ProjetoRepository projetoRepository,
        StartupRepository startupRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.startupRepository = startupRepository;
    }

    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();

        // Usuários ativos
        long usuariosAtivos = usuarioRepository.findAll()
            .stream()
            .filter(u -> u.getAtivo())
            .count();

        // Total de projetos
        long totalProjetos = projetoRepository.count();

        // Total de startups
        long totalStartups = startupRepository.count();

        // Startups ativas
        long startupsAtivas = startupRepository.findAll()
            .stream()
            .filter(s -> s.isAtivo())
            .count();

        List<Object[]> countProjetosByStatus = projetoRepository.countProjetosByStatus();
        
        data.put("countProjetosByStatus", countProjetosByStatus);
        data.put("usuariosAtivos", usuariosAtivos);
        data.put("totalProjetos", totalProjetos);
        data.put("totalStartups", totalStartups);
        data.put("startupsAtivas", startupsAtivas);

        return data;
    }
}
