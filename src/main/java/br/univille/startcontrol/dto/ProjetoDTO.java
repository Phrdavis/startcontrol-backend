package br.univille.startcontrol.dto;

import br.univille.startcontrol.model.Usuario;

public class ProjetoDTO {
    
    private String nome;
    private String descricao;
    private Usuario responsavel;
    private String status;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProjetoDTO{" +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", responsavel=" + responsavel +
                ", status='" + status + '\'' +
                '}';
    }

}
