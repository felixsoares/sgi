package br.com.sgi.model;

import br.com.sgi.model.Lancamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-15T14:47:27")
@StaticMetamodel(Membro.class)
public class Membro_ { 

    public static volatile ListAttribute<Membro, Lancamento> lancamentos;
    public static volatile SingularAttribute<Membro, String> nome;
    public static volatile SingularAttribute<Membro, Integer> id;

}