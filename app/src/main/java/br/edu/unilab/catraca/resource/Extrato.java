package br.edu.unilab.catraca.resource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by erivando on 25/12/2016.
 */

public class Extrato implements Serializable {

    private static final long serialVersionUID = 4260711945094777831L;

    private ArrayList<Extrato> extrato;

    private String descricao;
    private String data;
    private String local;
    private String valor;

    public Extrato() {
    }

    public Extrato(String descricao, String data, String local, String valor) {
        this.setDescricao(descricao);
        this.setData(data);
        this.setLocal(local);
        this.setValor(valor);
    }

    public Extrato(ArrayList<Extrato> extrato, String descricao, String data, String local, String valor) {
        this.setExtrato(extrato);
        this.setDescricao(descricao);
        this.setData(data);
        this.setLocal(local);
        this.setValor(valor);
    }

    public ArrayList<Extrato> getExtrato() {
        return extrato;
    }

    public void setExtrato(ArrayList<Extrato> extrato) {
        this.extrato=extrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao=descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data=data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local=local;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor=valor;
    }

    @Override
    public String toString() {
//        return "Extrato{" +
//                "descricao=" + descricao +
//                ", data='" + data + '\'' +
//                ", valor=" + valor +
//                '}';
        return "{'extrato':[{'descricao':"+ descricao +",'data':"+ data +",'local':\"+ local,'valor':"+ valor +"}]}";
    }
}
