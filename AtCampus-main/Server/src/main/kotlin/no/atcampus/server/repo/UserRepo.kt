package no.atcampus.server.repo

import no.atcampus.server.entities.ProgramEntity
import no.atcampus.server.entities.SchoolEntity
import no.atcampus.server.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<UserEntity, Long>{
    fun findUserEntityByEmail(email: String): UserEntity?
    fun findUserEntitiesBySchoolEntity(schoolEntity: SchoolEntity): MutableList<UserEntity>
    fun findUserEntitiesByProgramEntity(programEntity: ProgramEntity): MutableList<UserEntity>
}
