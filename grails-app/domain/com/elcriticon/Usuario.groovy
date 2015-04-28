package com.elcriticon

class Usuario {

    String nombreUsuario, contraseña

    static constraints = {
        nombreUsuario(blank:false, unique: true)
        contraseña(blank: false)
    }

    static mapping = {
        version false
        nombreUsuario column: "nombreUsuario"
    }
}
