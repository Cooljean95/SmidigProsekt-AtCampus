package no.atcampus.server.repo

import no.atcampus.server.entities.CommentEntity
import no.atcampus.server.entities.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepo: JpaRepository<CommentEntity, Long> {

    fun findCommentEntitiesByPostEntity(postEntity: PostEntity?): MutableList<CommentEntity>
}