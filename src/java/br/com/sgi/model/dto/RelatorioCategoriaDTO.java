package br.com.sgi.model.dto;

public class RelatorioCategoriaDTO {
    private String nome;
    private Double valor;
    
    public RelatorioCategoriaDTO(){
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
