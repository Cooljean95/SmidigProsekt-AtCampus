package no.atcampus.server.controller

import no.atcampus.server.entities.CommentEntity
import no.atcampus.server.entities.PostEntity
import no.atcampus.server.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/post")
class PostController(@Autowired private val postService: PostService, @Autowired private val commentService: CommentService) {

    @GetMapping("/{id}/comment")
    fun getAllCommentsOnPost(@PathVariable("id") postId: Long): ResponseEntity<MutableList<CommentEntity>>{
        return ResponseEntity.ok().body(commentService.findCommentsByPost(postId))
    }

    @PostMapping("/{id}/comment")
    fun postCommentOnPost(
        @PathVariable("id") postId: Long,
        @RequestBody commentDetails: CommentDetails
    ): ResponseEntity<CommentEntity> {
        val newComment = commentService.addComment(commentDetails)
        return ResponseEntity.ok().body(newComment)
    }

    @GetMapping("/{id}")
    fun getSpecificPost(@PathVariable id: String): ResponseEntity<PostEntity>{
        return ResponseEntity.ok(postService.findPostById(id.toLong()))
    }
}