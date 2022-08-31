package no.atcampus.server.service

import no.atcampus.server.entities.ProgramEntity
import no.atcampus.server.entities.SchoolEntity
import no.atcampus.server.repo.ProgramRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ProgramService(
    @Autowired private val programRepo: ProgramRepo
) {
    fun findProgramByName(name: String): ProgramEntity {
        val program =  programRepo.findProgramEntityByProgramName(name)

        program?.let {
            return program
        }
        throw EntityNotFoundException("Could not find a program with name $name")
    }

    fun findProgramById(id: Long) : ProgramEntity{
        val program = programRepo.findByIdOrNull(id)

        program?.let {
            return program
        }
        throw EntityNotFoundException("Could not find the program with id $id")
    }

    fun deleteProgram(id : Long) : ProgramEntity {
        val program = programRepo.findByIdOrNull(id)
        program?.let {
            programRepo.deleteById(id)
            return program
        }
        throw EntityNotFoundException("Could not find program with id $id")
    }

    //TODO: Kanskje rename til updateProgram, for å få samme konvensjon som de andre update metodene i andre services
    //TODO: Kanskje rename updateProgramInfo og UpdatedProgramInfo klassen til ProgramDetails, som de andre klassene
    fun updateProgramInfo(id: Long, updatedProgramInfo: UpdatedProgramInfo): ProgramEntity {
        val program = programRepo.findByIdOrNull(id)
        program?.let {
            val updatedProgramEntity = ProgramEntity(
                id = id,
                programName = updatedProgramInfo.programName ?: program.programName
            )
            return programRepo.save(updatedProgramEntity)
        }
        throw EntityNotFoundException("Could not find program with id $id")
    }

    fun addProgram(updatedProgramInfo: UpdatedProgramInfo): ProgramEntity {
        val newProgram = ProgramEntity(
            programName = updatedProgramInfo.programName?: throw Exception("program must include program name")
        )
        return programRepo.save(newProgram)
    }

    fun findAllPrograms(): MutableList<ProgramEntity> {
        val allPrograms = programRepo.findAll()
        allPrograms?.let {
            return allPrograms
        }
        throw EntityNotFoundException ("Could not find all programs")
    }
}

data class UpdatedProgramInfo(val programName: String?)