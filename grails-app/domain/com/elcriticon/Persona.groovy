package com.elcriticon

class Persona {

    String nombre, nacionalidad
    Short añoNacimiento
    Byte[] foto

    static constraints = {
        nombre(blank: false, nullable: false, size: 1..60)
        nacionalidad(blank: false, nullable: false, size: 1..40)
        añoNacimiento(blank: false, nullable: false)
        foto(blank: false, nullable: false)
    }

    static mapping = {
        version false
        añoNacimiento column: "añoNacimiento"
    }
}
