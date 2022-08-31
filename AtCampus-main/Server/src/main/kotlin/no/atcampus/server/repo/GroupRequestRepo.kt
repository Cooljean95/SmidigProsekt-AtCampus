package no.atcampus.server.repo

import no.atcampus.server.entities.GroupEntity
import no.atcampus.server.entities.GroupRequestEntity
import no.atcampus.server.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRequestRepo:JpaRepository<GroupRequestEntity, Long>{
    fun getAllByGroupEntity(groupEntity: GroupEntity): MutableList<GroupRequestEntity>
    fun findByGroupEntityAndUserEntity(groupEntity: GroupEntity, userEntity: UserEntity): GroupRequestEntity?
}
