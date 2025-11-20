package practice.q30

/**

📌 Java 코드
String name = "WonJoon";
if (name != null) {
System.out.println(name.toUpperCase());
}

🎯 요구사항
name 변수를 nullable로 선언해라.
name이 null이 아닐 때만 대문자로 변환해서 출력해라.
반드시 let을 활용해라.
 */

fun main() {
    val name: String? = "WonJoon"

    name?.let { actualName ->
        println(actualName.uppercase())
    }
}

