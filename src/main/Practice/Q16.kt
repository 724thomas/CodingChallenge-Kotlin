package practice

import practice.q15.PointFeePolicy

/**
🧩 문제 17 — 할인(Discount) 정책 시스템을 ETC 전략으로 설계하시오
📜 시나리오
온라인 쇼핑몰의 상품 가격은 여러 할인 정책에 따라 변동됩니다.
현재 다음 네 가지 정책이 존재합니다 👇

할인 정책	할인율
MEMBER	10%
COUPON	20%
SEASONAL	15%
NONE	0%

💡 향후 “VIP”, “BLACK FRIDAY” 같은 새로운 할인 정책이 추가될 예정입니다.
따라서 기존 코드를 수정하지 않고, 확장만으로 동작해야 합니다.
💡 요구사항
interface DiscountPolicy를 정의하시오.
함수: fun apply(price: Int): Int (할인된 최종 금액 반환)
할인 정책별로 구현체를 작성하시오.
MemberDiscountPolicy, CouponDiscountPolicy, SeasonalDiscountPolicy, NoDiscountPolicy
fun processDiscount(policy: DiscountPolicy, price: Int): Int 함수를 작성하시오.
main()에서 네 가지 정책을 테스트하시오.
새로운 정책(BlackFridayDiscountPolicy)을 추가해도 기존 코드 한 줄도 바꾸지 않아야 함.
 */

interface DiscountPolicy {
    fun apply(price: Int): Int
}

class MemberDiscountPolicy : DiscountPolicy {
    override fun apply(price: Int): Int = (price - (price * 0.1)).toInt()
}

class CouponDiscountPolicy : DiscountPolicy {
    override fun apply(price: Int): Int = (price - (price * 0.2)).toInt()
}

class SeasonalDiscountPolicy : DiscountPolicy {
    override fun apply(price: Int): Int = (price - (price * 0.15)).toInt()
}

class NoDiscountPolicy : DiscountPolicy {
    override fun apply(price: Int): Int = price
}

class BlackFridayDiscountPolicy : DiscountPolicy {
    override fun apply(price: Int): Int = (price - (price * 0.3)).toInt()
}

fun processPolicy(policy: DiscountPolicy, price: Int) = policy.apply(price)

fun main() {
    val price = 10000
    println(processPolicy(MemberDiscountPolicy(), price))
    println(processPolicy(CouponDiscountPolicy(), price))
    println(processPolicy(SeasonalDiscountPolicy(), price))
    println(processPolicy(NoDiscountPolicy(), price))
    println(processPolicy(BlackFridayDiscountPolicy(), price))
}

