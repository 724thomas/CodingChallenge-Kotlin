package Leetcode.kotlin


/*
🧩 문제 11 — 결제(Payment) 상태 전이 모델링
📜 시나리오
결제 시스템에서는 한 거래가 다음과 같은 상태를 거칩니다 👇
Requested — 결제 요청이 들어옴
Authorized — 결제 승인됨 (PG사 승인번호 포함)
Captured — 정산 완료 (정산 일시 포함)
Refunded — 환불 완료 (환불 금액 포함)
Failed — 결제 실패 (실패 사유 포함)

💡 요구사항
sealed class PaymentStatus를 정의하시오.
위 5가지 상태를 모두 하위 타입으로 구현하시오.
Authorized → approvalCode: String
Captured → capturedAt: LocalDateTime
Refunded → amount: Int
Failed → reason: String

fun PaymentStatus.canTransitionTo(next: PaymentStatus): Boolean 함수를 작성하시오.
Requested → Authorized 가능
Authorized → Captured 가능
Captured → Refunded 가능
나머지는 모두 불가능

main() 함수에서 여러 상태 전이 케이스를 테스트하시오.

🎯 예시 출력
Requested → Authorized : true
Authorized → Captured : true
Captured → Refunded : true
Captured → Failed : false
Authorized → Requested : false
 */

sealed class PaymentStatus {
    data object Requested : PaymentStatus()
    data class Authorized(val approvalCode: String) : PaymentStatus()
    data class Captured(val capturedAt : LocalDateTime) : PaymentStatus()
    data class Refunded(val amount: Int) : PaymentStatus()
    data class Failed(val reason: String) : PaymentStatus()
}

fun PaymentStatus.canTransitionTo(next: PaymentStatus): Boolean = when (this) {
    is PaymentStatus.Requested -> next is PaymentStatus.Authorized
    is PaymentStatus.Authorized -> next is PaymentStatus.Captured
    is PaymentStatus.Captured -> next is PaymentStatus.Refunded
    is PaymentStatus.Refunded, is PaymentStatus.Failed -> false
}