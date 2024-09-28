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
public class detalle_pago_profesor extends conexion implements sentencias {

    private Double PagoHora;
    private Double PagoTotal;
    private String FechaPago;
    private int idClase;
    private String NombreCurso;
    private String NombreMateria;
    private String NombreProfesor;
    private String FechaClase;
    public detalle_pago_profesor() {
    }

    public detalle_pago_profesor(Double PagoHora, Double PagoTotal, String FechaPago, int idClase, String NombreCurso, String NombreMateria, String NombreProfesor, String FechaClase) {
        this.PagoHora = PagoHora;
        this.PagoTotal = PagoTotal;
        this.FechaPago = FechaPago;
        this.idClase = idClase;
        this.NombreCurso = NombreCurso;
        this.NombreMateria = NombreMateria;
        this.NombreProfesor = NombreProfesor;
        this.FechaClase = FechaClase;
    }
    
    public Double getPagoHora() {
        return PagoHora;
    }

    public void setPagoHora(Double PagoHora) {
        this.PagoHora = PagoHora;
    }

    public Double getPagoTotal() {
        return PagoTotal;
    }

    public void setPagoTotal(Double PagoTotal) {
        this.PagoTotal = PagoTotal;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String FechaPago) {
        this.FechaPago = FechaPago;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public String getNombreCurso() {
        return NombreCurso;
    }

    public void setNombreCurso(String NombreCurso) {
        this.NombreCurso = NombreCurso;
    }

    public String getNombreMateria() {
        return NombreMateria;
    }

    public void setNombreMateria(String NombreMateria) {
        this.NombreMateria = NombreMateria;
    }

    public String getNombreProfesor() {
        return NombreProfesor;
    }

    public void setNombreProfesor(String NombreProfesor) {
        this.NombreProfesor = NombreProfesor;
    }

    @Override
    public boolean insertar() {
        String sql = "insert into detalle_pago_profesor values(?,?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setDouble(1, this.PagoHora);
            stm.setDouble(2, this.PagoTotal);
            stm.setString(3, this.FechaPago);
            stm.setInt(4, this.idClase);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_pago_profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
        ArrayList<detalle_pago_profesor> detalle_pago_profesores = new ArrayList<>();
        String sql = "SELECT dpp.pago_hora, dpp.pago_total, dpp.fecha_pago, dpp.detalle_materia_profesor_id, "
                + "dmp.curso_id AS idCurso, dmp.materia_id AS idMateria, dmp.profesor_id AS idProfesor, "
                + "dmp.fecha AS fechaclase, CONCAT(p.Nombre, ' ', p.Apellido) AS Profesor, "
                + "c.Nombre AS Curso, m.Nombre AS Materia "
                + "FROM detalle_pago_profesor dpp "
                + "JOIN detalle_materia_profesor dmp ON dpp.detalle_materia_profesor_id = dmp.id "
                + "JOIN curso c ON dmp.curso_id = c.id "
                + "JOIN materia m ON dmp.materia_id = m.id "
                + "JOIN profesor p ON dmp.profesor_id = p.id;";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                Double PagoHora = rs.getDouble("pago_hora");
                Double PagoTotal = rs.getDouble("pago_total");
                String FechaPago = rs.getString("fecha_pago");
                int idClase = rs.getInt("detalle_materia_profesor_id");
                String FechaClase = rs.getString("fechaclase");
                String NombreCurso = rs.getString("Curso");
                String NombreProfesor = rs.getString("Profesor");
                String NombreMateria = rs.getString("Materia");
                detalle_pago_profesor dpp = new detalle_pago_profesor(PagoHora, PagoTotal, FechaPago, idClase,NombreCurso, NombreMateria, NombreProfesor, FechaClase);
                detalle_pago_profesores.add(dpp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(detalle_pago_profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detalle_pago_profesores;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE detalle_pago_profesor SET pago_hora=?, pago_total=?, fecha_pago=? WHERE detalle_materia_profesor_id=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setDouble(1, this.PagoHora);
            stm.setDouble(2, this.PagoTotal);
            stm.setString(3, this.FechaPago);
            stm.setInt(4, this.idClase);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_pago_profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getFechaClase() {
        return FechaClase;
    }

    public void setFechaClase(String FechaClase) {
        this.FechaClase = FechaClase;
    }

}
