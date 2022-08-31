package no.atcampus.server.controller

import no.atcampus.server.entities.ProgramEntity
import no.atcampus.server.service.ProgramService
import no.atcampus.server.service.UpdatedProgramInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/program")
class ProgramController(@Autowired private val programService: ProgramService) {

    @GetMapping("/all")
    fun getPrograms(): ResponseEntity<MutableList<ProgramEntity>> {
        return ResponseEntity.ok(programService.findAllPrograms())
    }

    @GetMapping("/{id}")
    fun getSpecificProgram(@PathVariable id: Long): ResponseEntity<ProgramEntity> {
        return ResponseEntity.ok(programService.findProgramById(id))
    }

    @PostMapping("/new")
    fun createNewProgram(@RequestBody updatedProgramInfo: UpdatedProgramInfo): ResponseEntity<ProgramEntity> {
        return ResponseEntity.ok().body(programService.addProgram(updatedProgramInfo))
    }
}