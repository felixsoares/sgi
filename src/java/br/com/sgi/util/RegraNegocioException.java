package br.com.sgi.util;

import br.com.sgi.model.Mensagem;
import java.util.ArrayList;
import java.util.List;

public class RegraNegocioException extends Exception{
    
    private List<Mensagem> mensagens = new ArrayList<Mensagem>();

    public RegraNegocioException(List<Mensagem> mensagens) {
        super();
        setMensagem(mensagens);
    }
    
    public RegraNegocioException(String mensagem, String tipo) {
        super();
        setMensagem(mensagem, tipo);
    }
    
    public RegraNegocioException(String mensagem){
        super();
        setMensagem(mensagem);
    }

    public void setMensagem(List<Mensagem> mensagens) {
        this.mensagens.addAll(mensagens);
    }
    
    public void setMensagem(String mensagem, String tipo) {
        this.mensagens.add(new Mensagem(mensagem, tipo));
    }
 
    public void setMensagem(String mensagem) {
        this.mensagens.add(new Mensagem(mensagem));
    }
    
    public List<Mensagem> getMensagens(){
        if(mensagens.size() > 0){
            return this.mensagens;
        }
        return null;
    }
}
