package practice.q26

import java.time.LocalDateTime

/**
📋 요구사항

람다와 고차함수를 이용해 아래 조건을 만족하는 유연한 구조를 만들어보세요 👇

기본 필터 조건은 다음과 같다

isRead == false

createdAt 이 LocalDateTime.now().minusDays(3) 이후

특정 타입(excludedTypes)은 제외해야 한다. (예: listOf("COUPON"))

필터 로직을 함수로 분리하고, 조합할 수 있게 작성해야 한다.
(→ 함수형 컴포지션으로 “easier to change” 구조를 만든다)

최종적으로 List<NotificationDto> 를 반환한다.
 */

fun main() {
    val notifications = listOf(
        NotificationDto(1, "FOLLOW", "새 팔로워", LocalDateTime.now().minusDays(1), false),
        NotificationDto(2, "COUPON", "쿠폰 도착", LocalDateTime.now().minusHours(2), false),
        NotificationDto(3, "COMMENT", "댓글 알림", LocalDateTime.now().minusDays(2), true),
        NotificationDto(4, "FOLLOW", "다른 팔로워", LocalDateTime.now().minusDays(4), false),
        NotificationDto(5, "POST_POPULAR", "인기글 선정", LocalDateTime.now().minusHours(10), false),
    )

    val result: List<NotificationDto> = notifications
        .asSequence()
        .filter(unreadFilter())
        .filter(recentFilter(3))
        .filter(excludeTypesFilter(listOf("COUPON")))
        .toList()

    val filters = listOf(
        unreadFilter(),
        recentFilter(3),
        excludeTypesFilter(listOf("COUPON"))
    )

    val result2 = applyFilters(filters, notifications)


}

data class NotificationDto(
    val id: Long,
    val type: String,
    val title: String?,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
)
fun applyFilters(
    filters: List<(NotificationDto) -> Boolean>,
    notifications: List<NotificationDto>,
): List<NotificationDto> =
    notifications
        .asSequence()
        .filter { dto -> filters.all { it(dto) } }
        .toList()

fun unreadFilter(): (NotificationDto) -> Boolean = { !it.isRead }

fun recentFilter(days: Long): (NotificationDto) -> Boolean = { it.createdAt.isAfter(LocalDateTime.now().minusDays(days)) }

fun excludeTypesFilter(excludedTypes: List<String>): (NotificationDto) -> Boolean = {
    !excludedTypes.contains(it.type)
}
