package co.uk.simple.app.web


import co.uk.simple.app.dto.PersonResponse
import co.uk.simple.app.model.Person
import co.uk.simple.app.service.PersonService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder


@RestController
@RequestMapping("/api")
class PersonController(
    private val personService: PersonService
) {

    @GetMapping("/")
    fun display(): String = "Spring Boot CRUD operation with Kotlin and H2!"

    @GetMapping("/people")
    fun fetchPeople(
        @RequestParam(defaultValue = "0") pageNo: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<Collection<PersonResponse>> = personService.getAllPeople(pageNo, pageSize).run {
        return when (isEmpty()) {
            true -> ResponseEntity(HttpStatus.NO_CONTENT)
            false -> ResponseEntity(this, HttpStatus.OK)
        }
    }

    @PostMapping("/people")
    fun addNewPerson(@RequestBody person: Person, uri: UriComponentsBuilder): ResponseEntity<Void> {
        personService.save(person)
        val headers = HttpHeaders().apply {
            location = uri.path("/people/{id}").buildAndExpand(person.id).toUri()
        }
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @DeleteMapping("/people")
    fun removePeople(): ResponseEntity<Void> = personService.findAll().run {
        return when (isEmpty()) {
            true -> ResponseEntity(HttpStatus.NO_CONTENT)
            false -> {
                personService.deleteAll()
                return ResponseEntity(HttpStatus.OK)
            }
        }
    }

    @GetMapping("/people/{id}")
    fun fetchPersonById(@PathVariable("id") id: Long): ResponseEntity<PersonResponse> = personService.findById(id).run {
        return when (isPresent) {
            true -> ResponseEntity(get(), HttpStatus.OK)
            false -> ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/people/{id}")
    fun removePersonById(@PathVariable("id") personId: Long): ResponseEntity<Void> {
        return when (personService.findById(personId).isPresent) {
            true -> {
                personService.deleteById(personId)
                ResponseEntity(HttpStatus.NO_CONTENT)
            }
            false -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/people/{id}")
    fun updatePersonById(@PathVariable("id") id: Long, @RequestBody person: Person): ResponseEntity<Person> {
        return when (personService.findById(id).isPresent) {
            true -> {
                personService.save(person)
                ResponseEntity(personService.save(person), HttpStatus.OK)
            }
            false -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/people/query")
    fun fetchPeopleQuery(
        @RequestParam(defaultValue = "0") pageNo: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam name: String,
        @RequestParam age: Long,
    ): ResponseEntity<Collection<PersonResponse>> = personService.getAllPeopleQuery(pageNo, pageSize, name, age).run {
        return when (isEmpty()) {
            true -> ResponseEntity(HttpStatus.NO_CONTENT)
            false -> ResponseEntity(this, HttpStatus.OK)
        }
    }
}
