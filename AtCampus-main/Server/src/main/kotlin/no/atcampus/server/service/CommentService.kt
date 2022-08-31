package no.atcampus.server.service

import no.atcampus.server.entities.*
import no.atcampus.server.repo.CommentRepo
import no.atcampus.server.repo.PostRepo
import no.atcampus.server.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CommentService(
    @Autowired private val commentRepo: CommentRepo,
    @Autowired private val postRepo: PostRepo,
    @Autowired private val userRepo: UserRepo
    ) {

    fun findCommentsByPost(postId: Long): MutableList<CommentEntity> {
        //Used findByIdOrNull to not use Optional<Post>, and instead Post?.
        val post = postRepo.findByIdOrNull(postId)
        post?.let {
            return commentRepo.findCommentEntitiesByPostEntity(it)
        }
        throw EntityNotFoundException("Could not find the post with id $postId")
    }

    fun findCommentsById(id: Long): CommentEntity {
        val comment = commentRepo.findByIdOrNull(id)
        comment?.let {
            return comment
        }
        throw EntityNotFoundException("Could not find the comment with id $id")
    }

    fun deleteComment(id: Long): CommentEntity {
        val comment = commentRepo.findByIdOrNull(id)
        comment?.let {
            commentRepo.deleteById(id)
            return comment
        }
        throw EntityNotFoundException("Could not find comment with id $id")
    }

    //TODO: Rename to updateComment, since you only update one comment.
    fun updateComments(id: Long, updateComment: CommentDetails): CommentEntity {
        val comment = commentRepo.findByIdOrNull(id)
        comment?.let {
            val updatedCommentEntity = CommentEntity(
                id = comment.id,
                body = updateComment.body ?: comment.body,
                postEntity = postRepo.findByIdOrNull(updateComment.post) ?: comment.postEntity,
                userEntity = userRepo.findByIdOrNull(updateComment.user) ?: comment.userEntity
            )
            return commentRepo.save(updatedCommentEntity)
        }
        throw EntityNotFoundException("Could not update comments with id: $id")
    }

    fun addComment(commentDetails: CommentDetails): CommentEntity {
        val commentEntity = CommentEntity(
            body = commentDetails.body ?: throw Exception("CommentDetails must include description"),
            postEntity = postRepo.findByIdOrNull(commentDetails.post) ?: throw Exception("CommentDetails must include a post"),
            userEntity = userRepo.findByIdOrNull(commentDetails.user) ?: throw Exception("CommentDetails must include an user")
        )
        return commentRepo.save(commentEntity)
    }
}

data class CommentDetails(val id: Long?, val body: String?, val post: Long?, val user: Long? )
