package practice.q25

import java.time.LocalDateTime

/**


 */

data class NotificationDto(
    val id: Long,
    val type: String,
    val title: String?,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
)

data class NotificationResponse(
    val id: Long,
    val type: String,
    val title: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(dto: NotificationDto) = NotificationResponse(
            id = dto.id,
            type = dto.type,
            title = dto.title?.ifBlank { "제목 없음" } ?: "제목 없음",
            createdAt = dto.createdAt,
        )
    }
}

fun main() {
    val notifications = listOf(
        NotificationDto(1, "FOLLOW", "새 팔로워가 생겼어요", LocalDateTime.now().minusHours(1), false),
        NotificationDto(2, "COMMENT", null, LocalDateTime.now().minusHours(3), false),
        NotificationDto(3, "COUPON", "", LocalDateTime.now().minusDays(1), false),
        NotificationDto(4, "POST_POPULAR", "인기글 선정", LocalDateTime.now().minusMinutes(30), true),
        NotificationDto(5, "FOLLOW", "팔로워가 또 생겼어요", LocalDateTime.now().minusMinutes(10), false),
    )

    val result: List<NotificationResponse> = notifications
        .asSequence()
        .filterNot(NotificationDto::isRead)
        .filterNot{ it.title.isNullOrBlank() }
        .sortedByDescending { it.createdAt }
        .map(NotificationResponse::from)
        .toList()
}

