package co.uk.simple.app.actuator

import co.uk.simple.app.repository.PersonRepository
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component

@Component
@Endpoint(id = "db-status")
class DBEndpoint (private val repository: PersonRepository){

    private companion object {
        private const val ACTUATOR_DB_KEY = "DBRows"
    }

    @ReadOperation
    fun dbStatus() = mapOf(ACTUATOR_DB_KEY to repository.count())

}