package practice.q29

import java.time.LocalDateTime

/**
📋 요구사항

람다를 사용하여 다음 조건을 만족하는 코드를 작성하세요 👇
createdAt이 최근 7일 이내(LocalDateTime.now().minusDays(7))인 데이터만 대상으로 한다.
type별로 그룹핑한다. (groupBy)
각 그룹의 알림 개수를 구한다. (count)
결과를 Map<String, Int> 형태로 반환한다.
키(type) 기준으로 오름차순 정렬된 Map을 반환한다. (toSortedMap())
 */


fun main() {
    val notifications = listOf(
        NotificationDto(1, "FOLLOW", "팔로워 알림", LocalDateTime.now().minusDays(1), false),
        NotificationDto(2, "COUPON", "쿠폰 도착", LocalDateTime.now().minusDays(2), false),
        NotificationDto(3, "FOLLOW", "팔로워 또 생김", LocalDateTime.now().minusDays(3), true),
        NotificationDto(4, "COMMENT", "댓글 알림", LocalDateTime.now().minusDays(8), false),
        NotificationDto(5, "COUPON", "새 쿠폰 발급", LocalDateTime.now().minusDays(1), false),
    )

    val result: Map<String, Int> = notifications
        .asSequence()
        .filter { it.createdAt.isAfter(LocalDateTime.now().minusDays(7)) }
        .groupingBy { it.type }
        .eachCount()
        .toSortedMap()
}

data class NotificationDto(
    val id: Long,
    val type: String,           // FOLLOW, COMMENT, COUPON, POST 등
    val title: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
)