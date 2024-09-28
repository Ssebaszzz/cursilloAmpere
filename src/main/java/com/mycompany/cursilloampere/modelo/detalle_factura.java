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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebas
 */
public class detalle_factura extends conexion implements sentencias {

    private int Factura;
    private int Curso_id;
    private double Pago;
    private int Nro_cuota;
    private String nombreCurso;
    private String nombreAlumno;
    private int Factual;
    private int Cactual;

    public detalle_factura(int Factura, int Curso_id, double Pago, int Nro_cuota) {
        this.Factura = Factura;
        this.Curso_id = Curso_id;
        this.Pago = Pago;
        this.Nro_cuota = Nro_cuota;
    }
    public detalle_factura(int Factura, int Curso_id, double Pago, int Nro_cuota, String nombreCurso, String nombreAlumno) {
        this.Factura = Factura;
        this.Curso_id = Curso_id;
        this.Pago = Pago;
        this.Nro_cuota = Nro_cuota;
        this.nombreCurso = nombreCurso;
        this.nombreAlumno = nombreAlumno;
    }

    public detalle_factura(int Factura, int Curso_id, double Pago, int Nro_cuota, String nombreCurso) {
        this.Factura = Factura;
        this.Curso_id = Curso_id;
        this.Pago = Pago;
        this.Nro_cuota = Nro_cuota;
        this.nombreCurso = nombreCurso;
    }

    public detalle_factura() {
    }

    public int getFactura() {
        return Factura;
    }

    public void setFactura(int Factura) {
        this.Factura = Factura;
    }

    public int getCurso_id() {
        return Curso_id;
    }

    public void setCurso_id(int Curso_id) {
        this.Curso_id = Curso_id;
    }

    public double getPago() {
        return Pago;
    }

    public void setPago(double Pago) {
        this.Pago = Pago;
    }

    public int getNro_cuota() {
        return Nro_cuota;
    }

    public void setNro_cuota(int Nro_cuota) {
        this.Nro_cuota = Nro_cuota;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public int getFactual() {
        return Factual;
    }

    public void setFactual(int Factual) {
        this.Factual = Factual;
    }

    public int getCactual() {
        return Cactual;
    }

    public void setCactual(int Cactual) {
        this.Cactual = Cactual;
    }

    @Override
    public boolean insertar() {
        String sql = "insert into detalle_factura (factura_id, curso_id, monto_cuota, nro_cuota) values(?,?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Factura);
            stm.setInt(2, this.Curso_id);
            stm.setDouble(3, this.Pago);
            stm.setInt(4, this.Nro_cuota);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_cuota.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
        ArrayList<detalle_factura> detalle_facturas = new ArrayList<>();
        String sql = "SELECT df.factura_id, df.curso_id, df.monto_cuota, df.nro_cuota, c.Nombre AS NombreCurso, CONCAT(a.Nombre, ' ', a.Apellido) AS Alumno "
                + "FROM detalle_factura df "
                + "JOIN factura f ON df.factura_id = f.id "
                + "JOIN alumno a ON f.alumno_id = a.id "
                + "JOIN curso c ON df.curso_id = c.id";

        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                int idFactura = rs.getInt("factura_id");
                int idCurso = rs.getInt("curso_id");
                double Pago = rs.getDouble("monto_cuota");
                int Cuota = rs.getInt("nro_cuota");
                String NombreCurso = rs.getString("NombreCurso");
                String NombreAlumno = rs.getString("Alumno");
                detalle_factura df = new detalle_factura(idFactura, idCurso, Pago, Cuota, NombreCurso, NombreAlumno);
                detalle_facturas.add(df);
            }
        } catch (SQLException ex) {
            Logger.getLogger(detalle_factura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detalle_facturas;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE detalle_factura set factura_id = ?, curso_id = ?, monto_cuota=?, nro_cuota=? WHERE factura_id = ? AND curso_id=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Factura);
            stm.setInt(2, this.Curso_id);
            stm.setDouble(3, this.Pago);
            stm.setInt(4, this.Nro_cuota);
            stm.setInt(5, this.Factual);
            stm.setInt(6, this.Cactual);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_factura.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Integer> obtenerCursosDelAlumno(int alumnoId) {
        List<Integer> cursosDelAlumno = new ArrayList<>();
        String sql = "SELECT curso_id FROM detalle_cuota WHERE alumno_id = ?";

        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, alumnoId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    cursosDelAlumno.add(rs.getInt("curso_id"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(detalle_factura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cursosDelAlumno;
    }
}
