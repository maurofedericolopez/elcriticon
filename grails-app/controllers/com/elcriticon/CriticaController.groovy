package com.elcriticon

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CriticaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Critica.list(params), model:[criticaCount: Critica.count()]
    }

    def show(Critica critica) {
        respond critica
    }

    def create() {
        respond new Critica(params)
    }

    @Transactional
    def save(Critica critica) {
        if (critica == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (critica.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond critica.errors, view:'create'
            return
        }

        critica.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'critica.label', default: 'Critica'), critica.id])
                redirect critica
            }
            '*' { respond critica, [status: CREATED] }
        }
    }

    def edit(Critica critica) {
        respond critica
    }

    @Transactional
    def update(Critica critica) {
        if (critica == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (critica.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond critica.errors, view:'edit'
            return
        }

        critica.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'critica.label', default: 'Critica'), critica.id])
                redirect critica
            }
            '*'{ respond critica, [status: OK] }
        }
    }

    @Transactional
    def delete(Critica critica) {

        if (critica == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        critica.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'critica.label', default: 'Critica'), critica.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'critica.label', default: 'Critica'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
