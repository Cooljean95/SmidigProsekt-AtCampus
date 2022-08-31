package no.atcampus.server.service

import no.atcampus.server.entities.SchoolEntity
import no.atcampus.server.repo.SchoolRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

//TODO: Legg til mulighet å legge til en ny skole
@Service
class SchoolService(
    @Autowired private val schoolRepo: SchoolRepo
) {

    fun findSchoolByName(name: String): SchoolEntity {
        val school = schoolRepo.findSchoolEntityBySchoolName(name)
        school?.let {
            return school
        }
        throw EntityNotFoundException("Could not find a school with name $name")
    }

    fun findAllSchool(): MutableList<SchoolEntity> {
        val allSchool = schoolRepo.findAll()
        allSchool?.let {
            return allSchool
        }
       throw EntityNotFoundException ("Could not find all schools")
    }

    fun addSchool(updatedSchoolInfo: UpdatedSchoolInfo): SchoolEntity {
        val newSchool = SchoolEntity(
            schoolName = updatedSchoolInfo.schoolName?: throw Exception("school must include school name")
        )
        return schoolRepo.save(newSchool)
    }

    fun findSchoolById(id: Long) : SchoolEntity {
        val school = schoolRepo.findByIdOrNull(id)

        school?.let {
            return school
        }
        throw EntityNotFoundException("Could not find the program with id $id")
    }

    fun deleteSchool(id : Long) : SchoolEntity{
       val school = schoolRepo.findByIdOrNull(id)
        school?.let {
            schoolRepo.deleteById(id)
            return school
        }
        throw EntityNotFoundException("Could not find school with id $id")
    }

    //TODO: Kanskje rename updatedSchoolInfo til SchoolDetails, som de andre filene
    fun updateSchoolInfo(id: Long, updatedSchoolInfo: UpdatedSchoolInfo): SchoolEntity {
        val school = schoolRepo.findByIdOrNull(id)

        school?.let {
            val updatedSchoolEntity = SchoolEntity(
                id = id,
                schoolName = updatedSchoolInfo.schoolName ?: school.schoolName
            )
            return schoolRepo.save(updatedSchoolEntity)
        }
        throw EntityNotFoundException("Could not find school with id $id")
    }
}

data class UpdatedSchoolInfo(val schoolName: String?)