package practice.q31

/**
아래 Java 코드를 Kotlin으로 변환하되,
let을 반드시 활용하여 null-safe하게 처리해야 한다.

📌 Java 코드
Integer length = null;
if (length != null) {
int doubled = length * 2;
System.out.println(doubled);
}

🎯 요구사항

length를 Kotlin에서 nullable Int (Int?) 로 선언할 것
null이 아닐 때만
값을 2배로 계산
출력할 것
반드시 let을 활용할 것
코드 길이는 짧을수록 좋음
마지막 줄의 expression 결과를 반환하도록 함수로 작성해도 좋음 (선택)
 */

fun main() {
    val length: Int? = null

    length?.let { nonNullLength ->
        val doubled = nonNullLength * 2
        println(doubled)
    }
}

