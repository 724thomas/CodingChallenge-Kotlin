package practice.q15


/*
🚀 실습 문제로 ETC 전략 연습하기
🧩 문제 16 — 결제 수수료(Fee) 계산 로직을 ETC 스타일로 설계하시오
📜 시나리오

결제 타입에 따라 수수료 계산 방식이 달라집니다 👇

결제 방식	수수료 계산식
CARD	금액의 10%
CASH	금액의 5%
PAYPAL	금액의 8%
POINT	수수료 없음
💡 요구사항

기존 when문 대신, ETC 원칙에 맞게 설계하시오.

interface FeePolicy를 선언하고, 결제 타입별로 구현체를 작성하시오.

processFee(policy: FeePolicy, amount: Int) 함수를 통해 수수료를 계산하시오.

main()에서 4가지 결제 타입을 테스트하시오.

🎯 예시 출력
CARD 결제 → 수수료: 1000
CASH 결제 → 수수료: 500
PAYPAL 결제 → 수수료: 800
POINT 결제 → 수수료: 0
 */


interface FeePolicy {
    fun calculate(amount: Int): Int
}

class CardFeePolicy : FeePolicy {
    override fun calculate(amount: Int) = (amount * 0.1).toInt()
}

class CashFeePolicy : FeePolicy {
    override fun calculate(amount: Int) = (amount * 0.05).toInt()
}

class PaypalFeePolicy : FeePolicy {
    override fun calculate(amount: Int) = (amount * 0.08).toInt()
}

class PointFeePolicy : FeePolicy {
    override fun calculate(amount: Int) = 0
}

fun processFee(policy: FeePolicy, amount: Int): Int {
    return policy.calculate(amount)
}

fun main() {
    println("=== Q15 실행 예제 ===")
    
    val amount = 10000
    
    val cardFee = processFee(CardFeePolicy(), amount)
    println("CARD 결제 (${amount}원) → 수수료: ${cardFee}원")
    
    val cashFee = processFee(CashFeePolicy(), amount)
    println("CASH 결제 (${amount}원) → 수수료: ${cashFee}원")
    
    val paypalFee = processFee(PaypalFeePolicy(), amount)
    println("PAYPAL 결제 (${amount}원) → 수수료: ${paypalFee}원")
    
    val pointFee = processFee(PointFeePolicy(), amount)
    println("POINT 결제 (${amount}원) → 수수료: ${pointFee}원")
    
    println("\n=== 실행 완료 ===")
}