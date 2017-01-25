package br.com.sgi.model;

import java.io.Serializable;

public class Mensagem implements Serializable{

    public static String TIPO_SUCESSO = "success";
    public static String TIPO_ERRO = "error";
    
    private String tipo;
    private String mensagem;

    public Mensagem(String mensagem){
        this.mensagem = mensagem;
    }
    
    public Mensagem(String menssagem, String tipo){
        this.tipo = tipo;
        this.mensagem = menssagem;
    }
    
    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the menssagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * @param mensagem the menssagem to set
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
}
