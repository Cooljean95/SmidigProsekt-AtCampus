package no.atcampus.server.integration


import no.atcampus.server.service.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate


@DataJpaTest
@ActiveProfiles("test")
@Import(UserService::class, GroupService::class, PostService::class, CommentService::class, SchoolService::class, ProgramService::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class DatabaseIntegrationTest(@Autowired private val userService: UserService,
                              @Autowired private val groupService: GroupService,
                              @Autowired private val postService: PostService,
                              @Autowired private val commentService: CommentService,
                              @Autowired private val schoolService: SchoolService,
                              @Autowired private val programService: ProgramService
) {


    @Test
    fun registerUser() {
        val registerUser = UserDetail(
            1, "Jonas", "Fredrik", "Jonas@Fredrik.com", "123", "113", 1, 1,
            "123", LocalDate.now()
        )
        val createdUser = userService.registerUser(registerUser)
        assert(createdUser.email==("Jonas@Fredrik.com"))
        val foundUser = userService.loadUserByUsername("Jonas@Fredrik.com")
        assert(createdUser.email == foundUser.username)
    }

    @Test
    fun shouldGetUsersFromDB() {
        val result = userService.getAllUsers()
        assert(result.size == 3)
    }

    @Test
    fun shouldGetUserByIdAndReturnNameFromDB () {
        val getUser = userService.getUserById(1)
        assert(getUser.firstName == "Martin")
    }

    @Test
    fun addPost() {
        val postOnPost = PostDetails(1, "Title", "This is some Text", 1, 1
        )

        val createPost = postService.addPost(postOnPost)
        assert(createPost.body == ("This is some Text"))
    }

    @Test
    fun shouldGetPostFromDB () {
        val getPostFromID = postService.findPostById(1)
        assert(getPostFromID.title == "Amazing post by me")
    }

     @Test
     fun shouldRegisterGroup () {
         val registerGroup = GroupDetails(
             "Prog", "Jonas", "Fredrik", 1, 1
         )
         val createdGroup = groupService.addGroup("test@mail.com", registerGroup)
         assert(createdGroup.name == "Prog")
     }

    @Test
    fun shouldGetCommentFromDB () {
        val getCommentFromDB = commentService.findCommentsById(2)
        assert(getCommentFromDB.body=="No u.")
    }

    @Test
    fun shouldAddComment () {
        val createComment = CommentDetails (1, "heihei", 1, 1)

        val addedComment = commentService.addComment(createComment)
        assert(addedComment.body=="heihei")
    }

    @Test
    fun findSchoolByNameFromDB() {
        val finSchoolFromDb = schoolService.findSchoolById(1)
        assert(finSchoolFromDb.schoolName == "Høyskolen Kristiania")
    }

    @Test
    fun findProgramNameFromDB () {
        val findProgramNameFromID = programService.findProgramById(1)
        assert(findProgramNameFromID.programName == "Programmering")
    }
}