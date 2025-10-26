package practice.q21

import java.time.LocalDateTime

/**
🧩 문제 24 — ETC 기반 NotificationTemplate 설계 (메시지 생성 확장형)
📜 시나리오

현재 시스템에서는 알림을 보낼 때마다 메시지를 문자열로 직접 작성하고 있습니다 👇
val message = "Alice님이 게시글에 댓글을 달았습니다."

하지만 서비스가 커지면서 다양한 알림 종류와 언어가 추가됩니다 👇

알림 타입	예시 메시지 (한국어)	예시 메시지 (영어)
FOLLOW	“{name}님이 팔로우했습니다.”	“{name} started following you.”
COMMENT	“{name}님이 댓글을 남겼습니다.”	“{name} left a comment.”
COUPON	“🎟️ 쿠폰 {code} 도착!”	“🎟️ Coupon {code} arrived!”

문제는 알림이 늘어날 때마다
when(type)으로 분기하는 코드가 계속 늘어나는 것입니다 ❌

✅ 목표

ETC 원칙에 따라,
새 알림 타입이나 새 언어를 추가해도
기존 코드는 수정하지 않고,
템플릿 클래스만 추가하면 확장되도록 설계하세요.

💡 요구사항
interface NotificationTemplate 정의

interface NotificationTemplate {
fun support(type: String, language: String): Boolean
fun render(data: Map<String, Any>): String
}


템플릿 구현체 작성

FollowKoTemplate (“{name}님이 팔로우했습니다.”)
CommentKoTemplate (“{name}님이 댓글을 남겼습니다.”)
CouponKoTemplate (“🎟️ 쿠폰 {code} 도착!”)

NotificationTemplateEngine 클래스 작성
내부에 List<NotificationTemplate> 보유
fun render(type: String, language: String, data: Map<String, Any>): String
알맞은 템플릿을 찾아서 render() 실행
main()에서 3가지 알림을 테스트하시오.

🎯 예시 출력
📣 FOLLOW (ko): Alice님이 팔로우했습니다.
💬 COMMENT (ko): Bob님이 댓글을 남겼습니다.
🎟️ COUPON (ko): 🎟️ 쿠폰 ABC123 도착!
 */

enum class NotificationLanguage(val code: String) {
    KO("ko"),
    EN("en"),
    JP("jp");

    companion object {
        fun from(code: String): NotificationLanguage? =
            entries.find { it.code.equals(code, ignoreCase = true) }
    }
}

enum class NotificationType(val type: String) {
    FOLLOW("follow"),
    COMMENT("comment"),
    COUPON("coupon");

    companion object {
        fun from(type: String): NotificationType? =
            entries.find { it.type.equals(type, ignoreCase = true) }
    }
}


interface NotificationTemplate {
    fun support(type: String, language: String): Boolean
    fun render(data: Map<String, Any>): String
}

class FollowKoTemplate : NotificationTemplate {
    override fun support(type: String, language: String): Boolean {
        val langEnum = NotificationLanguage.from(language)
        val typeEnum = NotificationType.from(type)
        return typeEnum == NotificationType.FOLLOW && langEnum == NotificationLanguage.KO
    }

    override fun render(data: Map<String, Any>): String {
        val name = data["name"] as? String ?: "누군가"
        return "📣 $name 님이 팔로우했습니다."
    }
}

class CommentKoTemplate : NotificationTemplate {
    override fun support(type: String, language: String): Boolean {
        val langEnum = NotificationLanguage.from(language)
        val typeEnum = NotificationType.from(type)
        return typeEnum == NotificationType.COMMENT && langEnum == NotificationLanguage.KO
    }

    override fun render(data: Map<String, Any>): String {
        val name = data["name"] as? String ?: "누군가"
        val postId = data["postId"] as? Long ?: 0L
        return "💬 $name 님이 게시글($postId)에 댓글을 남겼습니다."
    }
}

class CouponKoTemplate : NotificationTemplate {
    override fun support(type: String, language: String): Boolean {
        val langEnum = NotificationLanguage.from(language)
        val typeEnum = NotificationType.from(type)
        return typeEnum == NotificationType.COUPON && langEnum == NotificationLanguage.KO
    }

    override fun render(data: Map<String, Any>): String {
        val code = data["code"] as? String ?: "UNKNOWN"
        val expireAt = data["expireAt"] as? LocalDateTime
        val expireText = expireAt?.toString() ?: "만료일 미정"
        return "🎟️ 쿠폰 $code 도착! (만료일: $expireText)"
    }
}

class NotificationTemplateEngine(
    private val templates: List<NotificationTemplate>
) {
    fun render(type: String, language: String, data: Map<String, Any>): String {
        val template = templates.find { it.support(type, language) }
            ?: throw IllegalArgumentException("지원되지 않는 템플릿: type=$type, language=$language")
        return template.render(data)
    }
}

fun main() {
    val engine = NotificationTemplateEngine(
        listOf(
            FollowKoTemplate(),
            CommentKoTemplate(),
            CouponKoTemplate()
        )
    )

    val messages = listOf(
        engine.render(
            type = "follow",
            language = "ko",
            data = mapOf("name" to "Alice")
        ),
        engine.render(
            type = "comment",
            language = "ko",
            data = mapOf("name" to "Bob", "postId" to 42L)
        ),
        engine.render(
            type = "coupon",
            language = "ko",
            data = mapOf("code" to "ABC123", "expireAt" to LocalDateTime.of(2025, 10, 30, 18, 0))
        )
    )

    messages.forEach { println(it) }
}