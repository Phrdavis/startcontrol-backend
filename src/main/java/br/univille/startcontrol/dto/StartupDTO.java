package br.univille.startcontrol.dto;

import br.univille.startcontrol.model.Incubadora;
import br.univille.startcontrol.model.Usuario;

public class StartupDTO {

    private long id;

    private String nome;

    private String cnpj;
    
    private String areaAtuacao;
    
    private Usuario responsavel;

    private Incubadora incubadora;

    private boolean ativo = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public Incubadora getIncubadora() {
        return incubadora;
    }

    public void setIncubadora(Incubadora incubadora) {
        this.incubadora = incubadora;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
