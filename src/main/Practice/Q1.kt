package Leetcode.kotlin


/*
🧩 문제 1 — 클래스 설계와 초기화

다음 Java 코드를 Kotlin으로 idiomatic하게 변환하시오.

public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("나이는 음수가 될 수 없습니다.");
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("나이는 음수가 될 수 없습니다.");
        }
        this.age = age;
    }
}


요구사항
코틀린의 primary constructor, init block, custom getter/setter를 활용하시오.
name은 항상 대문자로 반환되어야 한다.
age는 0 이상이어야 하며, 검증은 생성자와 setter 양쪽에 있어야 한다.
setter를 호출하지 못하도록 val로 바꾸는 경우, 왜 문제가 되는지 주석으로 설명하시오.
Kotlin 코드 마지막에 main()을 작성해 객체를 생성하고 검증이 작동함을 확인하시오.
 */

data class User(
    private var name: String,
    private var age: Int,
) {
    var name: String
        get() = name.uppercase()
        private set(value) {
            name = value
        }

    var age: Int
        get() = age
        set (value) {
            require(value >=0) { "나이는 음수가 될 수 없습니다." }
            age = value
        }

    init {
        require(age>=0) { "나이는 음수가 될 수 없습니다." }
    }


}
