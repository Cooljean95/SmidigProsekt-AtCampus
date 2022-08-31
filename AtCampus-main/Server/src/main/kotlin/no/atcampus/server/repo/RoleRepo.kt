package no.atcampus.server.repo

import no.atcampus.server.entities.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepo : JpaRepository<RoleEntity, Long>{

    fun getRoleEntityByName(name: String): RoleEntity?

}