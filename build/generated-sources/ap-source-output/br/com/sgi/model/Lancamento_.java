package br.com.sgi.model;

import br.com.sgi.model.Categoria;
import br.com.sgi.model.Conta;
import br.com.sgi.model.Membro;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-09T23:20:49")
@StaticMetamodel(Lancamento.class)
public class Lancamento_ { 

    public static volatile SingularAttribute<Lancamento, Date> dataLancada;
    public static volatile SingularAttribute<Lancamento, Integer> tipo;
    public static volatile SingularAttribute<Lancamento, Categoria> categoria;
    public static volatile SingularAttribute<Lancamento, Double> valor;
    public static volatile SingularAttribute<Lancamento, Membro> membro;
    public static volatile SingularAttribute<Lancamento, Conta> conta;
    public static volatile SingularAttribute<Lancamento, Integer> id;
    public static volatile SingularAttribute<Lancamento, Boolean> pagoRecebido;
    public static volatile SingularAttribute<Lancamento, String> descricao;

}