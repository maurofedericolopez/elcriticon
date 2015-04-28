package com.elcriticon

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DirectorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Director.list(params), model:[directorCount: Director.count()]
    }

    def show(Director director) {
        respond director
    }

    def create() {
        respond new Director(params)
    }

    @Transactional
    def save(Director director) {
        if (director == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (director.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond director.errors, view:'create'
            return
        }

        director.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'director.label', default: 'Director'), director.id])
                redirect director
            }
            '*' { respond director, [status: CREATED] }
        }
    }

    def edit(Director director) {
        respond director
    }

    @Transactional
    def update(Director director) {
        if (director == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (director.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond director.errors, view:'edit'
            return
        }

        director.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'director.label', default: 'Director'), director.id])
                redirect director
            }
            '*'{ respond director, [status: OK] }
        }
    }

    @Transactional
    def delete(Director director) {

        if (director == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        director.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'director.label', default: 'Director'), director.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'director.label', default: 'Director'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
