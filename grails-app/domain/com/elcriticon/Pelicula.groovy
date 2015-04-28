package com.elcriticon

class Pelicula {

    String nombre, sinopsis
    Short añoEstreno
    Director director
    static hasMany = [actores : Actor, criticas : Critica]

    static constraints = {
        nombre(blank: false, nullable: false, size: 1..40)
        sinopsis(blank: false, nullable: false, minSize: 5)
        añoEstreno(blank: false, nullable: false)
    }

    static mapping = {
        version false
        añoEstreno column: "añoEstreno"
    }
}
