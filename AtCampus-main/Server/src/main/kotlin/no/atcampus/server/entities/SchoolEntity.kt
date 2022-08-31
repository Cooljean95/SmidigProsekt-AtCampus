package no.atcampus.server.entities

import javax.persistence.*

@Entity
@Table(name = "schools")
class SchoolEntity(
    @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schools_school_id_seq")
@SequenceGenerator(name = "schools_school_id_seq", sequenceName = "schools_school_id_seq", allocationSize = 1)
@Column(name = "school_id")
val id: Long? = null,
@Column(name = "school_name")
val schoolName: String
){
    override fun toString(): String {
        return "School(id=$id, schoolName=$schoolName)"
    }
}