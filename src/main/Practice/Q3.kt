package practice.q3

/*
📜 요구사항

다음 Java 코드를 Kotlin스럽게 변환 + 개선하세요.
(함수형 컬렉션 API와 확장함수를 활용해야 합니다.)

public class Order {
    private String customer;
    private List<Integer> prices;

    public Order(String customer, List<Integer> prices) {
        this.customer = customer;
        this.prices = prices;
    }

    public String getCustomer() {
        return customer;
    }

    public List<Integer> getPrices() {
        return prices;
    }
}

public class OrderService {
    public int totalPrice(List<Order> orders, String customer) {
        int total = 0;
        for (Order order : orders) {
            if (order.getCustomer().equals(customer)) {
                for (Integer price : order.getPrices()) {
                    total += price;
                }
            }
        }
        return total;
    }
}

🧠 문제 요점
data class 활용
filter + flatMap + sum 등 코틀린 컬렉션 함수형 연산 활용
OrderService의 totalPrice()를 확장함수로 대체
null 가능한 컬렉션도 안전하게 처리
main()에서 테스트 출력
 */

data class Order(
    val customer: String,
    val prices: List<Int> = emptyList()
)

// 확장 함수로 변환
fun List<Order>?.totalPriceForCustomer(customer: String): Int = 
    this?.filter { it.customer == customer }
        ?.flatMap { it.prices }
        ?.sum()
        ?: 0

fun main() {
    println("=== Q3 실행 예제 ===")
    
    val orders = listOf(
        Order("홍길동", listOf(1000, 2000, 3000)),
        Order("김철수", listOf(5000, 3000)),
        Order("홍길동", listOf(1500, 2500))
    )
    
    val total = orders.totalPriceForCustomer("홍길동")
    println("홍길동의 총 구매액: $total")
    
    val emptyTotal = null.totalPriceForCustomer("홍길동")
    println("null 컬렉션 처리: $emptyTotal")
    
    println("=== 실행 완료 ===")
}

