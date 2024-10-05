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

public class Alumno extends conexion implements sentencias {

    private int id;
    private int ci;
    private String nombre;
    private String apellido;
    private int tel;
    private int telpadres;
    private String correo;
    private String fecha;
    private String Ruc;
    private String RucNom;

    public Alumno(int id, int ci, String nombre, String apellido, int tel, int telpadres, String correo, String fecha, String Ruc, String RucNom) {
        this.id = id;
        this.ci = ci;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tel = tel;
        this.telpadres = telpadres;
        this.correo = correo;
        this.fecha = fecha;
        this.Ruc = Ruc;
        this.RucNom = RucNom;
    }

    public Alumno() {
    }

    // Getters y setters
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

    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String Ruc) {
        this.Ruc = Ruc;
    }

    @Override
    public boolean insertar() {
        String sql = "INSERT INTO alumno (Cedula, Nombre, Apellido, Telefono, Correo, Tel_padres, fecha_inscripcion, ruc, ruc_nom) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.ci);
            stm.setString(2, this.nombre);
            stm.setString(3, this.apellido);
            stm.setInt(4, this.tel);
            stm.setString(5, this.correo);
            stm.setInt(6, this.telpadres);
            stm.setString(7, this.fecha);
            stm.setString(8, this.Ruc);
            stm.setString(9, this.RucNom);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public ArrayList<Alumno> consulta() {
        ArrayList<Alumno> Alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumno";
        try (Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int ci = rs.getInt("Cedula");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                int tel = rs.getInt("Telefono");
                int telpadres = rs.getInt("Tel_padres");
                String correo = rs.getString("Correo");
                String fecha = rs.getString("fecha_inscripcion");
                String Ruc = rs.getString("ruc");
                String RucNom = rs.getString("ruc_nom");
                Alumno a = new Alumno(id, ci, nombre, apellido, tel, telpadres, correo, fecha, Ruc, RucNom);
                Alumnos.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Alumnos;
    }

    @Override
    public boolean modificar() {
        String sql = "UPDATE alumno SET Cedula=?, Nombre=?, Apellido=?, Telefono=?, Correo=?, Tel_padres=?, fecha_inscripcion=?, ruc=?, ruc_nom=? WHERE id=?";
        try (Connection con = getCon(); PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, this.ci);
            stm.setString(2, this.nombre);
            stm.setString(3, this.apellido);
            stm.setInt(4, this.tel);
            stm.setString(5, this.correo);
            stm.setInt(6, this.telpadres);
            stm.setString(7, this.fecha);
            stm.setString(8, this.Ruc);
            stm.setString(9, this.RucNom);
            stm.setInt(10, this.id);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar() {
        String sql1 = "DELETE FROM detalle_factura WHERE factura_id IN (SELECT id FROM factura WHERE alumno_id = ?)";
        String sql2 = "DELETE FROM factura WHERE alumno_id = ?";
        String sql3 = "DELETE FROM detalle_cuota WHERE alumno_id = ?";
        String sql4 = "DELETE FROM alumno WHERE id = ?";

        try (Connection con = getCon()) {
            con.setAutoCommit(false);

            try (PreparedStatement stm1 = con.prepareStatement(sql1);
                 PreparedStatement stm2 = con.prepareStatement(sql2);
                 PreparedStatement stm3 = con.prepareStatement(sql3);
                 PreparedStatement stm4 = con.prepareStatement(sql4)) {

                stm1.setInt(1, this.id);
                stm1.executeUpdate();

                stm2.setInt(1, this.id);
                stm2.executeUpdate();

                stm3.setInt(1, this.id);
                stm3.executeUpdate();

                stm4.setInt(1, this.id);
                stm4.executeUpdate();

                con.commit();
                return true;
            } catch (SQLException ex) {
                con.rollback();
                Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int Auto_incremento() {
        String sql = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'cursilloampere' AND TABLE_NAME = 'alumno'";
        try (Connection con = getCon(); Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public String getRucNom() {
        return RucNom;
    }

    public void setRucNom(String RucNom) {
        this.RucNom = RucNom;
    }
}
