package no.atcampus.server.service
import io.mockk.every
import io.mockk.mockk
import no.atcampus.server.GenerateTestData
import no.atcampus.server.repo.SchoolRepo
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class SchoolEntityServiceUnitTest {
    private val schoolRepo = mockk<SchoolRepo>()
    private val schoolService = SchoolService(schoolRepo)
    private val testData = GenerateTestData()

    @Test
    fun testGetSchoolBySchoolName() {
        every {
            schoolRepo.findSchoolEntityBySchoolName(any())
        } answers {
            testData.schoolEntity
        }
        val school = schoolService.findSchoolByName("Høyskolen Kristiania")
        assert(school.schoolName.startsWith("Høy"))
    }

    @Test
    fun testGetSchoolById() {
        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }
        val program = schoolService.findSchoolById(1)
        assert(program.schoolName.startsWith("Høy"))
    }

    @Test
    fun testDeleteSchool(){

        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }

        every {
            schoolRepo.deleteById(any())
        } answers {
            testData.schoolEntity
        }

        assert(schoolService.deleteSchool(1) == testData.schoolEntity)

    }


    @Test
    fun testUpdateSchool(){
        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }
        every{
            schoolRepo.save(any())
        } answers {
            testData.schoolEntity
        }
        val updatedSchoolInfo = UpdatedSchoolInfo("Testname")
        val updatedSchool = schoolService.updateSchoolInfo(1, updatedSchoolInfo)
        assert(updatedSchool.schoolName.startsWith("Høyskolen"))
    }
}