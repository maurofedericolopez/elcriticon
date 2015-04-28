package com.elcriticon

class Critica {

    String nombre, email, reseña
    Byte puntaje
    Pelicula pelicula

    static constraints = {
        nombre(blank: false, nullable: false, size: 1..40)
        email(blank: false, nullable: false, email: true, size: 1..100)
        reseña(blank: false, nullable: false, minSize: 10)
        puntaje(range: 1..5)
    }

    static mapping = {
        version false
    }
}
