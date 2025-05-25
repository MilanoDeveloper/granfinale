package br.com.fiap.granfinale.model;

import java.util.Date;
import java.util.List;

public class Despesa {
    private int id;
    private String descricao;
    private double valor;
    private Date data;
    private Grupo grupo;
    private Participante pagador;
    private List<DespesaDivisao> divisoes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Participante getPagador() {
        return pagador;
    }

    public void setPagador(Participante pagador) {
        this.pagador = pagador;
    }

    public List<DespesaDivisao> getDivisoes() {
        return divisoes;
    }

    public void setDivisoes(List<DespesaDivisao> divisoes) {
        this.divisoes = divisoes;
    }
}
