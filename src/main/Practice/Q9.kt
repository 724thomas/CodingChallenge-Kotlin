package practice.q9


/*
🧩 문제 9 — 로그인 화면의 사용자 입력 이벤트 모델링
📜 시나리오
사용자가 로그인 화면에서 다음과 같은 행동을 할 수 있습니다 👇
아이디를 입력한다 (InputId)
비밀번호를 입력한다 (InputPassword)
로그인 버튼을 누른다 (Submit)
실패 후 다시 시도한다 (Retry)

💡 요구사항
sealed interface LoginIntent를 선언하시오.
위 4개의 하위 타입을 정의하시오.
data class InputId(val id: String)
data class InputPassword(val password: String)
data object Submit
data object Retry
fun LoginIntent.describe(): String 확장 함수를 작성하시오.
InputId → "입력한 아이디: {id}"
InputPassword → "입력한 비밀번호: {password}"
Submit → "로그인 버튼 클릭"
Retry → "재시도 버튼 클릭"
main()에서 4가지 이벤트를 모두 테스트하고 출력하시오.
 */

sealed interface LoginIntent {
    data class InputId(val id: String) : LoginIntent
    data class InputPassword(val password: String) : LoginIntent
    data object Submit : LoginIntent
    data object Retry : LoginIntent
}

fun LoginIntent.describe(): String = when (this) {
    is LoginIntent.InputId -> "입력한 아이디: $id"
    is LoginIntent.InputPassword -> "입력한 비밀번호: $password"
    LoginIntent.Submit -> "로그인 버튼 클릭"
    LoginIntent.Retry -> "재시도 버튼 클릭"
}

fun LoginIntent.handle() = when (this) {
    is LoginIntent.InputId -> println("🧠 ID 입력 처리: $id")
    is LoginIntent.InputPassword -> println("🔒 비밀번호 입력 처리: $password")
    LoginIntent.Submit -> println("🚀 로그인 시도 중...")
    LoginIntent.Retry -> println("🔁 재시도 중...")
}