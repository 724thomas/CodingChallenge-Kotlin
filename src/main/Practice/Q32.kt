package practice.q32

/**
아래는 실무에서 매우 흔하게 등장하는 패턴이다:
nullable DTO 필드 중 null이 아닐 때만 변환을 수행
변환 로직을 깔끔하게 유지
let, mapNotNull, apply 등과 함께 자주 쓰임
이번 문제는 그 중 let 기반 변환 로직을 연습하는 문제!

📌 상황 설명

서버에서는 클라이언트로부터 다음과 같은 DTO를 받는다.

data class UpdateUserRequest(
val nickname: String?,
val age: Int?
)

그리고 이 요청을 기반으로 기존 User 엔티티 값을 업데이트해야 한다:

data class User(
var nickname: String,
var age: Int
)

🎯 요구사항

아래 Java 방식을 Kotlin 스타일로 리팩토링해야 한다.
단, null-check 대신 let을 반드시 활용할 것.

📌 Java 스타일 업데이트 로직
if (dto.getNickname() != null) {
user.setNickname(dto.getNickname());
}

if (dto.getAge() != null) {
user.setAge(dto.getAge());
}

🧩 과제
👉 Kotlin으로 아래 함수를 작성하라:
fun applyUpdate(user: User, dto: UpdateUserRequest): User {
// 이 내부를 let을 활용하여 구현
}

🎯 조건
nickname과 age가 null이 아닐 때만 user의 값을 변경
반드시 let을 활용
불필요한 if문은 사용 금지
최대한 가독성 좋게
리턴은 업데이트된 user 그대로 반환
 */

fun applyUpdate(user: User, dto: UpdateUserRequest): User {
    dto.nickname?.let {
        nonNullNickname -> user.nickname = nonNullNickname
    }

    dto.age?.let {
        nonNullAge -> user.age = nonNullAge
    }
    return user
}

fun applyUpdate2(user: User, dto: UpdateUserRequest): User =
    user.apply {
        dto.nickname?.let { nickname = it }
        dto.age?.let { age = it }
    }

data class UpdateUserRequest(
    val nickname: String?,
    val age: Int?
)

data class User(
    var nickname: String,
    var age: Int
)


fun main() {

}

