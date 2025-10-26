package practice

/**
🧩 문제 22 — ETC 기반 NotificationSender 설계 (행동 확장형)
📜 시나리오

현재 시스템은 여러 푸시 발송 채널을 지원합니다.

채널	설명
FCM	Firebase를 통해 모바일 푸시 전송
EMAIL	이메일 발송
SMS	문자 메시지 발송

지금은 when (channelType) 으로 분기하여 발송을 처리하지만,
새 채널(SLACK, DISCORD, WEB_PUSH)이 추가될 때마다
기존 코드를 수정해야 합니다 ❌

이걸 ETC(Easier to Change) 전략으로 리팩터링하세요.
즉, “채널을 추가하더라도 기존 코드를 건드리지 않고 확장 가능”해야 합니다.

💡 요구사항
interface NotificationSender 정의
interface NotificationSender {
fun send(target: String, title: String, message: String)
}

채널별 구현체 작성
FcmNotificationSender
EmailNotificationSender
SmsNotificationSender
NotificationService 클래스 작성

내부에 Map<String, NotificationSender> 형태로 채널을 등록
fun send(channel: String, target: String, title: String, message: String)
→ 등록된 Sender를 찾아서 실행

main()에서 다음 발송을 테스트하시오
FCM → "모바일 푸시 전송"
EMAIL → "이메일 전송"
SMS → "문자 전송"

🎯 예시 출력
📱 FCM 발송: [To: user123] 제목=공지 / 내용=앱 업데이트 완료
📧 EMAIL 발송: [To: test@example.com] 제목=이벤트 / 내용=신규 쿠폰 도착
💬 SMS 발송: [To: 010-1234-5678] 제목=인증번호 / 내용=123456

🧠 힌트

ETC 핵심 포인트:
NotificationService는 절대 수정되지 않아야 함.
새로운 발송 채널을 추가할 때는
NotificationSender 구현체만 추가하면 됨.

실무에서는 이 구조가 Strategy + Registry Pattern 으로 발전합니다.
 */
interface NotificationSender {
    fun send(target: String, title: String, message: String)
}

class FcmNotificationSender : NotificationSender {
    override fun send(target: String, title: String, message: String) {
        println("📱 FCM 발송: [To: $target] 제목=$title / 내용=$message")
    }
}

class EmailNotificationSender : NotificationSender {
    override fun send(target: String, title: String, message: String) {
        println("📧 EMAIL 발송: [To: $target] 제목=$title / 내용=$message")
    }
}

class SmsNotificationSender : NotificationSender {
    override fun send(target: String, title: String, message: String) {
        println("💬 SMS 발송: [To: $target] 제목=$title / 내용=$message")
    }
}

class NotificationService(
    private val senders: Map<String, NotificationSender>
) {
    fun send(channel: String, target: String, title: String, message: String) {
        val sender = senders[channel]
            ?: throw IllegalArgumentException("지원하지 않는 채널: $channel")
        sender.send(target, title, message)
    }
}

fun main() {
    val service = NotificationService(
        mapOf(
            "FCM" to FcmNotificationSender(),
            "EMAIL" to EmailNotificationSender(),
            "SMS" to SmsNotificationSender()
        )
    )

    service.send("FCM", "user123", "공지", "앱 업데이트 완료")
    service.send("EMAIL", "test@example.com", "이벤트", "신규 쿠폰 도착")
    service.send("SMS", "010-1234-5678", "인증번호", "123456")
}

/*
package com.example.notification.service

import org.springframework.stereotype.Service
import com.example.notification.sender.NotificationSender

@Service
class NotificationService(
    private val senders: Map<String, NotificationSender> // Key: 채널명
) {

    fun sendNotification(channel: String, target: String, title: String, message: String) {
        val sender = senders[channel]
            ?: throw IllegalArgumentException("지원하지 않는 채널: $channel")

        sender.send(target, title, message)
    }
}

각 채널별 Sender 구현체들
package com.example.notification.sender

interface NotificationSender {
    fun send(target: String, title: String, message: String)
}

@Component("FCM")
class FcmNotificationSender : NotificationSender {
    override fun send(target: String, title: String, message: String) {
        println("📱 [FCM] Send to=$target / title=$title / message=$message")
        // 실제 FCM SDK 호출 로직 (FirebaseMessaging.getInstance().send(...) 등)
    }
}

@Component("EMAIL")
class EmailNotificationSender : NotificationSender {
    override fun send(target: String, title: String, message: String) {
        println("📧 [EMAIL] Send to=$target / title=$title / message=$message")
        // 실제 JavaMailSender 사용 로직
    }
}

@Component("SMS")
class SmsNotificationSender : NotificationSender {
    override fun send(target: String, title: String, message: String) {
        println("💬 [SMS] Send to=$target / title=$title / message=$message")
        // 실제 문자 발송 API 호출
    }
}
 */