package practice.q36

/**
 * public class User {
 *     private String nickname;
 *     private Integer age;
 *
 *     public User(String nickname, Integer age) {
 *         this.nickname = nickname;
 *         this.age = age;
 *     }
 *
 *     public String getNickname() {
 *         return nickname;
 *     }
 *
 *     public Integer getAge() {
 *         return age;
 *     }
 *
 *     public void setNickname(String nickname) {
 *         this.nickname = nickname;
 *     }
 *
 *     public void setAge(Integer age) {
 *         this.age = age;
 *     }
 * }
 * public class UpdateUserRequest {
 *     private String nickname;
 *     private Integer age;
 *
 *     public UpdateUserRequest(String nickname, Integer age) {
 *         this.nickname = nickname;
 *         this.age = age;
 *     }
 *
 *     public String getNickname() {
 *         return nickname;
 *     }
 *
 *     public Integer getAge() {
 *         return age;
 *     }
 * }
 * public class UserService {
 *
 *     public User applyUpdate(User user, UpdateUserRequest request) {
 *         if (request.getNickname() != null) {
 *             user.setNickname(request.getNickname());
 *         }
 *
 *         if (request.getAge() != null) {
 *             user.setAge(request.getAge());
 *         }
 *
 *         return user;
 *     }
 * }
 *
 */

data class User(
    var nickname: String,
    var age: Int,
) {
    fun applyUpdate(request: UpdateUserRequest) {
        request.nickname?.let { nickname = it }
        request.age?.let { age = it }
    }
}

data class UpdateUserRequest(
    val nickname: String?,
    val age: Int?,
)

class UserService {
    fun applyUpdate(user: User, request: UpdateUserRequest) {
        user.applyUpdate(request)
    }
}