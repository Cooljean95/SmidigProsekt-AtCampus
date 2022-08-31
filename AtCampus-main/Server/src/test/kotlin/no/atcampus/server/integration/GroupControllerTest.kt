package no.atcampus.server.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.mockk.InternalPlatformDsl.toStr
import no.atcampus.server.security.filter.TokenResponse
import org.json.JSONObject
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GroupControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun getAllGroupsTest(){
        val loggedInUser = mockMvc.post("/api/login"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val groups = mockMvc.get("/api/group/all"){
            content = body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(groups.response.contentAsString.contains("testgroup"))
    }

    @Test
    fun getSpecificGroupTest(){

        val loggedInUser = mockMvc.post("/api/login"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val group = mockMvc.get("/api/group/1") {
            body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(group.response.contentAsString.contains("testgroup"))

    }

    @Test
    fun getAllPostsByGroupTest(){

        val loggedInUser = mockMvc.post("/api/login"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val posts = mockMvc.get("/api/group/1/post"){
            body?.let { header("Authorization", "Bearer " + token) }

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(posts.response.contentAsString.contains("Amazing post by me"))
        assert(posts.response.contentAsString.contains("Shut up"))
    }

    @Test
    fun createGroupTest() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val group = mockMvc.post("/api/group/create"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"name\":\"test\",\n" +
                    "    \"description\": \"wow test\",\n" +
                    "    \"image\":\"wwwwwwww.image.no\",\n" +
                    "    \"admin\": 1,\n" +
                    "    \"school\": 1\n" +
                    "}"
            body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(group.response.contentAsString.contains("wow test"))
    }


    @Test
    fun addUserToGroupTest(){
        val loggedInUser = mockMvc.post("/api/login"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val group = mockMvc.post("/api/group/1/user/1"){
            body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(group.response.contentAsString.contains("testgroup"))
    }


    @Test
    fun addGroupRequestToGroupTest() {
        val loggedInUser = mockMvc.post("/api/login") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body = loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val groupRequest = mockMvc.post("/api/group/request/3/1") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"message\": \"Hello i want to join\"\n" +
                    "}"
            body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(groupRequest.response.contentAsString.contains("1"))
    }



    @Test
    fun shouldGetAllCommentsOnPost(){
        val loggedInUser = mockMvc.post("/api/login"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val testComment = mockMvc.get("/api/post/1/comment"){
            body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()
        assert(testComment.response.contentAsString.contains("It was not"))
    }

    @Test
    fun shouldPostToGroup(){
        val loggedInUser = mockMvc.post("/api/login"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val post = mockMvc.post("/api/group/1/post"){
            body?.let { header("Authorization", "Bearer " + token) }
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"title\": \"Hey cool\",\n" +
                    "    \"body\": 1,\n" +
                    "    \"user\": 1,\n" +
                    "    \"group\": 1\n" +
            "}"
        }
            .andExpect { status { is2xxSuccessful() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(post.response.contentAsString.contains("Hey cool"))
    }


}

