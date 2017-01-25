package br.com.sgi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lancamento", catalog = "sgi_db", schema = "")
@NamedQueries({
    @NamedQuery(name = "Lancamento.findAll", query = "SELECT l FROM Lancamento l")
    , @NamedQuery(name = "Lancamento.findById", query = "SELECT l FROM Lancamento l WHERE l.id = :id")
    , @NamedQuery(name = "Lancamento.findByDescricao", query = "SELECT l FROM Lancamento l WHERE l.descricao = :descricao")
    , @NamedQuery(name = "Lancamento.findByValor", query = "SELECT l FROM Lancamento l WHERE l.valor = :valor")
    , @NamedQuery(name = "Lancamento.findByTipo", query = "SELECT l FROM Lancamento l WHERE l.tipo = :tipo")
    , @NamedQuery(name = "Lancamento.findByDataLancada", query = "SELECT l FROM Lancamento l WHERE l.dataLancada = :dataLancada")
    , @NamedQuery(name = "Lancamento.findByPagoRecebido", query = "SELECT l FROM Lancamento l WHERE l.pagoRecebido = :pagoRecebido")})
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static Integer TIPO_DESPESA = 0;
    public static Integer TIPO_RECEITA = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private int id;

    @Basic(optional = false)
    @Column(name = "descricao")
    private String descricao;

    @Basic(optional = false)
    @Column(name = "valor")
    private Double valor;

    @Basic(optional = false)
    @Column(name = "tipo")
    private Integer tipo;

    @Basic(optional = false)
    @Column(name = "dataLancada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLancada;

    @Basic(optional = false)
    @Column(name = "pagoRecebido")
    private Boolean pagoRecebido;

    @JoinColumn(name = "id_categoria", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    @JoinColumn(name = "id_membro", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Membro membro;
    
    @JoinColumn(name = "id_conta", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta conta;

    public Lancamento() {
    }

    public Lancamento(Integer id) {
        this.id = id;
    }

    public Lancamento(Integer id, String descricao, double valor, int tipo, Date dataLancada, boolean pagoRecebido) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.dataLancada = dataLancada;
        this.pagoRecebido = pagoRecebido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Date getDataLancada() {
        return dataLancada;
    }

    public void setDataLancada(Date dataLancada) {
        this.dataLancada = dataLancada;
    }

    public Boolean getPagoRecebido() {
        return pagoRecebido;
    }

    public void setPagoRecebido(Boolean pagoRecebido) {
        this.pagoRecebido = pagoRecebido;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Membro getMembro() {
        return membro;
    }

    public void setMembro(Membro membro) {
        this.membro = membro;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
