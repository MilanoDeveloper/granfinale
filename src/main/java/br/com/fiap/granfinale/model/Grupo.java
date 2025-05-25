
package br.com.fiap.granfinale.model;

import java.util.List;

public class Grupo {
    private int id;
    private String nome;
    private List<Participante> membros;

    public Grupo() {
    }

    public Grupo(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Participante> getMembros() {
        return membros;
    }

    public void setMembros(List<Participante> membros) {
        this.membros = membros;
    }
}
