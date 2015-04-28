package com.elcriticon

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PeliculaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Pelicula.list(params), model:[peliculaCount: Pelicula.count()]
    }

    def show(Pelicula pelicula) {
        respond pelicula
    }

    def create() {
        respond new Pelicula(params)
    }

    @Transactional
    def save(Pelicula pelicula) {
        if (pelicula == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pelicula.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pelicula.errors, view:'create'
            return
        }

        pelicula.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), pelicula.id])
                redirect pelicula
            }
            '*' { respond pelicula, [status: CREATED] }
        }
    }

    def edit(Pelicula pelicula) {
        respond pelicula
    }

    @Transactional
    def update(Pelicula pelicula) {
        if (pelicula == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pelicula.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pelicula.errors, view:'edit'
            return
        }

        pelicula.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), pelicula.id])
                redirect pelicula
            }
            '*'{ respond pelicula, [status: OK] }
        }
    }

    @Transactional
    def delete(Pelicula pelicula) {

        if (pelicula == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pelicula.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), pelicula.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pelicula.label', default: 'Pelicula'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
