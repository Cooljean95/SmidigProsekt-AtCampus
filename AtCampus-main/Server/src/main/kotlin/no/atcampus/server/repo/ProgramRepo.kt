package no.atcampus.server.repo

import no.atcampus.server.entities.ProgramEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProgramRepo : JpaRepository<ProgramEntity, Long> {
    fun findProgramEntityByProgramName(name: String): ProgramEntity?

}