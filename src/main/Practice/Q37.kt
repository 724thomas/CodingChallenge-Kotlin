package practice.q37

/**
 * 아래 User 클래스가 있다.
 *
 * data class User(
 *     var name: String = "",
 *     var age: Int = 0,
 * )
 *
 * 요구사항
 *
 * apply를 사용해서 다음 조건을 만족하는 User 객체를 만들어라.
 *
 * name = "WonJoon"
 *
 * age = 20
 *
 * 생성된 User를 반환해야 한다
 *
 * 함수 시그니처 (이건 고정)
 * fun createUser(): User {
 *     // 여기에 코드 작성
 * }
 *
 * 제한 조건
 *
 * apply 반드시 사용
 *
 * User(...) 생성자 안에서 값 세팅 ❌
 *
 * 객체 생성 후 값 설정은 apply 블록 안에서만
 */

data class User(
    var name: String = "",
    var age: Int = 0,
)

fun createUser(): User {
    return User().apply {
        name = "WonJoon"
        age = 20
    }
}