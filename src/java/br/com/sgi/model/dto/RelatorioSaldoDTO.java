package br.com.sgi.model.dto;

import br.com.sgi.model.Conta;

public class RelatorioSaldoDTO {
    
    private Conta conta;
    private RelatorioLancamentoDTO lancamentoDTO;

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public void setLancamentoDTO(RelatorioLancamentoDTO lancamentoDTO) {
        this.lancamentoDTO = lancamentoDTO;
    }

    public RelatorioLancamentoDTO getLancamentoDTO() {
        return lancamentoDTO;
    }
}
