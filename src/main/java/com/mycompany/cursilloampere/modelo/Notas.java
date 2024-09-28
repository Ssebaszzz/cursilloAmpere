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
public class Notas extends conexion implements sentencias {
    private int Id;
    private int idCurso;
    private int idAlumno;
    private int idMateria;
    private int nota_obtenida;
    private String nombreAlumno;
    private String nombreGrupo;
    private String nombreMateria;
    private String Fecha;
    public Notas(int Id,int idCurso, int idAlumno, int idMateria, int nota_obtenida, String nombreAlumno, String nombreGrupo, String nombreMateria, String Fecha) {
        this.Id=Id;
        this.idCurso = idCurso;
        this.idAlumno = idAlumno;
        this.idMateria = idMateria;
        this.nota_obtenida = nota_obtenida;
        this.nombreAlumno = nombreAlumno;
        this.nombreGrupo = nombreGrupo;
        this.nombreMateria = nombreMateria;
        this.Fecha= Fecha;
    }

    public Notas(){
        
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
    public int getIdMateria() {
        return idMateria;
    }
    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getNota_obtenida() {
        return nota_obtenida;
    }

    public void setNota_obtenida(int nota_obtenida) {
        this.nota_obtenida = nota_obtenida;
    }
   
    public String getNombreAlumno() {
        return nombreAlumno;
    }
    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }
    public String getNombreGrupo() {
        return nombreGrupo;
    }
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }
    public String getNombreMateria() {
        return nombreMateria;
    }
    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
    
    @Override
    public boolean insertar() {
        String sql = "insert into notas (alumno_id, curso_id, materia_id, nota_obtenida, fecha_examen) values(?,?,?,?,?)";
        try (
            Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.idAlumno);
            stm.setInt(2, this.idCurso);
            stm.setInt(3, this.idMateria);
            stm.setInt(4, this.nota_obtenida);
            stm.setString(5, this.Fecha);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(detalle_materia_profesor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList consulta() {
         ArrayList<Notas> Notass = new ArrayList<>();
        String sql = "SELECT n.id, n.alumno_id, n.curso_id,n.nota_obtenida, n.materia_id, n.fecha_examen, m.Nombre AS NombreMateria, c.Nombre AS NombreCurso, CONCAT(a.Nombre, ' ', a.Apellido) AS NombreAlumno "
                + "FROM notas n "
                + "JOIN alumno a ON n.alumno_id = a.id "
                + "JOIN curso c ON n.curso_id = c.id "
                + "JOIN materia m ON n.materia_id = m.id;";
                
        
        try (
                Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {

            while (rs.next()) {
                int id= rs.getInt("id");
                int idAlumno = rs.getInt("alumno_id");
                int idCurso = rs.getInt("curso_id");
                int idMateria = rs.getInt("materia_id");
                String NombreCurso = rs.getString("NombreCurso");
                String NombreAlumno = rs.getString("NombreAlumno");
                String NombreMateria = rs.getString("NombreMateria");
                int nota_obtenida = rs.getInt("nota_obtenida");
                String Fecha= rs.getString("fecha_examen");
                Notas n = new Notas (id, idAlumno, idCurso, idMateria, nota_obtenida, NombreAlumno, NombreCurso, NombreMateria, Fecha);
                Notass.add(n);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Notas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Notass;
    }

    @Override
    public boolean modificar() {
         String sql = "UPDATE notas SET materia_id=? ,curso_id=?,nota_obtenida=?, fecha_examen=? WHERE id=?";
        try (
            Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(3, this.nota_obtenida);
            stm.setInt(2, this.idCurso);
            stm.setInt(1, this.idMateria);
            stm.setString(4, this.Fecha);
            stm.setInt(5, Id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Notas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
         String sql="Delete from notas WHERE id=?";
        try (
            Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.Id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Notas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}
