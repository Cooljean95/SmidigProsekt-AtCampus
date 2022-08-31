package no.atcampus.server.repo

import no.atcampus.server.entities.GroupEntity
import no.atcampus.server.entities.UserEntity
import no.atcampus.server.entities.UserGroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserGroupRepo : JpaRepository<UserGroupEntity, Long> {
    fun findUserGroupEntitiesByUserEntity(userEntity: UserEntity): MutableList<UserGroupEntity>
    fun findUserGroupEntitiesByGroupEntity(groupEntity: GroupEntity): List<UserGroupEntity>
    fun findUserGroupEntityByUserEntityAndGroupEntity(userEntity: UserEntity, groupEntity: GroupEntity): UserGroupEntity?
}
