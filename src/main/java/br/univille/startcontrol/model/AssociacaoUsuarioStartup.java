package br.univille.startcontrol.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AssociacaoUsuarioStartup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;
    
    @ManyToOne
    @JoinColumn(name = "startup_id", nullable = false)
    private Startup startupId;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "AssociacaoUsuarioStartup{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", startupId=" + startupId +
                '}';
    }

}
