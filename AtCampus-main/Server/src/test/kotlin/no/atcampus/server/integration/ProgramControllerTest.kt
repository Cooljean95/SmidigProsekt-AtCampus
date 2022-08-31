package no.atcampus.server.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import no.atcampus.server.security.filter.TokenResponse
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

class ProgramControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun gettAllPrograms() {
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

        val programs = mockMvc.get("/api/program/all"){
            body?.let { header("Authorization", "Bearer " + token)  }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(programs.response.contentAsString.contains("Programmering"))


    }

    @Test
    fun getSpecificProgramTest(){

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


        val program = mockMvc.get("/api/program/1") {
            content = body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andReturn()

        assert(program.response.contentAsString.contains("Programmering"))

    }

    @Test
    fun createNewProgramTest(){
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

        val program = mockMvc.post("/api/program/new"){
            contentType = MediaType.APPLICATION_JSON
            content = "{\n" +
                    "    \"programName\": \"Cybersikkerhet\"\n" +
                    "}"
            body?.let { header("Authorization", "Bearer " + token) }
        }
            .andExpect { status { is2xxSuccessful() } }
            .andReturn()

        assert(program.response.contentAsString.contains("Cybersikkerhet"))
    }


}