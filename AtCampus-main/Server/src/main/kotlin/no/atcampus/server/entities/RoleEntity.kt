package no.atcampus.server.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_role_id_seq")
    @SequenceGenerator(name = "roles_role_id_seq", sequenceName = "roles_role_id_seq", allocationSize = 1)
    @Column(name = "role_id")
    val id: Long? = null,
    @Column(name = "role_name")
    val name: String
) {
    override fun toString(): String {
        return "RoleEntity(id=$id, name='$name')"
    }
}