package br.com.fiap.granfinale.model;

public class DespesaDivisao {
    private Participante participante;
    private double valor;

    public DespesaDivisao() {
    }

    public DespesaDivisao(Participante participante, double valor) {
        this.participante = participante;
        this.valor = valor;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
