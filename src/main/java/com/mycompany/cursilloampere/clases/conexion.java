/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cursilloampere.clases;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author sebas
 */
public class conexion {
    private String base;
    private String host;
    private String usuario;
    private String contra;
    Connection con;
    public conexion() {
        this.base ="cursilloampere";
        this.host = "localhost";
        this.usuario = "root";
        this.contra = "";
    }

    public Connection getCon() {
        try {
            String url="jdbc:mysql://"+host+"/"+base;
            con=DriverManager.getConnection(url,usuario,contra);
            System.out.println("conexion exitosa");
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no conectado"+ex);
        }
        return con;
    }

    public conexion(String base, String host, String usuario, String contra) {
        this.base = base;
        this.host = host;
        this.usuario = usuario;
        this.contra = contra;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
    
}

