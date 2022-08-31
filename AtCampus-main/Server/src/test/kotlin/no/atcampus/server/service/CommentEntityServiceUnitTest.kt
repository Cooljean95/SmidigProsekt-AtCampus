package no.atcampus.server.service

import io.mockk.every
import io.mockk.mockk
import no.atcampus.server.GenerateTestData
import no.atcampus.server.repo.CommentRepo
import no.atcampus.server.repo.PostRepo
import no.atcampus.server.repo.UserRepo
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class CommentEntityServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val commentRepo = mockk<CommentRepo>()
    private val postRepo = mockk<PostRepo>()
    private val commentService = CommentService(commentRepo, postRepo, userRepo)
    private val testData = GenerateTestData()

    @Test
    fun testGetCommentById(){

        every {
            commentRepo.findByIdOrNull(any())
        } answers {
            testData.commentEntity
        }

        val comment = commentService.findCommentsById(1)
        assert(comment.body.startsWith("Det stemmer!"))
    }

    @Test
    fun testFindCommentsByPost(){

        every {
            postRepo.findByIdOrNull(any())
        } answers {
            testData.postEntity
        }

        every {
            commentRepo.findCommentEntitiesByPostEntity(any())
        } answers {
            mutableListOf(testData.commentEntity)
        }

        val comment = commentService.findCommentsByPost(1)
        assert(comment.size == 1)
        assert(comment[0].body.startsWith("Det stemmer"))
    }

    @Test
    fun testDeleteComment(){

        every {
            commentRepo.findByIdOrNull(any())
        } answers {
            testData.commentEntity
        }

        every {
            commentRepo.deleteById(any())
        } answers {
            testData.commentEntity
        }

        assert(commentService.deleteComment(1) == testData.commentEntity)

    }

    @Test
    fun updateComments() {
        every {
            commentRepo.findByIdOrNull(any())
        } answers {
            testData.commentEntity
        }

        every {
            commentRepo.save(any())
        } answers {
            firstArg()
        }
        every {
            postRepo.findByIdOrNull(any())
        } answers {
            testData.postEntity
        }
        every {
            userRepo.findByIdOrNull(any())
        } answers {
            testData.userEntity
        }
        val updateComments = CommentDetails(1, "test1", 1, 1)
        val updatedCommentInfo = commentService.updateComments(1, updateComments)
        assert(updatedCommentInfo.body == "test1")
    }


    @Test
    fun testAddComment(){
        val newComment = CommentDetails(1,
            "test body", 1, 1
        )

        every {
            commentRepo.save(any())
        } answers {
            firstArg()
        }
        every {
            postRepo.findByIdOrNull(any())
        } answers {
            testData.postEntity
        }
        every {
            userRepo.findByIdOrNull(any())
        } answers {
            testData.userEntity
        }

        val comment = commentService.addComment(newComment)
        assert(comment.body.startsWith("test body"))
    }

}