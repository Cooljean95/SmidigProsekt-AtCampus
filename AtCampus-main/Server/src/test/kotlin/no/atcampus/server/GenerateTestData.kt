package no.atcampus.server

import no.atcampus.server.entities.*

class GenerateTestData (

    val schoolEntity: SchoolEntity = SchoolEntity(id = 1, schoolName = "Høyskolen Kristiania"),
    val programEntity: ProgramEntity = ProgramEntity(id = 1, programName = "Informasjonsteknologi - Programmering"),
    val userEntity: UserEntity = UserEntity(id = 1, firstName = "Jens", lastName = "Jenka", email = "jensjenka@gmail.com", password = "jens123", phoneNumber = "95645234", schoolEntity = schoolEntity, programEntity = programEntity, userProfileImage = "http://flickr.com/image/jagajsdfe"),
    val userEntity2: UserEntity = UserEntity(id = 2, firstName = "Martin", lastName = "Olaussen", email = "martinolaussen@gmail.com", password = "martin123", phoneNumber = "48529412", schoolEntity = schoolEntity, programEntity = programEntity, userProfileImage = "http://flickr.com/image/adsdfe"),
    val groupEntity: GroupEntity = GroupEntity(id = 1, name = "Kohort 9000", description = "Kohort 9 + Alle", image = "http://flickr.com/reafj123f", admin = userEntity, schoolEntity = schoolEntity),
    val groupEntity2: GroupEntity = GroupEntity(id = 2, name = "Høye på Pæra", description = "Vi er kule", image = "http://flickr.com/afsdfag", admin = userEntity2, schoolEntity = schoolEntity),
    val groupRequest: GroupRequestEntity = GroupRequestEntity(id = 1, "Some message", userEntity, groupEntity2),
    val userGroupEntity: UserGroupEntity = UserGroupEntity(id = 1, userEntity = userEntity, groupEntity = groupEntity, favorite = true),
    val userGroupEntity2: UserGroupEntity = UserGroupEntity(id = 2, userEntity = userEntity2, groupEntity = groupEntity2, favorite = false),
    val postEntity: PostEntity = PostEntity(id = 1, title = "Kohort 9000 er...", body = "DRITKULE!!!!!!", userEntity = userEntity, groupEntity = groupEntity),
    val postEntity2: PostEntity = PostEntity(id = 2, title = "Vi er alle sammen...", body = "høye på pæra. -Martin Olaussen", userEntity = userEntity2, groupEntity = groupEntity2),
    val commentEntity: CommentEntity = CommentEntity(id = 1, body = "Det stemmer! Vi er best!", postEntity = postEntity, userEntity = userEntity),
    val commentEntity2: CommentEntity = CommentEntity(id = 2, body = "Vi er faktisk alt for høye på pæra.", postEntity = postEntity2, userEntity = userEntity2)

)