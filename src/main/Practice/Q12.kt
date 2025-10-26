package practice.q12


/*
🧩 문제 12 — API 응답 결과를 sealed class로 설계하시오
📜 시나리오

서버에서 클라이언트로 응답을 내려줄 때
응답에는 세 가지 형태가 있습니다 👇
성공 (Success) — 요청 처리 성공, 데이터 포함
클라이언트 오류 (ClientError) — 잘못된 요청, 에러 메시지 포함
서버 오류 (ServerError) — 내부 서버 에러, 로그용 코드 포함

💡 요구사항
sealed class ApiResult<out T>를 정의하시오.

하위 타입 3개를 작성하시오.
data class Success<out T>(val data: T)
data class ClientError(val message: String)
data class ServerError(val errorCode: String)

fun ApiResult<T>.toHttpStatus(): Int 함수를 작성하시오.
Success → 200
ClientError → 400
ServerError → 500

main()에서 3가지 결과를 테스트하시오.

🎯 예시 출력
✅ 성공 응답 → HTTP 200
❌ 클라이언트 오류: 잘못된 요청 → HTTP 400
🔥 서버 오류: DB_CONN_FAIL → HTTP 500

 */

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class ClientError(val message: String) : ApiResult<Nothing>()
    data class ServerError(val errorCode: String) : ApiResult<Nothing>()
}

// HTTP 상태 코드 매핑
fun <T> ApiResult<T>.toHttpStatus(): Int = when (this) {
    is ApiResult.Success -> 200
    is ApiResult.ClientError -> 400
    is ApiResult.ServerError -> 500
}

// 예시 출력용 확장 함수
fun <T> ApiResult<T>.describe(): String = when (this) {
    is ApiResult.Success -> "✅ 성공 응답 → HTTP ${toHttpStatus()}"
    is ApiResult.ClientError -> "❌ 클라이언트 오류: $message → HTTP ${toHttpStatus()}"
    is ApiResult.ServerError -> "🔥 서버 오류: $errorCode → HTTP ${toHttpStatus()}"
}

fun main() {
    val responses = listOf(
        ApiResult.Success("User(id=1, name=Alice)"),
        ApiResult.ClientError("잘못된 요청입니다."),
        ApiResult.ServerError("DB_CONN_FAIL")
    )

    responses.forEach { println(it.describe()) }
}
