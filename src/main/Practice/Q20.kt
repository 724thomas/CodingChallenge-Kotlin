package practice.q20

/**
🧩 문제 23 — ETC 기반 NotificationEvent 설계 (도메인 확장형)
📜 시나리오

현재 알림 시스템은 다양한 알림 이벤트를 발송합니다 👇

이벤트 타입	설명	예시 메시지
FOLLOW	누군가 나를 팔로우했을 때	“Alice 님이 팔로우했습니다.”
COMMENT	내 게시글에 댓글이 달렸을 때	“게시글(42)에 댓글이 달렸습니다.”
COUPON	새 쿠폰이 도착했을 때	“🎟️ 쿠폰 ABC123 도착!”

하지만 곧 새로운 알림이 계속 추가될 예정입니다.
예: LIKE, MENTION, SYSTEM_ALERT, FRIEND_REQUEST …

기존 방식은 when(eventType)으로 분기하는 구조라
새 이벤트 추가 시마다 코드 전체를 수정해야 했습니다 ❌

✅ 목표

ETC 원칙에 따라,
새 알림 이벤트가 추가되어도 기존 코드를 수정하지 않고 확장만 하면 되게끔 설계하시오.

💡 요구사항
interface NotificationEvent 정의
fun createMessage(): String
fun target(): String
fun channel(): String (ex. “FCM”, “EMAIL” 등)
알림 이벤트 구현체 작성
FollowEvent
CommentEvent
CouponEvent

NotificationProcessor 클래스 작성
fun process(event: NotificationEvent)
내부에서 event.channel()을 사용하여 알맞은 Sender(Fcm/Email/Sms) 를 호출
즉, 이벤트가 어떤 채널을 쓸지 스스로 결정함
main()에서 3가지 이벤트를 처리하시오.

🎯 예시 출력
📱 [FCM] [FOLLOW] To=user123: Alice 님이 팔로우했습니다.
💬 [FCM] [COMMENT] To=user123: 게시글(42)에 댓글이 달렸습니다.
📧 [EMAIL] [COUPON] To=test@example.com: 🎟️ 쿠폰 ABC123 도착!

🧠 힌트

NotificationProcessor는 “이벤트 타입”을 몰라야 한다 (즉, when 분기 금지)
각 이벤트가 자기 로직(createMessage(), channel())을 스스로 갖게 하면 ETC 완성
새 이벤트를 추가하려면? → class LikeEvent(...) : NotificationEvent 만 추가
 */

interface NotificationEvent {
    fun target(): String          // 누구에게 보낼지
    fun channel(): String         // 어떤 채널로 보낼지 (FCM, EMAIL 등)
    fun createMessage(): String   // 어떤 메시지를 보낼지
}

data class FollowEvent(
    val followerName: String,
    val targetUserId: String
) : NotificationEvent {
    override fun target() = targetUserId
    override fun channel() = "FCM"
    override fun createMessage() = "📣 $followerName 님이 팔로우했습니다."
}

data class CommentEvent(
    val postId: Long,
    val targetUserId: String
) : NotificationEvent {
    override fun target() = targetUserId
    override fun channel() = "FCM"
    override fun createMessage() = "💬 게시글($postId)에 댓글이 달렸습니다."
}

data class CouponEvent(
    val couponCode: String,
    val email: String
) : NotificationEvent {
    override fun target() = email
    override fun channel() = "EMAIL"
    override fun createMessage() = "🎟️ 쿠폰 $couponCode 이 도착했습니다!"
}

interface NotificationSender {
    fun send(target: String, message: String)
}

class FcmNotificationSender : NotificationSender {
    override fun send(target: String, message: String) {
        println("📱 [FCM] To=$target / $message")
    }
}

class EmailNotificationSender : NotificationSender {
    override fun send(target: String, message: String) {
        println("📧 [EMAIL] To=$target / $message")
    }
}

class NotificationProcessor(
    private val senders: Map<String, NotificationSender>
) {
    fun process(event: NotificationEvent) {
        val sender = senders[event.channel()]
            ?: throw IllegalArgumentException("지원하지 않는 채널: ${event.channel()}")
        sender.send(event.target(), event.createMessage())
    }
}

fun main() {
    val processor = NotificationProcessor(
        mapOf(
            "FCM" to FcmNotificationSender(),
            "EMAIL" to EmailNotificationSender()
        )
    )

    val events = listOf(
        FollowEvent(followerName = "Alice", targetUserId = "user123"),
        CommentEvent(postId = 42, targetUserId = "user123"),
        CouponEvent(couponCode = "ABC123", email = "test@example.com")
    )

    events.forEach { processor.process(it) }
}
