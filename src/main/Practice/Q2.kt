package Leetcode.kotlin


/*
🧩 문제 2 — 상속과 접근 제어

아래의 Java 코드를 Kotlin으로 변환하고,
Kotlin의 open, override, protected, internal 등을 적절히 사용하시오.

public abstract class Vehicle {
    protected int speed;

    public Vehicle(int speed) {
        this.speed = speed;
    }

    public abstract void move();

    public void printSpeed() {
        System.out.println("Speed: " + speed);
    }
}

public class Car extends Vehicle {
    private String brand;

    public Car(String brand, int speed) {
        super(speed);
        this.brand = brand;
    }

    @Override
    public void move() {
        System.out.println(brand + "가 " + speed + "km/h로 이동 중입니다.");
    }
}


요구사항
Vehicle은 abstract class로 선언하시오.
speed의 가시성을 Kotlin스럽게 바꾸되, 상속 관계가 깨지지 않아야 한다.
Car 클래스는 move()를 override하고, printSpeed()를 그대로 활용해야 한다.
Kotlin에서의 protected 접근 범위를 코드 내 주석으로 비교 설명하시오.
main()에서 Car 객체를 만들어 출력해보시오.
 */

abstract class Vehicle(
    protected val speed: Int
) {
    abstract fun move()

    fun printSpeed() {
        println("Speed: $speed")
    }
}

class Car(
    private val brand: String,
    speed: Int
) : Vehicle(speed) {

    override fun move() {
        println("${brand}가 ${speed}km/h로 이동 중입니다.")
    }
}