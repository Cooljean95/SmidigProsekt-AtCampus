package no.atcampus.server.controller
import no.atcampus.server.entities.GroupEntity
import no.atcampus.server.entities.PostEntity
import no.atcampus.server.entities.UserEntity
import no.atcampus.server.security.filter.TokenResponse
import no.atcampus.server.security.jwt.JwtUtil
import no.atcampus.server.service.GroupService
import no.atcampus.server.service.PostService
import no.atcampus.server.service.UserDetail
import no.atcampus.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api")
class UserController(@Autowired private val userService: UserService,
@Autowired private val groupService: GroupService,
@Autowired private val postService: PostService
) {

    @GetMapping("/user/all")
    fun getAllUsers() : ResponseEntity<List<UserEntity>>{
        return ResponseEntity.ok().body(userService.getAllUsers())
    }
    
    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody refreshToken: String): ResponseEntity<TokenResponse> {
        val decodedJwt = JwtUtil.decodeRefreshToken(refreshToken)
        val email = decodedJwt.subject
    
        val user = userService.getUserByEmail(email)
        val userDetails = User(user.email, user.password, user.roles?.map {roleEntity -> SimpleGrantedAuthority(roleEntity.name) })
        
        val issuer = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/login").toUriString();
    
        val newToken = JwtUtil.createToken(userDetails, issuer)
        val newRefreshToken = JwtUtil.createRefreshToken(userDetails, issuer)
        
        return ResponseEntity.ok().body(TokenResponse(newToken, newRefreshToken))
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody userDetail: UserDetail): ResponseEntity<UserEntity>{
        return ResponseEntity.ok().body(userService.registerUser(userDetail))
    }

    @GetMapping("/user/{id}")
    fun getSpecificUser(@PathVariable id: Long): ResponseEntity<UserEntity> {
        return ResponseEntity.ok().body(userService.getUserById(id))
    }

    @GetMapping("/user/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UserEntity>{
        return ResponseEntity.ok().body(userService.getUserByEmail(email))
    }

    // Needs work
    @PutMapping("/user/update/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDetail: UserDetail): UserEntity{
        return userService.updateUserById(id, userDetail)
    }

    @GetMapping("/user/{id}/group")
    fun getAllGroupsFromUser(@PathVariable id: Long): ResponseEntity<MutableList<GroupEntity>>{
        return ResponseEntity.ok().body(groupService.getGroupsByUserId(id))
    }

    @GetMapping("/user/{id}/post")
    fun getAllGroupPostsByUser(@PathVariable id: Long): ResponseEntity<MutableList<PostEntity>>{
        return ResponseEntity.ok().body(postService.getAllRecentPostsByUser(id))
    }
}