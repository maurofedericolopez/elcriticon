package com.elcriticon

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ActorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Actor.list(params), model:[actorCount: Actor.count()]
    }

    def show(Actor actor) {
        respond actor
    }

    def create() {
        respond new Actor(params)
    }

    @Transactional
    def save(Actor actor) {
        if (actor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (actor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond actor.errors, view:'create'
            return
        }

        actor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'actor.label', default: 'Actor'), actor.id])
                redirect actor
            }
            '*' { respond actor, [status: CREATED] }
        }
    }

    def edit(Actor actor) {
        respond actor
    }

    @Transactional
    def update(Actor actor) {
        if (actor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (actor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond actor.errors, view:'edit'
            return
        }

        actor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'actor.label', default: 'Actor'), actor.id])
                redirect actor
            }
            '*'{ respond actor, [status: OK] }
        }
    }

    @Transactional
    def delete(Actor actor) {

        if (actor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        actor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'actor.label', default: 'Actor'), actor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'actor.label', default: 'Actor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
