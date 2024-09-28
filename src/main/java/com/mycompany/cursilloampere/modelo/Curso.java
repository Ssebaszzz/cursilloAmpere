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
 * @author sebas
 */
public class Curso extends conexion implements sentencias {

    private int id;
    private String Nombre;
    private Double Matricula;
    private Double Costo;
    private int Duracion;
    private int Aula;

    public Curso() {
    }

    public Curso(int id, String Nombre, Double Matricula, Double Costo, int Duracion, int Aula) {
        this.id = id;
        this.Nombre = Nombre;
        this.Matricula = Matricula;
        this.Costo = Costo;
        this.Duracion = Duracion;
        this.Aula = Aula;
    }

    public int getAula() {
        return Aula;
    }

    public void setAula(int Aula) {
        this.Aula = Aula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Double getMatricula() {
        return Matricula;
    }

    public void setMatricula(Double Matricula) {
        this.Matricula = Matricula;
    }

    public Double getCosto() {
        return Costo;
    }

    public void setCosto(Double Costo) {
        this.Costo = Costo;
    }

    public int getDuracion() {
        return Duracion;
    }

    public void setDuracion(int Duracion) {
        this.Duracion = Duracion;
    }

    @Override
    public boolean insertar() {
        String sql = "insert into curso (Nombre, Matricula, Costo, duracion, aula_id) values(?,?,?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.Nombre);
            stm.setDouble(2, this.Matricula);
            stm.setDouble(3, this.Costo);
            stm.setInt(4, this.Duracion);
            stm.setInt(5, this.Aula);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
        ArrayList<Curso> Cursos = new ArrayList<>();
        String sql = "SELECT c.id, c.Nombre, c.Matricula, c.Costo, c.Duracion, aula_id\n"
                + "FROM curso c\n"
                + "JOIN aula au ON c.aula_id = au.nro_aula";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String Nombre = rs.getString("Nombre");
                Double Matricula = rs.getDouble("Matricula");
                Double Costo = rs.getDouble("Costo");
                int Duracion = rs.getInt("duracion");
                int aula = rs.getInt("aula_id");
                Curso c = new Curso(id, Nombre, Matricula, Costo, Duracion, aula);
                Cursos.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Cursos;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE curso set Nombre= ?,  Matricula= ?, Costo=?,duracion=?, aula_id=? WHERE id = ?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, this.Nombre);
            stm.setDouble(2, this.Matricula);
            stm.setDouble(3, this.Costo);
            stm.setInt(4,this.Duracion);
            stm.setInt(5,this.Aula);
            stm.setInt(6,this.id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
