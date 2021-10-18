package co.uk.simple.app.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Transient
import javax.persistence.PostLoad

@Entity
data class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val surname: String,
    val phone: String,
    @Column(name = "birth_date", columnDefinition = "DATE")
    val birthDate: LocalDate,
    @Transient
    var age: Int
) {

    @PostLoad
    private fun calculateAge() {
        this.age = LocalDate.now().year - birthDate.year
    }
}
