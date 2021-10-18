package co.uk.simple.app.repository

import co.uk.simple.app.model.Person
import java.time.LocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

    @Query(
        value = "SELECT * FROM PERSON WHERE name = ?1 and year(birth_date) = year(?2)",
        countQuery = "SELECT * FROM PERSON WHERE name = ?1 and year(birth_date) = year(?2)",
        nativeQuery = true
    )
    fun findByNameAndAge(name: String, age: LocalDate, pageable: Pageable): Page<Person>
}