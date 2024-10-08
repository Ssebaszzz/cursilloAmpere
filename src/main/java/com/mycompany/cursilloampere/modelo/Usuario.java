/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cursilloampere.modelo;

/**
 *
 * @author Sebas
 */
public class Usuario {
    private String User="Admin";
    private String Password="123";
    
    public Usuario() {
    }
    
    public boolean validarLogin(String Usuario, String Contraseña) {
        return User.equals(Usuario) && Password.equals(Contraseña);
    }
}
