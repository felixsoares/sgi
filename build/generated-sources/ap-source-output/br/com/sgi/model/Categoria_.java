package br.com.sgi.model;

import br.com.sgi.model.Lancamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-05T20:50:28")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile ListAttribute<Categoria, Lancamento> lancamentoList;
    public static volatile SingularAttribute<Categoria, Integer> id;
    public static volatile SingularAttribute<Categoria, String> descricao;

}