package no.atcampus.server.entities

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "comment")
class CommentEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_comment_id_seq")
    @SequenceGenerator(name = "comment_comment_id_seq", sequenceName = "comment_comment_id_seq", allocationSize = 1)
    @Column(name = "comment_id")
    val id: Long? = null,
    @Column(name = "comment_body")
    val body: String,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "comment_post_id", referencedColumnName = "post_id")
    val postEntity: PostEntity,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "comment_user_id", referencedColumnName = "user_id")
    val userEntity: UserEntity,
    @Column(name = "comment_date")
    val date: LocalDate? = LocalDate.now()
){
    override fun toString(): String {
        return "Comment(id=$id, body='$body', post=$postEntity, user=$userEntity, date=$date)"
    }
}