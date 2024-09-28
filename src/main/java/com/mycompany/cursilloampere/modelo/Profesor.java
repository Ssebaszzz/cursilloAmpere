/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cursilloampere.modelo;

import com.mycompany.cursilloampere.clases.conexion;
import com.mycompany.cursilloampere.clases.sentencias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Alvarito
 */
public class Profesor extends conexion implements sentencias{
    private int id;
    private int ci;
    private String nombre;
    private String apellido;
    private int tel;
    private String correo;

    private Profesor(int id, int ci, String nombre, String apellido, int tel, String correo) {
         this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tel = tel;
        this.correo = correo;
    }

    public Profesor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean insertar() {
         String sql = "insert into profesor (Cedula, Nombre, Apellido, Telefono , Correo)  values(?,?,?,?,?)";
        try (
            Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.ci);
            stm.setString(2, this.nombre);
            stm.setString(3, this.apellido);
            stm.setInt(4, this.tel);
            stm.setString(5, this.correo);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
         ArrayList<Profesor> Profesores = new ArrayList<>();
        String sql = "select * from profesor";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int ci = rs.getInt("Cedula");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                int tel = rs.getInt("Telefono");
                String correo = rs.getString("Correo");
                Profesor p = new Profesor(id, ci, nombre, apellido, tel,correo);
                Profesores.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Profesores;
    }

    @Override
    public boolean modificar() {
        String sql =" UPDATE profesor set Cedula=?, Nombre=?, Apellido=?, Telefono=?, Correo=? WHERE id = ?";
        try (
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql)
            ){
            stm.setInt(1, this.ci);
            stm.setString(2, this.nombre);
            stm.setString(3, this.apellido);
            stm.setInt(4, this.tel);
            stm.setString(5, this.correo);
            stm.setInt(6,this.id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
         String sql="Delete from profesor where id=?";
        try (
            Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    
}

