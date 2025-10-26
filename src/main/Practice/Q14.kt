package practice.q14

import java.time.LocalDateTime

/*
🧩 문제 15 — 배송(Delivery) 상태 전이 로직을 sealed class로 설계하시오
📦 시나리오

택배 배송 시스템을 설계하려고 합니다.
배송 상태는 다음과 같습니다 👇

Created — 배송이 생성됨 (송장번호가 아직 없음)
Shipped — 물품이 발송됨 (송장번호 필요)
InTransit — 배송 중 (현재 위치 필요)
Delivered — 배송 완료 (배송 완료 일시 필요)
Canceled — 배송 취소 (취소 사유 필요)

💡 요구사항

sealed class DeliveryStatus를 정의하시오.
각 상태별로 필요한 데이터를 추가하시오.
Shipped(val trackingNo: String)
InTransit(val location: String)
Delivered(val deliveredAt: LocalDateTime)
Canceled(val reason: String)

fun DeliveryStatus.canTransitionTo(next: DeliveryStatus): Boolean 을 구현하시오.
Created → Shipped ✅
Shipped → InTransit ✅
InTransit → Delivered ✅
Shipped → Canceled ✅
Created → Canceled ✅
Delivered나 Canceled 상태에서는 어떤 전이도 ❌ 불가능

main()에서 여러 상태 전이를 테스트하시오.
🎯 예시 출력
Created → Shipped : true
Shipped → InTransit : true
InTransit → Delivered : true
Created → Canceled : true
Delivered → Shipped : false
Canceled → Created : false

🧠 힌트
sealed class로 모든 상태를 정의
when (this)에서 현재 상태별 허용 전이를 분기
LocalDateTime import 필요 (import java.time.LocalDateTime)
 */

sealed class DeliveryStatus {
    data object Created : DeliveryStatus()
    data class Shipped(val trackingNo: String) : DeliveryStatus()
    data class InTransit(val lcation: String) : DeliveryStatus()
    data class Delivered(val deliveredAt: LocalDateTime) : DeliveryStatus()
    data class Canceled(val reason: String) : DeliveryStatus()
}

fun DeliveryStatus.canTransitionTo(next: DeliveryStatus): Boolean = when (this) {
    is DeliveryStatus.Created -> next is DeliveryStatus.Shipped || next is DeliveryStatus.Canceled
    is DeliveryStatus.Shipped -> next is DeliveryStatus.InTransit || next is DeliveryStatus.Canceled
    is DeliveryStatus.InTransit -> next is DeliveryStatus.Delivered
    is DeliveryStatus.Delivered, is DeliveryStatus.Canceled -> false
}

fun main() {
    println("=== Q14 실행 예제 ===")
    
    val created = DeliveryStatus.Created
    val shipped = DeliveryStatus.Shipped("1234567890")
    val inTransit = DeliveryStatus.InTransit("서울 강남구")
    val delivered = DeliveryStatus.Delivered(LocalDateTime.now())
    val canceled = DeliveryStatus.Canceled("고객 요청")
    
    println("Created → Shipped: ${created.canTransitionTo(shipped)}")
    println("Shipped → InTransit: ${shipped.canTransitionTo(inTransit)}")
    println("InTransit → Delivered: ${inTransit.canTransitionTo(delivered)}")
    println("Created → Canceled: ${created.canTransitionTo(canceled)}")
    println("Delivered → Shipped: ${delivered.canTransitionTo(shipped)}")
    println("Canceled → Created: ${canceled.canTransitionTo(created)}")
    
    println("\n=== 실행 완료 ===")
}
