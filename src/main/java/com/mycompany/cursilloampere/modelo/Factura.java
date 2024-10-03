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
public class Factura extends conexion implements sentencias {

    private int Id;
    private String Fecha;
    private String Concepto;
    private int Alumno;
    private int Ruc;
    private String NombreAlumno;

    public Factura(int Id, String Fecha, String Concepto, int Alumno, int Ruc, String NombreAlumno) {
        this.Id = Id;
        this.Fecha = Fecha;
        this.Concepto = Concepto;
        this.Alumno = Alumno;
        this.Ruc = Ruc;
        this.NombreAlumno = NombreAlumno;
    }

    public Factura() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getConcepto() {
        return Concepto;
    }

    public void setConcepto(String Concepto) {
        this.Concepto = Concepto;
    }

    public int getAlumno() {
        return Alumno;
    }

    public void setAlumno(int Alumno) {
        this.Alumno = Alumno;
    }

    public int getRuc() {
        return Ruc;
    }

    public void setRuc(int Ruc) {
        this.Ruc = Ruc;
    }

    @Override
    public boolean insertar() {
        String sql = "insert into factura (alumno_id, fecha_pago, concepto) values(?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Alumno);
            stm.setString(2, this.Fecha);
            stm.setString(3, this.Concepto);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
        ArrayList<Factura> Facturas = new ArrayList<>();
        String sql = "SELECT f.id, f.alumno_id, f.fecha_pago, f.concepto, a.ruc AS Ruc, CONCAT(a.Nombre, ' ', a.Apellido) AS Alumno "
                + "FROM factura f "
                + "JOIN alumno a ON f.alumno_id = a.id ";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int idFactura = rs.getInt("id");
                int idAlumno = rs.getInt("alumno_id");
                String Concepto = rs.getString("concepto");
                String NombreAlumno = rs.getString("Alumno");
                String Fecha = rs.getString("fecha_pago");
                int ruc = rs.getInt("Ruc");
                Factura f = new Factura(idFactura, Fecha, Concepto, idAlumno, ruc, NombreAlumno);
                Facturas.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(detalle_cuota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Facturas;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE factura set alumno_id = ?, concepto = ?, fecha_pago=? WHERE id=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Alumno);
            stm.setString(2, this.Concepto);
            stm.setString(3, this.Fecha);
            stm.setInt(4, this.Id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getNombreAlumno() {
        return NombreAlumno;
    }

    public void setNombreAlumno(String NombreAlumno) {
        this.NombreAlumno = NombreAlumno;
    }
    public int Auto_incremento() {
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'cursilloampere' AND TABLE_NAME = 'factura'";
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
    public boolean matriculado(int idA) {
    String sql = "SELECT CASE WHEN df.nro_cuota = 0 THEN TRUE ELSE FALSE END AS esta_matriculado " +
                 "FROM factura f " +
                 "JOIN detalle_factura df ON f.id = df.factura_id " +
                 "WHERE f.alumno_id = ? LIMIT 1";
    try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
        stm.setInt(1, idA); // Usamos idA para el alumno_id

        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getBoolean("esta_matriculado");
        }
        return false; // Retornar false si no se encuentra el alumno
    } catch (SQLException ex) {
        Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
}

}
