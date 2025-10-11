package Leetcode.kotlin


/*
🧩 문제 5 — 주문 상태 처리를 Sealed Class로 설계하시오
📜 요구사항

아래 Java 코드는 단순히 enum으로 상태를 표현하고 있습니다.
이를 Kotlin의 sealed class 혹은 sealed interface 로 리팩터링하여
더 타입 안전한 분기 처리로 바꾸세요.

public enum OrderStatus {
    CREATED,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELED
}

public class Order {
    private OrderStatus status;

    public Order(OrderStatus status) {
        this.status = status;
    }

    public String getMessage() {
        switch (status) {
            case CREATED:
                return "주문이 생성되었습니다.";
            case PAID:
                return "결제가 완료되었습니다.";
            case SHIPPED:
                return "배송이 시작되었습니다.";
            case DELIVERED:
                return "배송이 완료되었습니다.";
            case CANCELED:
                return "주문이 취소되었습니다.";
            default:
                throw new IllegalStateException("잘못된 상태");
        }
    }
}

💡 변환 조건
enum 대신 sealed class 또는 sealed interface 를 사용하시오.
상태별로 별도의 data class or object 를 만들어라.
when 표현식으로 메시지를 반환하되, else 없이 모든 하위 타입을 처리해야 한다.
Order 객체의 상태는 OrderState 타입으로 관리하며,
getMessage() 대신 확장 함수를 작성하여 메시지를 구하도록 하시오.
main()에서 다양한 상태의 주문을 테스트 출력하시오.
 */

sealed class OrderStatus {
    object Created : OrderStatus()
    object Paid : OrderStatus()
    object Shipped : OrderStatus()
    object Delivered : OrderStatus()
    object Canceled : OrderStatus()
}

// sealed class에 대한 확장 함수로 메시지 책임을 분리
fun OrderStatus.message(): String = when (this) {
    OrderStatus.Created -> "주문이 생성되었습니다."
    OrderStatus.Paid -> "결제가 완료되었습니다."
    OrderStatus.Shipped -> "배송이 시작되었습니다."
    OrderStatus.Delivered -> "배송이 완료되었습니다."
    OrderStatus.Canceled -> "주문이 취소되었습니다."
}

data class Order(val status: OrderStatus) {
    fun getMessage(): String = status.message()
}

fun main() {
    val orders = listOf(
        Order(OrderStatus.Created),
        Order(OrderStatus.Paid),
        Order(OrderStatus.Shipped),
        Order(OrderStatus.Delivered),
        Order(OrderStatus.Canceled)
    )

    orders.forEach { println(it.getMessage()) }
}