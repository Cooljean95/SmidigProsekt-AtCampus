package no.atcampus.server.service

import io.mockk.every
import io.mockk.mockk
import no.atcampus.server.GenerateTestData
import no.atcampus.server.entities.RoleEntity
import no.atcampus.server.entities.UserEntity
import no.atcampus.server.repo.*
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.util.*

class UserEntityServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val groupRepo = mockk<GroupRepo>()
    private val schoolRepo = mockk<SchoolRepo>()
    private val programRepo = mockk<ProgramRepo>()
    private val userGroupRepo = mockk<UserGroupRepo>()
    private val roleRepo = mockk<RoleRepo>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val userService = UserService(userRepo, groupRepo, userGroupRepo, schoolRepo, programRepo, roleRepo, passwordEncoder)
    private val testData = GenerateTestData()


    @Test
    fun testGetUserByEmail(){
        every { userRepo.findUserEntityByEmail(any()) } answers {
            testData.userEntity
        }
        val user = userService.getUserByEmail("jensjenka@gmail.com")
        assert(user!!.firstName == "Jens")
    }

    /*
        Weird error
     */
    @Test
    fun testGetUsersByGroup(){
        every { userGroupRepo.findUserGroupEntitiesByGroupEntity(any()) } answers {
            mutableListOf(testData.userGroupEntity)
        }

        every{ groupRepo.findByIdOrNull(any()) } answers {
            testData.groupEntity
        }

        every { userRepo.findByIdOrNull(any()) } answers {
            testData.userEntity
        }

        every { userRepo.getById(any()) } answers {
            testData.userEntity
        }

        val userList = userService.getUsersByGroup(testData.groupEntity.id!!)
        assert(userList.size == 1)
        assert(userList[0].firstName.contains("Jens"))
    }

    @Test
    fun testGetUserById(){
        every { userRepo.findByIdOrNull(any()) } answers {
            testData.userEntity
        }
        val user = userService.getUserById(testData.userEntity.id!!)
        assert(user.firstName == "Jens")

    }

    @Test
    fun testUpdateUserById(){
        every { userRepo.findById(any()) } answers {
            Optional.of(testData.userEntity)
        }
        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }
        every{
            programRepo.findByIdOrNull(any())
        } answers {
            testData.programEntity
        }

        val userDetail = UserDetail(id = null, firstName = "test", null, null,
            null, null, null, null, null, null)

        every { userRepo.save(any()) } answers {
            firstArg()
        }



        val updatedUser = userService.updateUserById(1, userDetail)
        assert(updatedUser.firstName == "test")
        assert(updatedUser.lastName == "Jenka")
    }

    @Test
    fun testUpdateUserProfileImage(){
        every { userRepo.findById(any()) } answers {
            Optional.of(testData.userEntity)
        }
        every { userRepo.save(any()) } answers {
            firstArg()
        }
        assert(testData.userEntity.userProfileImage!!.contains("flickr"))
        val updatedUser = userService.updateUserProfileImage(1, "wow nice imagelink")
        assert(updatedUser.userProfileImage!!.contains("wow nice imagelink"))

    }

    @Test
    fun testAddUser(){
        val userDetail = UserDetail(id = null,
            firstName = "test",
            lastName = "test",
            email = "test",
            password = "test",
            phoneNumber = "test",
            school = 1,
            program = 1,
            userProfileImage = "test", dateCreated = LocalDate.now())

        every {
            schoolRepo.findByIdOrNull(any())
        } answers {
            testData.schoolEntity
        }
        every {
            programRepo.findByIdOrNull(any())
        } answers {
            testData.programEntity
        }

        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every { passwordEncoder.encode(any()) } answers { "encodedpassword" }

        every {roleRepo.getRoleEntityByName(any()) } answers { RoleEntity(1, "USER") }

        every { userRepo.findUserEntityByEmail(any()) } answers {
            null
        }

        val newUser = userService.registerUser(userDetail)
        assert(newUser.firstName == "test")

    }


}
