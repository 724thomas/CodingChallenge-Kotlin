package Leetcode.kotlin


/*
🧩 문제 6 — API 결과를 sealed class로 표현하시오
📜 요구사항

다음 Java 코드를 idiomatic Kotlin으로 리팩터링하시오.
단, sealed class를 활용하여 API 응답의 성공/실패/로딩 상태를 명확히 구분해야 합니다.

public class ApiResponse<T> {
    private T data;
    private String errorMessage;
    private boolean isLoading;

    public ApiResponse(T data, String errorMessage, boolean isLoading) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.isLoading = isLoading;
    }

    public T getData() { return data; }
    public String getErrorMessage() { return errorMessage; }
    public boolean isLoading() { return isLoading; }
}

💡 변환 조건
ApiResponse<T>를 sealed class 로 설계하시오.
(하위 클래스: Success, Error, Loading)
제네릭 타입 T는 그대로 유지하시오.
상태별로 필요한 필드를 정의하시오.
Success → data: T
Error → message: String
Loading → 데이터 없음
when 표현식을 이용해 상태에 따라 적절한 로그를 출력하는 함수를 작성하시오.
main()에서 3가지 상태를 각각 출력하는 테스트 코드를 작성하시오.

🎯 예시 출력
데이터 로딩 중...
성공: User(name=Alice)
실패: 서버 오류 발생

 */

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}

// 💡 확장 함수로 책임 분리 (sealed class는 순수 모델 역할만)
fun <T> ApiResponse<T>.log() = when (this) {
    is ApiResponse.Success -> println("성공: $data")
    is ApiResponse.Error -> println("실패: $message")
    ApiResponse.Loading -> println("데이터 로딩 중...")
}

// ✅ 테스트용 main
fun main() {
    val responses = listOf(
        ApiResponse.Loading,
        ApiResponse.Success("User(name=Alice)"),
        ApiResponse.Error("서버 오류 발생")
    )

    responses.forEach { it.log() }
}
