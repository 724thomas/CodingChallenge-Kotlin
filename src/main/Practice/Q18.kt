package practice

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
🧩 문제 21 — ETC 기반 이벤트 로깅 시스템 설계
📜 시나리오

서비스에는 다양한 이벤트가 발생합니다.
예를 들어 👇

이벤트 타입	설명	추가 데이터
USER_LOGIN	유저 로그인 이벤트	userId
USER_SIGNUP	회원가입 이벤트	email
PURCHASE	상품 결제 이벤트	orderId, amount
SYSTEM_ALERT	서버 장애 / 경고 알림	severity, message

이전에는 단순히 "eventType": String, "data": String 으로 관리했지만,
새 이벤트가 추가될 때마다 파싱 코드와 로거(Logger)를 수정해야 했습니다.
(즉, “Hard to Change” 구조 💥)

✅ 목표
ETC 원칙을 적용해서,
새로운 이벤트 타입을 추가해도 기존 코드 수정 없이 확장 가능
타입 안정성(Type-safe) 을 유지하도록 설계

💡 요구사항

data class EventLog(val timestamp: LocalDateTime, val event: EventType)
→ 공통 로깅 구조

sealed class EventType 하위에 다음 4가지 이벤트 정의:
UserLogin(val userId: Long)
UserSignup(val email: String)
Purchase(val orderId: String, val amount: Int)
SystemAlert(val severity: String, val message: String)
fun EventLog.describe(): String 함수를 구현하시오.

각 이벤트를 사람이 읽을 수 있는 문자열로 변환
main()에서 여러 이벤트를 생성하고 로그를 출력하시오.
새로운 이벤트(PasswordReset(val email: String)) 추가 시,
기존 코드 수정 없이 확장만으로 작동해야 함.

🎯 예시 출력
[2025-10-17 10:00] 👤 로그인: userId=1001
[2025-10-17 10:01] 📝 회원가입: email=test@example.com
[2025-10-17 10:02] 💳 결제 완료: orderId=ORD1234, amount=25000원
[2025-10-17 10:03] ⚠️ 시스템 경고: HIGH - DB Connection Timeout

🧠 힌트

sealed class EventType + when (event) 조합으로 타입 안정성 확보

EventLog는 절대 수정하지 않고,
새로운 이벤트 추가는 EventType의 하위 클래스 추가로만 해결

LocalDateTime 사용 (import java.time.LocalDateTime)
 */

data class EventLog(
    val timestamp: LocalDateTime,
    val event: EventType
)

sealed class EventType {
    data class UserLogin(val userId: Long) : EventType()
    data class UserSignup(val email: String) : EventType()
    data class Purchase(val orderId: String, val amount: Int) : EventType()
    data class SystemAlert(val severity: String, val message: String) : EventType()
}

fun EventLog.describe(): String {
    val formattedTime = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    return when (event) {
        is EventType.UserLogin ->
            "[$formattedTime] 👤 로그인: userId=${event.userId}"
        is EventType.UserSignup ->
            "[$formattedTime] 📝 회원가입: email=${event.email}"
        is EventType.Purchase ->
            "[$formattedTime] 💳 결제 완료: orderId=${event.orderId}, amount=${event.amount}원"
        is EventType.SystemAlert ->
            "[$formattedTime] ⚠️ 시스템 경고: ${event.severity} - ${event.message}"
    }
}

// 테스트용 main
fun main() {
    val now = LocalDateTime.of(2025, 10, 17, 10, 0)
    val logs = listOf(
        EventLog(now, EventType.UserLogin(userId = 1001)),
        EventLog(now.plusMinutes(1), EventType.UserSignup(email = "test@example.com")),
        EventLog(now.plusMinutes(2), EventType.Purchase(orderId = "ORD1234", amount = 25000)),
        EventLog(now.plusMinutes(3), EventType.SystemAlert(severity = "HIGH", message = "DB Connection Timeout"))
    )
    logs.forEach { println(it.describe()) }
}


