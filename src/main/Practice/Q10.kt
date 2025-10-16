package Leetcode.kotlin


/*
🧩 문제 10 — 주문 처리 로직을 sealed class로 모델링하시오 (백엔드 실무형)
📜 시나리오

당신은 OrderService를 만들고 있습니다.
주문은 다음 중 하나의 상태를 가질 수 있습니다 👇
Created — 주문이 생성됨
Paid — 결제 완료됨 (결제 금액 포함)
Shipped — 배송 중 (운송장 번호 포함)
Delivered — 배송 완료
Canceled — 주문 취소됨 (사유 포함)

💡 요구사항

sealed class OrderStatus를 정의하시오.
위 5가지 상태를 하위 클래스로 구현하시오.
각 상태에 필요한 데이터를 포함시키시오.
Paid → amount: Int
Shipped → trackingNo: String
Canceled → reason: String
fun OrderStatus.describe(): String 확장 함수를 작성하시오.
각 상태별로 적절한 메시지를 반환해야 함
main()에서 모든 상태를 테스트하시오.

🎯 예시 출력
📦 주문 생성됨
💳 결제 완료 (금액: 50000원)
🚚 배송 중 (운송장: CJ123456789)
✅ 배송 완료
❌ 주문 취소 (사유: 고객 요청)
 */

sealed class OrderStatus {
    data object created : OrderStatus()
    data class Paid(val amount: Int) : OrderStatus()
    data class Shipped(val trackingNo: String) : OrderStatus()
    data object Delivered : OrderStatus()
    data class Canceled(val reason: String) : OrderStatus()
}

fun OrderStatus.describe(): String = when (this) {
    is OrderStatus.created -> "📦 주문 생성됨"
    is OrderStatus.Paid -> "💳 결제 완료 (금액: ${this.amount}원)"
    is OrderStatus.Shipped -> "🚚 배송 중 (운송장: ${this.trackingNo})"
    is OrderStatus.Delivered -> "✅ 배송 완료"
    is OrderStatus.Canceled -> "❌ 주문 취소 (사유: ${this.reason})"
}