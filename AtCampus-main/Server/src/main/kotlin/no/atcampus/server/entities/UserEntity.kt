package no.atcampus.server.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(name = "users_user_id_seq", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    val id: Long? = null,
    @Column(name = "user_first_name")
    val firstName: String,
    @Column(name = "user_last_name")
    val lastName: String,
    @Column(name = "user_email")
    val email: String,
    @Column(name = "user_password")
    val password: String,
    @Column(name = "user_phone_number")
    val phoneNumber: String,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_school", referencedColumnName = "school_id")
    val schoolEntity: SchoolEntity,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_program", referencedColumnName = "program_id")
    val programEntity: ProgramEntity,
    @Column(name = "user_profile_image")
    val userProfileImage: String?,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableList<RoleEntity>? = mutableListOf(),
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "group_request_id")
    @JsonIgnore
    val groupRequestEntity: MutableList<GroupRequestEntity>? = mutableListOf(),
    @Column(name = "user_date_created")
    val dateCreated: LocalDate? = LocalDate.now(),


    ){
    override fun toString(): String {
        return "UserEntity(id=$id, firstName='$firstName', lastName='$lastName', email='$email', password='$password', phoneNumber='$phoneNumber', schoolEntity=$schoolEntity, programEntity=$programEntity, userProfileImage=$userProfileImage, roles=$roles, dateCreated=$dateCreated)"
    }
}
