package no.atcampus.server.entities
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "groups")
class GroupEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "groups_group_id_seq")
    @SequenceGenerator(name = "groups_group_id_seq", sequenceName = "groups_group_id_seq", allocationSize = 1)
    @Column(name = "group_id")
    val id: Long? = null,
    @Column(name = "group_name")
    val name: String,
    @Column(name = "group_description")
    val description: String,
    @Column(name = "group_image")
    val image: String,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "group_admin", referencedColumnName = "user_id")
    val admin: UserEntity,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "group_school", referencedColumnName = "school_id")
    val schoolEntity: SchoolEntity,
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "group_request_id")
    @JsonIgnore
    val groupRequestEntity: MutableList<GroupRequestEntity>? = mutableListOf(),
    @Column(name = "group_date_created")
    val dateCreated: LocalDate? = LocalDate.now(),

    ){
    override fun toString(): String {
        return "Group(id=$id, name='$name', description='$description', image='$image', admin=$admin, school=$schoolEntity, dateCreated=$dateCreated)"
    }
}