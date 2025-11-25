package practice.q34

/**
📌 문제
🔽 Java 코드 (변환 대상)
public class UserService {

public User updateUser(UpdateRequest request) {
User user = findUser(request.getUserId());

if (request.getNickname() != null) {
if (!request.getNickname().isEmpty()) {
user.setNickname(request.getNickname().trim());
}
}

if (request.getAge() != null) {
if (request.getAge() > 0) {
user.setAge(request.getAge());
}
}

return user;
}

private User findUser(Long id) {
// DB 조회했다고 가정
return new User(id, "initial", 10);
}
}

📌 요구사항
위 코드를 Kotlin으로 자연스럽게 변환하라.
scope function(let/apply/run) 등을 적절히 활용해 가독성을 개선하라.
nullable 처리, elvis, early return을 적절히 사용해 실무 품질로 만들라.
data class / DTO 구조는 코틀린 스타일로 개선하라.
📌 DTO / Entity 정의 조건
User, UpdateRequest는 코틀린 data class로 작성하라.
nickname이 null이면 변경 없음
nickname이 빈 문자열("")이면 변경하지 않음
nickname은 항상 trim() 적용
age는 null이면 변경 없음
age가 0 이하이면 변경하지 않음
 */

fun main() {

}

class UserService {

    fun updateUser(request: UpdateRequest): User {
        val user = findUser(request.userId)

        request.nickname
            ?.takeIf { it.isNotBlank() }      // null OR blank → skip
            ?.trim()
            ?.let { user.nickname = it }

        request.age
            ?.takeIf { it > 0 }
            ?.let { user.age = it }

        return user
    }

    private fun findUser(id: Long): User {
        return User(id, "initial", 10)
    }
}

data class User(
    val id: Long,
    var nickname: String,
    var age: Int
)

data class UpdateRequest(
    val userId: Long,
    val nickname: String?,
    val age: Int?
)