package com.markswell.resource;

import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import com.markswell.domain.Post;
import com.markswell.domain.User;
import com.markswell.dto.PostRequest;
import io.restassured.http.ContentType;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import com.markswell.repository.PostRepository;
import com.markswell.repository.UserRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
@QuarkusTestResource(restrictToAnnotatedClass = true,
        value = H2DatabaseTestResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostResourceTest {

    @Inject
    private UserRepository userRepository;
    @Inject
    private PostRepository postRepository;
    private Long userId;
    private Long followerId;

    @BeforeEach
    @Transactional
    public void config() {
        var user =  new User(null, 18, "First User");
        var follower =  new User(null, 18, "First User");
        userRepository.persist(user);
        userRepository.persist(follower);
        postRepository.persist(new Post("First Post", user));
        postRepository.persist(new Post("Second Post", user));
        this.userId = user.getId();
        this.followerId = follower.getId();
    }

    @Test
    @Order(1)
    @DisplayName("Should save an user post successfully")
    public void savePostSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .body(new PostRequest("Testing a post"))
                .pathParams("userId", this.userId)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    @Test
    @Order(2)
    @DisplayName("Should return an error because the user don't exists")
    public void savePostWithNonexistentUser() {
        var nonexistentUser = 999;
        given()
                .contentType(ContentType.JSON)
                .body(new PostRequest("Testing a post"))
                .pathParams("userId", nonexistentUser)
                .when()
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    @Order(3)
    @DisplayName("Should return 404 when user doesn't exist")
    public void getPostsNonexistentUser() {
        var nonexistentUserId = 999;

        given()
                .pathParams("userId", nonexistentUserId)
                .header("FollowerId", followerId)
                .when()
                .get()
                .then()
                .statusCode(404);
    }

    @Test
    @Order(3)
    @DisplayName("Should return 400 when followerId header weren't sent")
    public void getPostsWithoutFollowerIdHeader() {

        given()
                .pathParams("userId", this.userId)
                .when()
                .get()
                .then()
                .statusCode(400)
                .body("parameterViolations[0].message",
                        Matchers.is("The header FollowerId must not be null"));
    }

    @Test
    @Order(4)
    @DisplayName("Should return 400 when followerId header weren't sent")
    public void getPostsWithFollowerIdHeaderIsNotRegistered() {
        var followerNotRegistered = 999;
        given()
                .pathParams("userId", this.userId)
                .header("FollowerId", followerNotRegistered)
                .when()
                .get()
                .then()
                .statusCode(400)
                .body("localizedMessage",
                        Matchers.is("Follower don't exist"));
    }

}