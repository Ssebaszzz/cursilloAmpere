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
public class detalle_materia_profesor extends conexion implements sentencias {

    private int Id;
    private int Duracion;
    private String Fecha;
    private String Hr_entrada;
    private String Hr_salida;
    private int idProfesor;
    private int idCurso;
    private int idMateria;
    private String nombreProfesor;
    private String nombreCurso;
    private String nombreMateria;

    public detalle_materia_profesor(int Id, int Duracion, String Fecha, String Hr_entrada, String Hr_salida, int idProfesor, int idCurso, int idMateria) {
        this.Id = Id;
        this.Duracion = Duracion;
        this.Fecha = Fecha;
        this.Hr_entrada = Hr_entrada;
        this.Hr_salida = Hr_salida;
        this.idProfesor = idProfesor;
        this.idCurso = idCurso;
        this.idMateria = idMateria;
    }

    public detalle_materia_profesor(int Id, int Duracion, String Fecha, String Hr_entrada, String Hr_salida, int idProfesor, int idCurso, int idMateria, String nombreProfesor, String nombreCurso, String nombreMateria) {
        this.Id = Id;
        this.Duracion = Duracion;
        this.Fecha = Fecha;
        this.Hr_entrada = Hr_entrada;
        this.Hr_salida = Hr_salida;
        this.idProfesor = idProfesor;
        this.idCurso = idCurso;
        this.idMateria = idMateria;
        this.nombreProfesor = nombreProfesor;
        this.nombreCurso = nombreCurso;
        this.nombreMateria = nombreMateria;
    }

    public detalle_materia_profesor() {
    }

    @Override
    public boolean insertar() {
        String sql = "insert into detalle_materia_profesor (horas_trabajadas, fecha, hr_entrada, hr_salida, profesor_id, curso_id, materia_id)  values(?,?,?,?,?,?,?)";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Duracion);
            stm.setString(2, this.Fecha);
            stm.setString(3, this.Hr_entrada);
            stm.setString(4, this.Hr_salida);
            stm.setInt(5, this.idProfesor);
            stm.setInt(6, this.idCurso);
            stm.setInt(7, this.idMateria);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_materia_profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
        ArrayList<detalle_materia_profesor> detalle_materia_profesores = new ArrayList<>();
        String sql = "SELECT dmp.id, dmp.horas_trabajadas, dmp.fecha, dmp.hr_entrada, dmp.hr_salida, dmp.profesor_id, dmp.curso_id, dmp.materia_id, \n"
                + "c.Nombre AS Curso, m.Nombre AS Materia, CONCAT(p.Nombre, ' ', p.Apellido) AS Profesor\n"
                + "FROM detalle_materia_profesor dmp\n"
                + "JOIN curso c ON dmp.curso_id = c.id\n"
                + "JOIN materia m ON dmp.materia_id = m.id\n"
                + "JOIN profesor p ON dmp.profesor_id = p.id;";
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int Duracion = rs.getInt("horas_trabajadas");
                String Fecha = rs.getString("fecha");
                String hr_entrada = rs.getString("hr_entrada");
                String hr_salida = rs.getString("hr_salida");
                int idP = rs.getInt("profesor_id");
                int idC = rs.getInt("curso_id");
                int idM = rs.getInt("materia_id");
                String nombreProfesor = rs.getString("Profesor");
                String nombreCurso = rs.getString("Curso");
                String nombreMateria = rs.getString("Materia");
                detalle_materia_profesor dmp = new detalle_materia_profesor(id, Duracion, Fecha, hr_entrada, hr_salida, idP, idC, idM, nombreProfesor, nombreCurso, nombreMateria);
                detalle_materia_profesores.add(dmp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(detalle_materia_profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detalle_materia_profesores;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE detalle_materia_profesor SET horas_trabajadas=?, fecha=?, hr_entrada=?, hr_salida=?, profesor_id=?, curso_id=?, materia_id=? WHERE id=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Duracion);
            stm.setString(2, this.Fecha);
            stm.setString(3, this.Hr_entrada);
            stm.setString(4, this.Hr_salida);
            stm.setInt(5, this.idProfesor);
            stm.setInt(6, this.idCurso);
            stm.setInt(7, this.idMateria);
            stm.setInt(8, this.Id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_materia_profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        String sql = "Delete from detalle_materia_profesor where id=?";
        try (
                Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_materia_profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getDuracion() {
        return Duracion;
    }

    public void setDuracion(int Duracion) {
        this.Duracion = Duracion;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getHr_entrada() {
        return Hr_entrada;
    }

    public void setHr_entrada(String Hr_entrada) {
        this.Hr_entrada = Hr_entrada;
    }

    public String getHr_salida() {
        return Hr_salida;
    }

    public void setHr_salida(String Hr_salida) {
        this.Hr_salida = Hr_salida;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

}
