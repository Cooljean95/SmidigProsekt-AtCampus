package no.atcampus.server.service
import io.mockk.every
import io.mockk.mockk
import no.atcampus.server.GenerateTestData
import no.atcampus.server.repo.ProgramRepo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.data.repository.findByIdOrNull

class ProgramEntityServiceUnitTest {

    private val programRepo = mockk<ProgramRepo>()
    private val programService = ProgramService(programRepo)
    private val testData = GenerateTestData()


    @Test
    fun testGetProgramByName(){
        every {
            programRepo.findProgramEntityByProgramName(any())
        } answers {
            testData.programEntity
        }
        val program = programService.findProgramByName("Informasjonsteknologi - Programmering")
        assert(program.programName.startsWith("Informasjonsteknologi"))
    }

    @Test
    fun testGetProgramById(){
        every {
            programRepo.findByIdOrNull(any())
        } answers {
            testData.programEntity
        }
        val program = programService.findProgramById(1)
        assert(program.programName.startsWith("Informasjonsteknologi"))
    }

    @Test
    fun testDeleteProgram(){

        every {
            programRepo.findByIdOrNull(any())
        } answers {
            testData.programEntity
        }

        every {
            programRepo.deleteById(any())
        } answers {
            testData.programEntity
        }

        assert(programService.deleteProgram(1) == testData.programEntity)

    }

    @Test
    fun testUpdateProgram(){
        every {
            programRepo.findByIdOrNull(any())
        } answers{
            testData.programEntity
        }
        every {
            programRepo.save(any())
        } answers {
            testData.programEntity
        }

        val programDetails = UpdatedProgramInfo( "Updated program")
        val updatedProgram = programService.updateProgramInfo(1, programDetails)

        assert(updatedProgram.programName.startsWith("Informasjonsteknologi"))

    }


}