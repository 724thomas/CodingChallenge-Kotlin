package practice.q17

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
🧩 문제 20 — ETC 기반 Push Notification 설계
📜 시나리오

현재 푸시 알림 시스템은 다음처럼 제목(title), 메시지(message), **추가 데이터(extra)**를 함께 보냅니다.

초기 버전은 다음과 같아요 👇

data class PushMessage(
val title: String,
val message: String,
val extra: String?
)


하지만 곧 서비스에서 여러 종류의 푸시가 추가됩니다.
알림 종류	설명	추가 데이터 구조
FOLLOW	“누군가 나를 팔로우함”	followerName
COMMENT	“내 게시글에 댓글이 달림”	commentId, postId
COUPON	“새 쿠폰이 도착함”	couponCode, expireAt
SYSTEM	“점검, 공지 등 시스템 메시지”	없음
❌ 기존 코드의 문제점
이전 설계(extra: String?)는
각 알림마다 서로 다른 구조의 데이터를 문자열로 넣다 보니
매번 when (type)으로 파싱해야 하고,
새로운 알림 타입 추가 시 PushMessage 자체를 수정해야 함 😩
즉, 변화에 약한 구조 (Hard to Change) 입니다.
✅ 목표 — ETC(Easier to Change) 방식으로 리팩터링
sealed class PushExtra 를 정의하시오.
FollowExtra(val followerName: String)
CommentExtra(val commentId: Long, val postId: Long)
CouponExtra(val couponCode: String, val expireAt: LocalDateTime)

SystemExtra (object)
data class PushMessage 는 다음 구조를 가집니다 👇
data class PushMessage(
val title: String,
val message: String,
val extra: PushExtra
)

새로운 알림 타입을 추가할 때,
기존 PushMessage 클래스는 수정하지 않아야 합니다.
describe() 확장 함수를 만들어
알림 내용을 사람이 읽기 좋은 형태로 출력하세요.

🎯 예시 출력
📣 [FOLLOW] 새 팔로워: Alice
💬 [COMMENT] 게시글(42)에 댓글(101) 등록됨
🎟️ [COUPON] 쿠폰 ABC123, 만료일 2025-10-20T18:00
🔔 [SYSTEM] 시스템 점검 안내

 */
data class PushMessage(
    val title: String,
    val message: String,
    val extra: PushExtra
)

sealed class PushExtra{
    data class FollowExtra(val followerName: String) : PushExtra()
    data class CommentExtra(val commentId: Long, val postId: Long) : PushExtra()
    data class CouponExtra(val couponCode: String, val expireAt: LocalDateTime) : PushExtra()
    data object SystemExtra : PushExtra()
}

fun PushMessage.describe(): String = when (extra) {
    is PushExtra.FollowExtra ->
        "📣 [FOLLOW] ${extra.followerName} 님이 나를 팔로우했습니다!"
    is PushExtra.CommentExtra ->
        "💬 [COMMENT] 게시글(${extra.postId})에 댓글(${extra.commentId})이 달렸습니다."
    is PushExtra.CouponExtra -> {
        val formatted = extra.expireAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        "🎟️ [COUPON] 쿠폰 ${extra.couponCode}, 만료일 $formatted"
    }
    PushExtra.SystemExtra ->
        "🔔 [SYSTEM] 시스템 공지: $message"
}

fun main() {
    val now = LocalDateTime.of(2025, 10, 20, 18, 0)

    val messages = listOf(
        PushMessage(
            title = "새 팔로워",
            message = "Alice 님이 팔로우했습니다.",
            extra = PushExtra.FollowExtra("Alice")
        ),
        PushMessage(
            title = "새 댓글",
            message = "게시글에 댓글이 달렸습니다.",
            extra = PushExtra.CommentExtra(commentId = 101, postId = 42)
        ),
        PushMessage(
            title = "쿠폰 도착",
            message = "ABC123 쿠폰이 도착했습니다.",
            extra = PushExtra.CouponExtra("ABC123", now)
        ),
        PushMessage(
            title = "시스템 점검",
            message = "10월 21일 02:00~03:00 시스템 점검 예정",
            extra = PushExtra.SystemExtra
        )
    )

    messages.forEach { println(it.describe()) }
}
