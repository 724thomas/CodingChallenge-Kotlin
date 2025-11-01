package practice

import java.time.LocalDateTime

/**
“알림 목록을 최신순으로 정렬하고,
offset, limit 값을 받아 페이징 처리할 수 있게 해주세요.
기본 필터(읽지 않은 알림, N일 이내, 제외 타입)는 그대로 유지해요.”

람다와 고차함수를 활용해 다음 조건을 만족하세요 👇
기본 필터 구조는 이전 문제와 동일 (unreadFilter, recentFilter, excludeTypesFilter)
정렬 기준은 createdAt 내림차순
offset과 limit 파라미터를 받아 페이징 처리
함수형 조합(applyFilters, applySorting, applyPaging) 구조로 작성
최종 결과를 List<NotificationDto> 로 반환
 */

fun main() {
    val notifications = listOf(
        NotificationDto(1, "FOLLOW", "새 팔로워", LocalDateTime.now().minusDays(1), false),
        NotificationDto(2, "COUPON", "쿠폰 도착", LocalDateTime.now().minusHours(2), false),
        NotificationDto(3, "COMMENT", "댓글 알림", LocalDateTime.now().minusDays(2), true),
        NotificationDto(4, "FOLLOW", "다른 팔로워", LocalDateTime.now().minusDays(4), false),
        NotificationDto(5, "POST_POPULAR", "인기글 선정", LocalDateTime.now().minusHours(10), false),
        NotificationDto(6, "FOLLOW", "팔로워가 또 생김", LocalDateTime.now().minusMinutes(5), false),
    )

    val filters: List<(NotificationDto) -> Boolean> = listOf(
        unreadFilter(),
        recentFilter(3),
        excludeTypesFilter(listOf("COUPON"))
    )

    val result = notifications
        .let { applyFilters(filters, it) }       // 1️⃣ 필터링
        .let(::applySorting)                     // 2️⃣ 정렬 (함수 참조)
        .let { applyPaging(it, offset = 0, limit = 2) } // 3️⃣ 페이징

    println(result)
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
    notifications: List<NotificationDto>
): List<NotificationDto> =
    notifications.filter { dto -> filters.all { it(dto) } }

fun unreadFilter(): (NotificationDto) -> Boolean = { !it.isRead }

fun recentFilter(days: Long): (NotificationDto) -> Boolean =
    { it.createdAt.isAfter(LocalDateTime.now().minusDays(days)) }

fun excludeTypesFilter(excluded: List<String>): (NotificationDto) -> Boolean =
    { it.type !in excluded }

fun applySorting(notifications: List<NotificationDto>): List<NotificationDto> =
    notifications.sortedByDescending { it.createdAt }

fun applyPaging(notifications: List<NotificationDto>, offset: Int, limit: Int): List<NotificationDto> =
    notifications.drop(offset).take(limit)
