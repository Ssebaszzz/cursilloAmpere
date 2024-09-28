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
public class Aula extends conexion implements sentencias {

    private int nro_aula;
    private int sucursal;
    private String direccion;

    public Aula(int nro_aula, int sucursal, String direccion) {
        this.nro_aula = nro_aula;
        this.sucursal = sucursal;
        this.direccion = direccion;
    }

    public Aula() {
    }

    public int getNro_aula() {
        return nro_aula;
    }

    public void setNro_aula(int nro_aula) {
        this.nro_aula = nro_aula;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean insertar() {
        String sql = "insert into aula values(?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.nro_aula);
            stm.setInt(2, this.sucursal);
            stm.setString(3, this.direccion);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Aula.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
        ArrayList<Aula> Aulas = new ArrayList<>();
        String sql = "select * from aula";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int nro_aula = rs.getInt("nro_aula");
                int suc = rs.getInt("Sucursal");
                String dir = rs.getString("Direccion");
                Aula au = new Aula(nro_aula, suc, dir);
                Aulas.add(au);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Aula.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Aulas;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE aula set Sucursal = ?, Direccion=? WHERE nro_aula = ?";
        try (
            Connection con = getCon();
            PreparedStatement stm = con.prepareStatement(sql)
            ){
            stm.setInt(1,this.sucursal);
            stm.setString(2,this.direccion);
            stm.setInt(3,this.nro_aula);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Aula.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        String sql = "Delete from aula where nro_aula=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.nro_aula);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Aula.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
