package no.atcampus.server.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.atcampus.server.security.filter.TokenResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
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
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    fun getSpecificPostTest(){

        val loggedInUser = mockMvc.post("/api/login"){
            contentType = APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token


        val post = mockMvc.get("/api/post/1") {
            content = body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()

        assert(post.response.contentAsString.contains("Amazing post by me"))

    }

    @Test
    fun shouldPostCommentOnPost(){
        val loggedInUser = mockMvc.post("/api/login"){
            contentType = APPLICATION_JSON
            content = "{\n" +
                    "    \"email\": \"test@mail.com\",\n" +
                    "    \"password\": \"pirate\"\n" +
                    "}"
        }.andExpect { status { isOk() } }
            .andReturn()

        val body =  loggedInUser.response.contentAsString
        val tokenResponse = jacksonObjectMapper().readValue(body) as TokenResponse
        val token = tokenResponse.token

        val comment = mockMvc.post("/api/post/1/comment"){
            contentType = APPLICATION_JSON
            content = "{\n" +
                    "    \"body\": \"Awesome comment\",\n" +
                    "    \"post\": 1,\n" +
                    "    \"user\": 1\n" +
                    "}"
            body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { is2xxSuccessful() } }
            .andReturn()

        assert(comment.response.contentAsString.contains("Awesome comment"))
    }
}
