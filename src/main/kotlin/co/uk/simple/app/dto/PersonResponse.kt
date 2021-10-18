package co.uk.simple.app.dto

import co.uk.simple.app.model.Person
import java.time.LocalDate

data class PersonResponse (
    val id: Long,
    val name: String,
    val surname: String,
    val phone: String,
    val birthDate: LocalDate,
    val age: Int
) {

    constructor(person: Person) : this(
        id = person.id,
        name = person.name,
        surname = person.surname,
        phone = person.phone,
        birthDate = person.birthDate,
        age = person.age
    )
}
