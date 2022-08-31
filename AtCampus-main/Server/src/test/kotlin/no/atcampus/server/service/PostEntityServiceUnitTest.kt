package no.atcampus.server.service

import io.mockk.every
import io.mockk.mockk
import no.atcampus.server.GenerateTestData
import no.atcampus.server.repo.GroupRepo
import no.atcampus.server.repo.PostRepo
import no.atcampus.server.repo.UserRepo
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class PostEntityServiceUnitTest {

    private val groupRepo = mockk<GroupRepo>()
    private val postRepo = mockk<PostRepo>()
    private val userRepo = mockk<UserRepo>()
    private val groupService = mockk<GroupService>()
    private val postService = PostService(groupRepo, postRepo, userRepo, groupService)
    private val testData = GenerateTestData()

    @Test
    fun testGetPostById(){

        every {
            postRepo.findByIdOrNull(any())
        } answers {
            testData.postEntity
        }

        val post = postService.findPostById(1)
        assert(post.body.startsWith("DRITKULE!!!!!!"))
    }


    @Test
    fun testFindPostsByGroup(){
        every {
            groupRepo.findByIdOrNull(any())
        } answers {
            testData.groupEntity
        }

        every {
            postRepo.findPostEntityByGroupEntity(any())
        } answers {
            mutableListOf(testData.postEntity)
        }
        every {
            postRepo.findFirst20PostEntityByGroupEntityOrderByDateCreatedDesc(any())
        }answers {
            mutableListOf(testData.postEntity)
        }

        val post = postService.findPostsByGroup(1)
        assert(post[0].body.startsWith("DRITKULE!!!!!!"))
    }


    @Test
    fun testDeletePost(){

        every {
            postRepo.findByIdOrNull(any())
        } answers {
            testData.postEntity
        }

        every {
            postRepo.deleteById(any())
        } answers {
            testData.postEntity
        }

        assert(postService.deletePost(1) == testData.postEntity)
    }


    @Test
    fun updatePosts() {
        every {
            postRepo.findByIdOrNull(any())
        } answers {
            testData.postEntity
        }

        every {
            userRepo.findByIdOrNull(any())
        }answers {
            testData.userEntity
        }
        every {
            groupRepo.findByIdOrNull(any())
        }answers {
            testData.groupEntity
        }
        every {
            postRepo.save(any())
        } answers {
            firstArg()
        }
        val updatePosts = PostDetails(1, "update", "new updated body", 1, 1)
        val updatedPostInfo = postService.updatePostInfo(1, updatePosts)
        assert(updatedPostInfo.title == "update")
    }


    @Test
    fun testAddPost(){
        val newPost = PostDetails(1,
            "Kohort 9000", "This is a body", 1, 1
        )

        every {
            postRepo.save(any())
        } answers {
            testData.postEntity
        }
        every {
            userRepo.findByIdOrNull(any())
        }answers {
            testData.userEntity
        }
        every {
            groupRepo.findByIdOrNull(any())
        }answers {
            testData.groupEntity
        }

        val post = postService.addPost(newPost)
        assert(post.title.startsWith("Kohort 9000"))
    }



}