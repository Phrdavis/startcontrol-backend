package br.univille.startcontrol.dto;

public class IncubadoraDTO {

    private String nome;
    private String descricao;
    private String tag;
    private String icon;
    private String color;
    private boolean ativo = true;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    @Override
    public String toString() {
        return "Incubadora{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tag='" + tag + '\'' +
                ", color='" + color + '\'' +
                ", icon='" + icon + '\'' +
                ", ativo=" + ativo +
                '}';
    }
    
}
