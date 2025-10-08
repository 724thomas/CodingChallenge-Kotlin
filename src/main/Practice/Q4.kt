package Leetcode.kotlin


/*
🧩 문제 4 — Scope Function을 활용한 객체 초기화와 검증
📜 요구사항

아래 Java 코드를 Kotlin스럽게 리팩터링하시오.
단, scope function(apply, let, run, also, with)을 활용해야 합니다.

public class User {
    private String name;
    private int age;
    private String email;

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("나이는 음수가 될 수 없습니다.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }
}

public class UserService {
    public User register(String name, int age, String email) {
        User user = new User(name, age, email);
        user.validate();
        System.out.println("등록 완료: " + user);
        return user;
    }
}

💡 변환 조건
data class User로 선언
validate() 내부 검증 로직은 그대로 유지하되, scope function(apply, run, also, let, with) 중 하나 이상을 사용하여 리팩터링
UserService.register()는 객체 생성 후 검증 및 로그를 method chaining으로 수행
main() 함수에서 정상 동작 예시 출력
 */

data class User(
    val name: String,
    val age: Int,
    val email: String,
) {
    fun validate() = apply {
        require(name.isNotBlank()) { "이름은 비어있을 수 없습니다." }
        require(age >= 0) { "나이는 음수가 될 수 없습니다." }
        require("@" in email) { "올바른 이메일 형식이 아닙니다." }
    }
}

class UserService {
    fun register(name: String, age: Int, email: String): User =
        User(name, age, email)
            .validate()
            .also { println("등록 완료: $it") }
}


data class User2 (
    val name: String,
    val age: Int,
    val email: String,
) {
    init {
        require(name.isNotBlank()) {"이름은 비어있을 수 없습니다." }
        require(age >= 0) {"나이는 음수가 될 수 없습니다." }
        require(email.contains("@")) {"올바른 이메일 형식이 아닙니다." }
    }
}

class UserService2 {
    fun register(
        name: String,
        age: Int,
        email: String,
    ): User2 =
        User2(name, age, email).also {
        println("등록 완료: $it")
    }
}