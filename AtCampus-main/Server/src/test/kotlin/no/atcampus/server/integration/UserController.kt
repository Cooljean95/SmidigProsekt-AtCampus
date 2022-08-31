package no.atcampus.server.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.mockk.every
import no.atcampus.server.security.filter.TokenResponse
import no.atcampus.server.service.UserService
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
import org.springframework.test.web.servlet.put


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserController {


    @Autowired private lateinit var mockMvc: MockMvc


    @Test
    fun refreshAccessTokenTest(){
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
        val token2 = tokenResponse.refreshToken


        val user = mockMvc.post("/api/refresh") {
            contentType = APPLICATION_JSON
            content = body.let { token2 }
        }
            .andExpect { status { isOk() } }
            .andReturn()

        println(tokenResponse.refreshToken)


        val body2 = user.response.contentAsString
        val tokenResponse2 = jacksonObjectMapper().readValue(body2) as TokenResponse

        println(tokenResponse2.refreshToken)

        assert(tokenResponse.refreshToken == tokenResponse2.refreshToken)

    }


    @Test
    fun registerUser() {

        mockMvc.post("/api/register") {
            contentType = APPLICATION_JSON
            content = "{\n" +
                    "    \"firstName\":\"Jonas\",\n" +
                    "    \"lastName\": \"Hansen\",\n" +
                    "    \"email\":\"test123123221122@mail.com\",\n" +
                    "    \"password\":\"password\",\n" +
                    "    \"phoneNumber\":\"12312312\",\n" +
                    "    \"school\": 1,\n" +
                    "    \"program\": 1,\n" +
                    "    \"userProfileImage\":\"www.com.com\"\n" +
                    "}"
        }.andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun getAllUsersTest(){

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


        val user = mockMvc.get("/api/user/all") {
            content = body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()

        assert(user.response.contentAsString.contains("Martin"))

    }

    @Test
    fun getSpecificUserTest() {

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

        val user = mockMvc.get("/api/user/1") {
            content = body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()

        assert(user.response.contentAsString.contains("Martin"))

    }

    @Test
    fun getUserByEmailTest() {

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

        val user = mockMvc.get("/api/user/email/syvert@edjord.com") {
            content = body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()

        assert(user.response.contentAsString.contains("@")
        )
    }

/*
    @Test
    fun updateUserTest() {

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


        val user = mockMvc.put("/api/user/update/1"){
            content = body?.let { header("Authorization", "Bearer " + token) }

            contentType = APPLICATION_JSON
            content = "{\n" +
                    "    \"lastName\": \"jonas\"\n" +
                    "}"
        }
            .andExpect { status { is2xxSuccessful() }  }
            .andReturn()
            assert(user.response.contentAsString.contains("jonas"))
    }

 */

    @Test
    fun getAllGroupsFromUserTest() {
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


        val userGroups = mockMvc.get("/api/user/1/group") {
            content = body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
            .andReturn()

        assert(userGroups.response.contentAsString.contains("testgroup"))

    }


}
