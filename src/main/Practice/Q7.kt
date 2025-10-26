package practice.q7


/*
🧩 문제 7 — 재시도 가능한 네트워크 응답 설계하기
📜 요구사항

아래 Java 코드는 단순한 응답 모델입니다.
이를 Kotlin으로 sealed interface 기반으로 리팩터링하고,
“재시도 가능한 오류”를 표현할 수 있도록 설계하세요.

public class NetworkResponse<T> {
    private T data;
    private String error;
    private boolean retryable;

    public NetworkResponse(T data, String error, boolean retryable) {
        this.data = data;
        this.error = error;
        this.retryable = retryable;
    }

    public T getData() { return data; }
    public String getError() { return error; }
    public boolean isRetryable() { return retryable; }
}

💡 변환 조건
NetworkResponse<T>를 sealed interface로 선언하시오.
하위 타입을 다음 4개로 설계하시오:
Success<T>(data: T)
ClientError(message: String)
ServerError(message: String, retryable: Boolean)
Loading
ServerError 중 retryable == true인 경우만 재시도 가능한 상태로 취급한다.

fun handle(response: NetworkResponse<T>) 함수를 작성하여,
각 상태별로 적절한 로그를 출력하시오.

main()에서 4가지 상태를 테스트하시오.
🎯 예시 출력
⏳ 요청 중...
✅ 성공: User(name=Alice)
❌ 클라이언트 오류: 잘못된 요청입니다.
⚠️ 서버 오류: 네트워크 불안정 (재시도 가능)
🔥 서버 오류: 시스템 장애 (재시도 불가)
 */

sealed interface NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>
    data class ClientError(val message: String) : NetworkResponse<Nothing>
    data class ServerError(val message: String, val retryable: Boolean) : NetworkResponse<Nothing>
    data object Loading : NetworkResponse<Nothing>
}

// 💡 pure 함수로 반환 (테스트 용이)
fun <T> NetworkResponse<T>.describe(): String = when (this) {
    is NetworkResponse.Success -> "성공: $data"
    is NetworkResponse.ClientError -> "클라이언트 오류: $message"
    is NetworkResponse.ServerError ->
        "서버 오류: $message " + if (retryable) "(재시도 가능)" else "(재시도 불가)"
    NetworkResponse.Loading -> "요청 중..."
}

fun main() {
    val responses = listOf(
        NetworkResponse.Loading,
        NetworkResponse.Success("User(name=Alice)"),
        NetworkResponse.ClientError("잘못된 요청입니다."),
        NetworkResponse.ServerError("네트워크 불안정", retryable = true),
        NetworkResponse.ServerError("시스템 장애", retryable = false)
    )

    responses.forEach { println(it.describe()) }
}
