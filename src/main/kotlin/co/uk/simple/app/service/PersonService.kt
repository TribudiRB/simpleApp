package co.uk.simple.app.service

import co.uk.simple.app.dto.PersonResponse
import co.uk.simple.app.model.Person
import co.uk.simple.app.repository.PersonRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import org.springframework.web.bind.annotation.RequestBody


@Service
class PersonService(private val repository: PersonRepository) {

    fun findById(id: Long): Optional<PersonResponse> = repository.findById(id).map { PersonResponse(it) }

    fun save(entity: Person): Person = repository.save(entity)

    fun findAll(): List<Person> = repository.findAll()

    fun deleteById(id: Long): Unit = repository.deleteById(id)

    fun deleteAll(): Unit = repository.deleteAll()

    fun getAllPeople(pageNo: Int, pageSize: Int): List<PersonResponse> {
        val paging = PageRequest.of(pageNo, pageSize)
        val pagedResult = repository.findAll(paging)

        return when (pagedResult.hasContent()) {
            true -> pagedResult.content.map { PersonResponse(it) }
            false -> listOf()
        }
    }

    fun getAllPeopleQuery(pageNo: Int, pageSize: Int, name: String, age: Long): List<PersonResponse> {
        val paging = PageRequest.of(pageNo, pageSize)
        val birthYear = LocalDate.now().minusYears(age)
        val pagedResult = repository.findByNameAndAge(name, birthYear, paging)

        return when (pagedResult.hasContent()) {
            true -> pagedResult.content.map { PersonResponse(it) }
            false -> listOf()
        }
    }

}