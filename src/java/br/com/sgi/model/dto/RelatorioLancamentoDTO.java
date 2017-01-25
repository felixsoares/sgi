package br.com.sgi.model.dto;

public class RelatorioLancamentoDTO {
    
    private Double valorDespesa;
    private Double valorReceita;

    public RelatorioLancamentoDTO() {
    }

    public Double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(Double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public Double getValorReceita() {
        return valorReceita;
    }

    public void setValorReceita(Double valorReceita) {
        this.valorReceita = valorReceita;
    }   
}
