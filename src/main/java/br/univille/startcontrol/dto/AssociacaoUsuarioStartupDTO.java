package br.univille.startcontrol.dto;

import br.univille.startcontrol.model.Startup;
import br.univille.startcontrol.model.Usuario;

public class AssociacaoUsuarioStartupDTO {

    private Usuario usuarioId;

    private Startup startupId;

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Startup getStartupId() {
        return startupId;
    }

    public void setStartupId(Startup startupId) {
        this.startupId = startupId;
    }

}
