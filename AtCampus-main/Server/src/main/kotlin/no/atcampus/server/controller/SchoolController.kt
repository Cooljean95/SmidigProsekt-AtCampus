package no.atcampus.server.controller

import no.atcampus.server.entities.*
import no.atcampus.server.service.GroupService
import no.atcampus.server.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/school")
class SchoolController(
    @Autowired private val schoolService: SchoolService,

) {
    @GetMapping("/all")
    fun getSchools(): ResponseEntity<MutableList<SchoolEntity>> {
        return ResponseEntity.ok(schoolService.findAllSchool())
    }

    @GetMapping("/{id}")
    fun getSpecificSchool(@PathVariable id: Long): ResponseEntity<SchoolEntity> {
        return ResponseEntity.ok(schoolService.findSchoolById(id))
    }

    @PostMapping("/new")
    fun createNewSchool(@RequestBody updatedSchoolInfo: UpdatedSchoolInfo): ResponseEntity<SchoolEntity> {
        return ResponseEntity.ok().body(schoolService.addSchool(updatedSchoolInfo))
    }
}

