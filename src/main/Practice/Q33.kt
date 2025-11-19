package practice.q33

/**
user가 장소(place)를 등록할 때,
주소 문자열(addressString) 을 전달한다.

이 문자열이 비어있거나 공백이면 null로 처리,
그 외라면 내부적으로 사용하는 Address 객체로 변환해야 한다.

Java에서는 다음처럼 작성됐다고 하자:

String addressString = request.getAddress();

if (addressString != null && !addressString.trim().isEmpty()) {
Address address = new Address(addressString.trim());
user.setAddress(address);
}

🎯 요구사항

Kotlin으로 다음 함수를 완성하라:

fun applyAddress(user: User, addressString: String?) {
// 여기 내부를 let을 이용해서 구현
}


조건:
addressString이
null이거나
trim() 결과가 빈 문자열이면
→ user.address = null

그 외라면
→ Address(trimmedValue) 로 변환하여 user.address에 넣어라
반드시 let을 활용해야 한다
불필요한 if문을 최소화하라
가독성 좋은 코드로 작성하라

📌 참고 데이터 모델
data class User(
var address: Address?
)

data class Address(
val value: String
)

🧩 예시
val user = User(null)

applyAddress(user, "    Seoul ")
→ user.address = Address("Seoul")

applyAddress(user, "   ")
→ user.address = null

applyAddress(user, null)
→ user.address = null
 */

fun applyAddress(user: User, addressString: String?) {
    user.apply {
        address =
            addressString
                ?.trim()
                ?.let {
                    if (it.isEmpty()) null
                    else Address(it)
            }
    }
}

fun applyAddress2(user: User, addressString: String?) {
    user.apply {
        address = addressString
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let { Address(it) }
    }
}

data class User(
    var address: Address?
)

data class Address(
    val value: String
)

fun main() {

}

