package practice.q23

import java.time.LocalDateTime

/**
📘 상황

운영팀이 알림 발송 통계를 보고 싶어 합니다.
NotificationDto 목록에서 타입별(type) 미확인 알림 개수를 구해야 합니다.

📋 요구사항

isRead == false 인 알림만 대상으로 한다.

type 별로 그룹핑한다.

각 그룹별 개수(Int) 를 계산한다.

결과는 Map<String, Int> 형태로 반환한다.

출력 시 타입명 오름차순 정렬된 Map으로 만들어라.

✅ 출력 예시
{
COUPON=2,
FOLLOW=2
}


람다를 이용해 한 줄 체이닝으로 작성해보세요 👇

val result: Map<String, Int> = notifications
...


 */

data class NotificationDto(
    val id: Long,
    val type: String,          // 알림 종류 (FOLLOW, COUPON, POST_POPULAR 등)
    val title: String,         // 알림 제목
    val createdAt: LocalDateTime, // 생성 시각
    val isRead: Boolean        // 읽음 여부
)

fun main() {
    val notifications = listOf(
        NotificationDto(1, "FOLLOW", "새 팔로워가 생겼어요", LocalDateTime.now().minusHours(1), false),
        NotificationDto(2, "COUPON", "새 쿠폰이 도착했어요", LocalDateTime.now().minusDays(1), false),
        NotificationDto(3, "POST_POPULAR", "인기글 선정", LocalDateTime.now().minusMinutes(30), true),
        NotificationDto(4, "FOLLOW", "다른 팔로워가 생겼어요", LocalDateTime.now().minusMinutes(10), false),
        NotificationDto(5, "COUPON", "쿠폰이 또 왔어요", LocalDateTime.now().minusMinutes(5), false)
    )

    val result: Map<String, Int> = notifications
        .asSequence()
        .filterNot { it.isRead }
        .groupingBy { it.type }
        .eachCount()
        .toSortedMap()
}

