package br.com.sgi.model.dto;

import java.util.Date;

public class RelatorioMembroDTO {
    
    private String nome;
    private String data;
    private Double valor;

    public RelatorioMembroDTO(){
        
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
