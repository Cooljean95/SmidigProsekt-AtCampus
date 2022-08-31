package no.atcampus.server.entities

import javax.persistence.*

@Entity
@Table(name = "group_request")
class GroupRequestEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_request_group_request_id_seq")
    @SequenceGenerator(name = "group_request_group_request_id_seq", sequenceName = "group_request_group_request_id_seq", allocationSize = 1)
    @Column(name = "group_request_id")
    val id: Long? = null,
    @Column(name = "group_request_message")
    val message: String? = "",
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    val userEntity: UserEntity,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    val groupEntity: GroupEntity
)