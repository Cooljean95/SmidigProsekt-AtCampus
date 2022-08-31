package no.atcampus.server.service

import no.atcampus.server.entities.*
import no.atcampus.server.repo.GroupRepo
import no.atcampus.server.repo.PostRepo
import no.atcampus.server.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class PostService(
    @Autowired private val groupRepo: GroupRepo,
    @Autowired private val postRepo: PostRepo,
    @Autowired private val userRepo: UserRepo,
    @Autowired private val groupService: GroupService
) {

    fun findPostsByGroup(groupId: Long): MutableList<PostEntity>{
        val group = groupRepo.findByIdOrNull(groupId)
        group?.let {
            return postRepo.findFirst20PostEntityByGroupEntityOrderByDateCreatedDesc(it)
        }
        throw EntityNotFoundException("Could not find the group with id $groupId")
    }

    fun findPostById(id: Long): PostEntity {
        val post = postRepo.findByIdOrNull(id)
        post?.let {
            return post
        }
        throw EntityNotFoundException("Could not find the post with id $id")
    }

    fun deletePost(id: Long): PostEntity{
        val post = postRepo.findByIdOrNull(id)
        post?.let {
            postRepo.deleteById(id)
            return post
        }
        throw EntityNotFoundException("Could not find post with id $id")
    }


    fun addPost(postDetails: PostDetails): PostEntity{
        val postEntity = PostEntity(
            title = postDetails.title ?: throw Exception("PostDetails must include a title"),
            body = postDetails.body ?: throw Exception("PostDetails must include a body"),
            userEntity = userRepo.findByIdOrNull(postDetails.user ?: 0) ?: throw Exception("PostDetails must include an user"),
            groupEntity = groupRepo.findByIdOrNull(postDetails.group ?: 0) ?: throw Exception("PostDetails must include a group"),
        )
        return postRepo.save(postEntity)
    }

    //TODO: Burde endre updatePosts til updatePost, når det er singel. Kanskje også gi den samme navn som klassen.
    fun updatePostInfo(id: Long, updatePosts: PostDetails): PostEntity {
        val post = postRepo.findByIdOrNull(id)

        post?.let {
            val updatedPostEntity = PostEntity(
                id = id,
                title = updatePosts.title ?: post.title,
                body = updatePosts.body?: post.body,
                userEntity = userRepo.findByIdOrNull(updatePosts.user ?: 0)?: post.userEntity,
                groupEntity = groupRepo.findByIdOrNull(updatePosts.group ?: 0)?: post.groupEntity
            )

            return postRepo.save(updatedPostEntity)
        }
        throw EntityNotFoundException("Could not find post with id $id")
    }

    
    fun getAllRecentPostsByUser(userId: Long): MutableList<PostEntity>{
        
        val groups = groupService.getGroupsByUserId(userId)
        val allPosts = groups.flatMap { groupEntity -> postRepo.findPostEntityByGroupEntity(groupEntity) }
        allPosts?.let {
            return allPosts.toMutableList()
        }

        throw EntityNotFoundException("Could not find post with id $userId")
    }
}

data class PostDetails(val id: Long?, val title: String?, val body: String?, val user: Long?, val group: Long?)

data class MessageDetails(val message: String?)