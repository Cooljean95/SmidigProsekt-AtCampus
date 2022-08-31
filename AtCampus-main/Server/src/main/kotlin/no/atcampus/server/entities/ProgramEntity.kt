package no.atcampus.server.entities

import javax.persistence.*

@Entity
@Table(name = "programs")
class ProgramEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "programs_program_id_seq")
    @SequenceGenerator(name = "programs_program_id_seq", sequenceName = "programs_program_id_seq", allocationSize = 1)
    @Column(name = "program_id")
    val id: Long? = null,
    @Column(name = "program_name")
    val programName: String
) {
    override fun toString(): String {
        return "Program(id=$id, programName='$programName')"
    }
}