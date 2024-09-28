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
public class Alumno extends conexion implements sentencias {

    private int id;
    private int ci;
    private String nombre;
    private String apellido;
    private int tel;
    private int telpadres;
    private String correo;
    private String fecha;
    private int Ruc;

    public Alumno(int id, int ci, String nombre, String apellido, int tel, int telpadres, String correo, String fecha, int Ruc) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tel = tel;
        this.telpadres = telpadres;
        this.correo = correo;
        this.fecha = fecha;
        this.Ruc = Ruc;
    }

    public Alumno() {
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

    public int getTelpadres() {
        return telpadres;
    }

    public void setTelpadres(int telpadres) {
        this.telpadres = telpadres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getRuc() {
        return Ruc;
    }

    public void setRuc(int Ruc) {
        this.Ruc = Ruc;
    }

    @Override
    public boolean insertar() {
        String sql = "insert into alumno (Cedula, Nombre, Apellido, Telefono, Correo, Tel_padres, fecha_inscripcion, ruc) values(?,?,?,?,?,?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.ci);
            stm.setString(2, this.nombre);
            stm.setString(3, this.apellido);
            stm.setInt(4, this.tel);
            stm.setString(5, this.correo);
            stm.setInt(6, this.telpadres);
            stm.setString(7, this.fecha);
            stm.setInt(8, this.Ruc);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    @Override
    public ArrayList consulta() {
        ArrayList<Alumno> Alumnos = new ArrayList<>();
        String sql = "select * from alumno";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int ci = rs.getInt("Cedula");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");;
                int tel = rs.getInt("Telefono");;
                int telpadres = rs.getInt("Tel_padres");
                String correo = rs.getString("Correo");
                String fecha = rs.getString("fecha_inscripcion");
                int Ruc = rs.getInt("ruc");

                Alumno a = new Alumno(id, ci, nombre, apellido, tel, telpadres, correo, fecha, Ruc);
                Alumnos.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Alumnos;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE alumno set Cedula=?, Nombre=?, Apellido=?, Telefono=?, Correo=?, Tel_padres=?, fecha_inscripcion=?, ruc=? WHERE id = ?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.ci);
            stm.setString(2, this.nombre);
            stm.setString(3, this.apellido);
            stm.setInt(4, this.tel);
            stm.setString(5, this.correo);
            stm.setInt(6, this.telpadres);
            stm.setString(7, this.fecha);
            stm.setInt(8, this.Ruc);
            stm.setInt(9, this.id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
    Connection con = null;
    PreparedStatement stm1 = null;
    PreparedStatement stm2 = null;
    PreparedStatement stm3 = null;
    PreparedStatement stm4 = null;

    String sql1 = "DELETE FROM detalle_factura WHERE factura_id IN "
                + "(SELECT id FROM factura WHERE alumno_id = ?);";
    String sql2 = "DELETE FROM factura WHERE alumno_id = ?;";
    String sql3 = "DELETE FROM detalle_cuota WHERE alumno_id = ?;";
    String sql4 = "DELETE FROM alumno WHERE id = ?;";

    try {
        con = getCon();
        con.setAutoCommit(false); // Inicia la transacción

        // Eliminar detalle_factura
        stm1 = con.prepareStatement(sql1);
        stm1.setInt(1, this.id);
        stm1.executeUpdate();

        // Eliminar factura
        stm2 = con.prepareStatement(sql2);
        stm2.setInt(1, this.id);
        stm2.executeUpdate();

        // Eliminar detalle_cuota
        stm3 = con.prepareStatement(sql3);
        stm3.setInt(1, this.id);
        stm3.executeUpdate();

        // Eliminar alumno
        stm4 = con.prepareStatement(sql4);
        stm4.setInt(1, this.id);
        stm4.executeUpdate();

        con.commit(); // Confirma la transacción si todo sale bien
        return true;

    } catch (SQLException ex) {
        if (con != null) {
            try {
                con.rollback(); // Deshace la transacción en caso de error
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    } finally {
        try {
            if (stm1 != null) stm1.close();
            if (stm2 != null) stm2.close();
            if (stm3 != null) stm3.close();
            if (stm4 != null) stm4.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    public int Auto_incremento() {
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'cursilloampere' AND TABLE_NAME = 'alumno'";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            if (rs.next()) {
                int nextId = rs.getInt("AUTO_INCREMENT");
                return nextId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
