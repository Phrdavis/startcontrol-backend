package br.univille.startcontrol.model;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Incubadora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String descricao;
    private String tag;
    private String color;
    private String icon;

    @Nullable
    private long totalStartups;
    private boolean ativo = true;

    // Getters and Setters
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getTotalStartups(){
        return totalStartups;
    }

    public void setTotalStartups(long totalStartups){
        this.totalStartups = totalStartups;
    }

    @Override
    public String toString() {
        return "Incubadora{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tag='" + tag + '\'' +
                ", color='" + color + '\'' +
                ", icon='" + icon + '\'' +
                ", totalStartups='" + totalStartups + '\'' +
                ", ativo=" + ativo +
                '}';
    }

}
