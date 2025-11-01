package practice.q22

import java.time.LocalDate
import java.time.LocalDateTime

/**
📘 상황

노크플레이스 서비스에서 “사용자 알림 내역”을 조회하는 API가 있어요.
이 API는 아래 DTO 리스트를 반환합니다 👇

📋 문제

관리자 페이지에서 “읽지 않은 알림 중, 오늘 생성된 알림만 제목 리스트로 정렬해 보여주기”가 필요합니다.

다음 리스트가 주어졌을 때,
람다를 활용해 아래 조건을 만족하는 List<String>을 반환하세요.

조건

isRead == false 인 알림만 선택
createdAt 이 오늘(LocalDate.now()) 인 알림만 선택
제목(title)만 추출
생성 시각(createdAt) 내림차순 정렬

💡 입력 예시


✅ 출력 예시
["댓글이 달렸어요", "새 팔로워가 생겼어요"]

 */
data class NotificationDto(
    val id: Long,
    val type: String,   // e.g., "FOLLOW", "COUPON", "POST_POPULAR"
    val title: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
)

fun main() {
    val notifications = listOf(
        NotificationDto(1, "FOLLOW", "새 팔로워가 생겼어요", LocalDateTime.now().minusHours(1), false),
        NotificationDto(2, "COUPON", "새 쿠폰이 도착했어요", LocalDateTime.now().minusDays(1), false),
        NotificationDto(3, "POST_POPULAR", "인기글에 선정되었어요", LocalDateTime.now().minusMinutes(30), true),
        NotificationDto(4, "COMMENT", "댓글이 달렸어요", LocalDateTime.now().minusMinutes(10), false)
    )
    val result = notifications
        .filter { !it.isRead }
        .filter { it.createdAt.toLocalDate() == LocalDate.now() }
        .sortedByDescending { it.createdAt }
        .map { it.title }
        .toList()
}

fun findTodayUnreadTitles(
    notifications: List<NotificationDto>,
    now: LocalDateTime = LocalDateTime.now(),
): List<String> {
    val today = now.toLocalDate()

    return notifications
        .asSequence()
        .filterNot { it.isRead }
        .filter { it.createdAt.toLocalDate() == today }
        .sortedByDescending { it.createdAt }
        .map { it.title }
        .toList()
}

