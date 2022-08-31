package no.atcampus.server.service

import io.mockk.every
import io.mockk.mockk
import no.atcampus.server.GenerateTestData
import no.atcampus.server.entities.ProgramEntity
import no.atcampus.server.entities.SchoolEntity
import no.atcampus.server.entities.UserEntity
import no.atcampus.server.repo.*
import org.junit.Test
import org.springframework.data.repository.findByIdOrNull


class GroupEntityServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val groupRepo = mockk<GroupRepo>()
    private val schoolRepo = mockk<SchoolRepo>()
    private val userGroupRepo = mockk<UserGroupRepo>()
    private val groupRequestRepo = mockk<GroupRequestRepo>()
    private val groupService = GroupService(userRepo, groupRepo, schoolRepo, userGroupRepo, groupRequestRepo)
    private val testData = GenerateTestData()

    @Test
    fun testGetGroupsByName(){

        every {
            groupRepo.findGroupEntitiesByName(any())
        } answers {
            mutableListOf(testData.groupEntity)
        }

        val groups = groupService.getGroupsByName("Kohort 9000")
        assert(groups.size == 1 && groups[0].name.startsWith("Kohort"))

    }

    @Test
    fun testGetAllGroups(){

        every {
            groupRepo.findAll()
        } answers {
            mutableListOf(testData.groupEntity, testData.groupEntity2)
        }

        val groups = groupService.getAllGroups()
        assert(groups.size == 2 && groups[0].name.startsWith("Kohort"))

    }

    @Test
    fun testGetGroupsByUserId(){

        every {
            userRepo.findByIdOrNull(any())
        } answers {
            testData.userEntity
        }

        every {
            userGroupRepo.findUserGroupEntitiesByUserEntity(any())
        } answers {
            mutableListOf(testData.userGroupEntity)
        }

        val groups = groupService.getGroupsByUserId(1)
        assert(groups.size == 1 && groups[0].name.startsWith("Kohort"))

    }

    @Test
    fun testGetGroupsBySchool(){

        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }

        every {
            groupRepo.findGroupEntitiesBySchoolEntity(any())
        } answers {
            mutableListOf(testData.groupEntity, testData.groupEntity2)
        }

        val groups = groupService.getGroupsBySchool(1)
        assert(groups.size == 2 && groups[0].name.startsWith("Kohort"))

    }

    @Test
    fun testUpdateGroup(){

        every {
            groupRepo.findByIdOrNull(any())
        } answers {
            testData.groupEntity
        }

        every {
            groupRepo.save(any())
        } answers {
            testData.groupEntity
        }

        every {
            userRepo.findByIdOrNull(any())
        } answers {
            testData.userEntity
        }
        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }

        val groupDetails = GroupDetails(
            "Kohort 9000", "test", "test", 1, 1
        )
        val updatedGroup = groupService.updateGroupById(1, groupDetails)
        assert(updatedGroup.name.startsWith("Kohort"))

    }


    @Test
    fun testAddGroup(){
        every {
            userRepo.findUserEntityByEmail(any())
        } answers {
            testData.userEntity2
        }
        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }
        val groupDetails = GroupDetails(
            "AKohort 9000", "test", "test", 1, 1
        )

        every {
            groupRepo.save(any())
        } answers {
            firstArg()
        }

        val group = groupService.addGroup("martinolaussen@gmail.com", groupDetails)
        assert(group.name.startsWith("AKohort"))
    }
}
