package practice.q32

/**
📌 상황 설명

회원이 이메일을 수정하는 API를 호출했을 때,
서버에서는 아래 조건을 만족해야 한다:

email 값이 null이면 변경하지 않음

email이 공백이거나 빈 문자열이면 null로 저장

email이 존재하면 앞뒤 공백 제거 + 소문자로 강제 변환

저장할 때는 Email(value: String) 객체로 감싸야 함

Java 스타일이라면 보통 아래와 같이 작성되었을 것이다:

String email = dto.getEmail();
if (email != null) {
email = email.trim().toLowerCase();
if (email.isEmpty()) {
user.setEmail(null);
} else {
user.setEmail(new Email(email));
}
}

🎯 요구사항

아래 Kotlin 함수 내부를
let / takeIf / apply 등을 활용해
코틀린스럽게 변환해라.

📌 모델
data class User(var email: Email?)
data class Email(val value: String)
data class UpdateEmailRequest(val email: String?)

📌 구현할 함수
fun applyEmail(user: User, dto: UpdateEmailRequest) {
// 이 부분을 let / takeIf / apply 중 1개 이상 반드시 활용해서 구현
}

🎯 조건 요약

dto.email 이 null 이면 → user.email 은 변경하지 않음

dto.email 이 " " 빈 문자열/공백이면 → user.email = null

그 외라면

trim

lowercase

Email 객체로 감싸서 user.email에 저장

let / takeIf / apply 중 1개 이상 필수
 */

fun applyEmail(user: User, dto: UpdateEmailRequest) {
    dto.email?.let { raw ->
        user.email = raw
            .trim()
            .lowercase()
            .takeIf { it.isNotBlank() }
            ?.let(::Email)
    }
}

data class User(var email: Email?)
data class Email(val value: String)
data class UpdateEmailRequest(val email: String?)