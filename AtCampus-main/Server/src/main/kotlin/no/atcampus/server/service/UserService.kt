package no.atcampus.server.service

import no.atcampus.server.entities.UserEntity
import no.atcampus.server.repo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.persistence.EntityNotFoundException

@Service
class UserService(
    @Autowired private val userRepo: UserRepo,
    @Autowired private val groupRepo: GroupRepo,
    @Autowired private val userGroupRepo: UserGroupRepo,
    @Autowired private val schoolRepo: SchoolRepo,
    @Autowired private val programRepo: ProgramRepo,
    @Autowired private val roleRepo: RoleRepo,
    @Autowired private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = getUserByEmail(username)
        return User(user.email, user.password, user.roles?.map {roleEntity -> SimpleGrantedAuthority(roleEntity.name) })
    }


    fun getUserByEmail(email: String): UserEntity{
        val user = userRepo.findUserEntityByEmail(email)
        user?.let {
            return user
        }
        throw EntityNotFoundException("Could not find the user with email $email")
    }

    // Not tested
    //TODO: Maybe change to mutablelist?
    fun getAllUsers(): List<UserEntity>{
        return userRepo.findAll()
    }

    fun getUsersByGroup(groupId: Long): MutableList<UserEntity> {
        val group = groupRepo.findByIdOrNull(groupId)
        group?.let {
            val memberships = userGroupRepo.findUserGroupEntitiesByGroupEntity(group)
            return memberships.map { membership ->
                membership.userEntity
            }.toMutableList()
        }
        throw EntityNotFoundException("Could not find the group with group id $groupId")
    }


    fun getUserById(id: Long): UserEntity{
        val user = userRepo.findByIdOrNull(id)
        user?.let {
            return user
        }
        throw EntityNotFoundException("Could not find the user with id $id")
    }

    fun updateUserById(id: Long, userDetail: UserDetail): UserEntity{
        val user = userRepo.findByIdOrNull(id)

        user?.let {
            val updatedUserEntity = UserEntity(
                id = user.id,
                firstName = userDetail.firstName ?: user.firstName,
                lastName = userDetail.lastName ?: user.lastName,
                email = userDetail.email ?: user.email,
                password = userDetail.password ?: user.password,
                phoneNumber = userDetail.phoneNumber ?: user.phoneNumber,
                schoolEntity = schoolRepo.findByIdOrNull(userDetail.school ?: 0) ?: user.schoolEntity,
                programEntity = programRepo.findByIdOrNull(userDetail.program ?: 0) ?: user.programEntity,
                userProfileImage = userDetail.userProfileImage ?: user.userProfileImage,
                dateCreated = userDetail.dateCreated ?: user.dateCreated,
                roles = user.roles
            )

            userRepo.save(updatedUserEntity)

            return updatedUserEntity
        }

        throw EntityNotFoundException("Could not find user with id $id")

    }

    //TODO: Can be deleted, works with updateUserById
    fun updateUserProfileImage(id: Long, imageUrl: String): UserEntity{
        val user = userRepo.findById(id)

        val updatedUserEntity = UserEntity(
            id = user.get().id,
            firstName = user.get().firstName,
            lastName = user.get().lastName,
            email = user.get().email,
            password = user.get().password,
            phoneNumber = user.get().phoneNumber,
            schoolEntity = user.get().schoolEntity,
            programEntity = user.get().programEntity,
            userProfileImage = imageUrl,
            dateCreated = user.get().dateCreated
        )
        userRepo.save(updatedUserEntity)
        return updatedUserEntity
    }

    fun registerUser(user: UserDetail): UserEntity{
        val test = userRepo.findUserEntityByEmail(user.email!!)
        if(test !== null){
            throw Exception("Email already in use")
        }


        val newUserEntity = UserEntity(
            firstName = user.firstName ?: throw Exception("UserDetails must include firstname"),
            lastName = user.lastName ?: throw Exception("UserDetails must include lastname"),
            email = user.email ?: throw Exception("UserDetails must include email"),
            password = passwordEncoder.encode(user.password) ?: throw Exception("UserDetails must include password"),
            phoneNumber = user.phoneNumber ?: throw Exception("UserDetails must include phoneNumber"),
            schoolEntity = schoolRepo.findByIdOrNull(user.school ?: 0) ?: throw Exception("UserDetails must include school"),
            programEntity = programRepo.findByIdOrNull(user.program ?: 0) ?: throw Exception("UserDetails must include program"),
            userProfileImage = user.userProfileImage ?: throw Exception("UserDetails must include userProfileImage"),
            dateCreated = LocalDate.now() ?: throw Exception("UserDetails must include dateCreated"),
        )
        val userRole = roleRepo.getRoleEntityByName("USER")
        userRole?.let {
            newUserEntity.roles?.add(it)
        }
        userRepo.save(newUserEntity)
        return newUserEntity
    }
}

data class UserDetail(
    val id: Long?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val password: String?,
    val phoneNumber: String?,
    val school: Long?,
    val program: Long?,
    val userProfileImage: String?,
    val dateCreated: LocalDate?,
)
