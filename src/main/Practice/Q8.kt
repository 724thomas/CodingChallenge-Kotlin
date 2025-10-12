package Leetcode.kotlin


/*
🧩 문제 8 — 로그인 화면 상태를 sealed interface로 설계하시오
📜 요구사항

로그인 화면에는 여러 상태가 있습니다:

사용자가 아직 아무 행동도 안 한 상태 (Idle)

로그인 요청 중 (Loading)

로그인 성공 (Success)

로그인 실패 (Error) — 실패 메시지를 표시해야 함

다음 Java 코드를 Kotlin스럽게 리팩터링하세요 👇

public class LoginViewState {
    private boolean loading;
    private boolean success;
    private String errorMessage;

    public LoginViewState(boolean loading, boolean success, String errorMessage) {
        this.loading = loading;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isLoading() { return loading; }
    public boolean isSuccess() { return success; }
    public String getErrorMessage() { return errorMessage; }
}

💡 변환 조건
LoginViewState를 sealed interface로 설계하시오.
하위 상태 4개를 선언하시오.
Idle
Loading
Success(val userName: String)
Error(val message: String)
when 표현식을 이용해 상태별로 화면 메시지를 출력하는 함수를 작성하시오.
main()에서 4가지 상태를 테스트하시오.
 */

sealed interface LoginViewState {
    data object Idle : LoginViewState
    data object Loading : LoginViewState
    data class Success(val userName: String) : LoginViewState
    data class Error(val message: String) : LoginViewState
}

fun LoginViewState.describe(): String = when (this) {
    LoginViewState.Idle -> "사용자가 아무 행동도 안 한 상태"
    LoginViewState.Loading -> "로그인 요청 중..."
    is LoginViewState.Success -> "로그인 성공! 환영합니다, $userName 님."
    is LoginViewState.Error -> "로그인 실패: $message"
}

fun main() {
    val states = listOf(
        LoginViewState.Idle,
        LoginViewState.Loading,
        LoginViewState.Success("Alice"),
        LoginViewState.Error("잘못된 비밀번호입니다.")
    )

    states.forEach { state ->
        println(state.describe())
    }
}