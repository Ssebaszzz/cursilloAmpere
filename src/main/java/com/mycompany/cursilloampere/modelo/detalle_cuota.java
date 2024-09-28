/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cursilloampere.modelo;

import com.mycompany.cursilloampere.Detalle_cuotaController;
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
public class detalle_cuota extends conexion implements sentencias {

    private int idCurso;
    private int idAlumno;
    private String Alumno;
    private String Grupo;
    private int idAlumnoActual;
    private int idCursoActual;
    private int nro_cuota;

    public detalle_cuota(int idCurso, int idAlumno, String Alumno, String Grupo, int nro_cuota) {
        this.idCurso = idCurso;
        this.idAlumno = idAlumno;
        this.Alumno = Alumno;
        this.Grupo = Grupo;
        this.nro_cuota = nro_cuota;
    }

    public detalle_cuota(int idCurso, int idAlumno, String Alumno, String Grupo, int idAlumnoActual, int idCursoActual, int nro_cuota) {
        this.idCurso = idCurso;
        this.idAlumno = idAlumno;
        this.Alumno = Alumno;
        this.Grupo = Grupo;
        this.idAlumnoActual = idAlumnoActual;
        this.idCursoActual = idCursoActual;
        this.nro_cuota = nro_cuota;
    }

    public detalle_cuota() {
    }

    public String getGrupo() {
        return Grupo;
    }

    public void setGrupo(String Grupo) {
        this.Grupo = Grupo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getAlumno() {
        return Alumno;
    }

    public void setAlumno(String Alumno) {
        this.Alumno = Alumno;
    }

    @Override
    public boolean insertar() {
        String sql = "insert into detalle_cuota (alumno_id, curso_id, cantidad_cuotas) values(?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idAlumno);
            stm.setInt(2, this.idCurso);
            stm.setInt(3, this.nro_cuota);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_cuota.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList<detalle_cuota> consulta() {
        ArrayList<detalle_cuota> detalle_cuotas = new ArrayList<>();
        String sql = "SELECT dc.alumno_id, dc.curso_id, dc.cantidad_cuotas, c.Nombre AS NombreCurso, CONCAT(a.Nombre, ' ', a.Apellido) AS Alumno "
                + "FROM detalle_cuota dc "
                + "JOIN alumno a ON dc.alumno_id = a.id "
                + "JOIN curso c ON dc.curso_id = c.id;";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                int idAlumno = rs.getInt("alumno_id");
                int idCurso = rs.getInt("curso_id");
                String NombreCurso = rs.getString("NombreCurso");
                String NombreAlumno = rs.getString("Alumno");
                int cuota = rs.getInt("cantidad_cuotas");
                detalle_cuota dc = new detalle_cuota(idCurso, idAlumno, NombreAlumno, NombreCurso, cuota);
                detalle_cuotas.add(dc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(detalle_cuota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detalle_cuotas;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE detalle_cuota set alumno_id = ?, curso_id = ?, cantidad_cuotas=? WHERE alumno_id = ? AND curso_id=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idAlumno);
            stm.setInt(2, this.idCurso);
            stm.setInt(3, this.nro_cuota);
            stm.setInt(4, this.idAlumnoActual);
            stm.setInt(5, this.idCursoActual);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_cuota.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        String sql = "Delete from detalle_cuota where alumno_id=? AND curso_id=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idAlumno);
            stm.setInt(2, this.idCurso);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_cuota.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Integer> obtenerCursosDelAlumno(int alumnoId) {
        List<Integer> cursosDelAlumno = new ArrayList<>();
        String sql = "SELECT curso_id FROM detalle_cuota WHERE alumno_id = ?";

        try (
                Connection con = getCon(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, alumnoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cursosDelAlumno.add(rs.getInt("curso_id"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(detalle_cuota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cursosDelAlumno;
    }

    public int getIdAlumnoActual() {
        return idAlumnoActual;
    }

    public void setIdAlumnoActual(int idAlumnoActual) {
        this.idAlumnoActual = idAlumnoActual;
    }

    public int getIdCursoActual() {
        return idCursoActual;
    }

    public void setIdCursoActual(int idCursoActual) {
        this.idCursoActual = idCursoActual;
    }

    public int getNro_cuota() {
        return nro_cuota;
    }

    public void setNro_cuota(int nro_cuota) {
        this.nro_cuota = nro_cuota;
    }

}
