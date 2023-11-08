package br.edu.unilab.catraca.resource;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Erivando Sena on 24/11/2016.
 */


public class Usuario implements Serializable {

    private static final long serialVersionUID = 4260711945094777831L;

    private ArrayList<Usuario> usuario;

    private int usua_id;
    private String usua_nome;
    private String usua_email;
    private String usua_login;
    private String usua_senha;
    private String usua_nivel;
    private int id_base_externa;

    public Usuario() {
    }

    public Usuario(ArrayList<Usuario> usuario, int usua_id, String usua_nome, String usua_email, String usua_login, String usua_senha, String usua_nivel, int id_base_externa) {
        this.setUsuario(usuario);
        this.usua_id=usua_id;
        this.usua_nome=usua_nome;
        this.usua_email=usua_email;
        this.usua_login=usua_login;
        this.usua_senha=usua_senha;
        this.usua_nivel=usua_nivel;
        this.id_base_externa=id_base_externa;
    }

    public ArrayList<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(ArrayList<Usuario> usuario) {
        this.usuario=usuario;
    }

    public int getUsua_id() {
        return usua_id;
    }

    public void setUsua_id(int usua_id) {
        this.usua_id = usua_id;
    }

    public String getUsua_nome() {
        return usua_nome;
    }

    public void setUsua_nome(String usua_nome) {
        this.usua_nome = usua_nome;
    }

    public String getUsua_email() {
        return usua_email;
    }

    public void setUsua_email(String usua_email) {
        this.usua_email = usua_email;
    }

    public String getUsua_login() {
        return usua_login;
    }

    public void setUsua_login(String usua_login) {
        this.usua_login = usua_login;
    }

    public String getUsua_senha() {
        return usua_senha;
    }

    public void setUsua_senha(String usua_senha) {
        this.usua_senha = usua_senha;
    }

    public String getUsua_nivel() {
        return usua_nivel;
    }

    public void setUsua_nivel(String usua_nivel) {
        this.usua_nivel = usua_nivel;
    }

    public int getId_base_externa() {
        return id_base_externa;
    }

    public void setId_base_externa(int id_base_externa) {
        this.id_base_externa = id_base_externa;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usua_id=" + usua_id +
                ", usua_nome='" + usua_nome + '\'' +
                ", usua_email='" + usua_email + '\'' +
                ", usua_login='" + usua_login + '\'' +
                ", usua_senha='" + usua_senha + '\'' +
                ", usua_nivel='" + usua_nivel + '\'' +
                ", id_base_externa=" + id_base_externa +
                '}';
    }

}
