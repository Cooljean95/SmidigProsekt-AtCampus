package no.atcampus.server.repo

import no.atcampus.server.entities.GroupEntity
import no.atcampus.server.entities.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepo: JpaRepository<PostEntity, Long>{

    //TODO: Rename to findPostEntitiesByGroupEntity . Might only return one entity no matter how many, depending on how spring boot works.
    fun findPostEntityByGroupEntity(groupEntity: GroupEntity): MutableList<PostEntity>
    fun findFirst20PostEntityByGroupEntityOrderByDateCreatedDesc(groupEntity: GroupEntity): MutableList<PostEntity>

}