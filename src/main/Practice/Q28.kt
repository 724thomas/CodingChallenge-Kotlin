package practice.q28

import java.time.LocalDateTime

/**
🧩 Kotlin 실무 문제 ⑥ — “Notification Query Pipeline DSL”
📘 상황

지금은 운영팀이 알림 목록 API에서 다음과 같이 계속 요구사항을 변경하고 있습니다:
“이번엔 읽지 않은 알림만, FOLLOW 타입만 보여주세요.”
“아니, 이번엔 최근 7일 이내 알림만, COUPON은 빼주세요.”
“다음엔 최신순으로 정렬 말고, 오래된 순으로 줘봐요.”

이런 요구가 자주 들어오면, 매번 filter() 체인을 수정하는 대신
 **“알림 쿼리 파이프라인 DSL”**을 만들어두면 변경이 쉬워집니다.
(즉, 비즈니스 로직을 함수 호출이 아니라 구성(DSL) 으로 표현)

📦 주어진 DTO


📋 요구사항

람다와 DSL을 이용해 NotificationQueryPipeline 클래스를 완성하세요.
이 클래스는 다음 기능을 제공합니다 👇
읽지 않은 알림만 필터링 (unreadOnly())
최근 N일 이내만 남기기 (withinDays(days: Long))
특정 타입만 포함 (includeTypes(vararg types: String))
특정 타입 제외 (excludeTypes(vararg types: String))
정렬 순서 선택 (sortByCreated(descending: Boolean = true))
limit 설정 (limit(count: Int))
execute(list: List<NotificationDto>): List<NotificationDto> 로 실행

💡 예시 사용 코드
fun main() {

)

val result = NotificationQueryPipeline()
.unreadOnly()
.withinDays(3)
.excludeTypes("COUPON")
.sortByCreated(descending = true)
.limit(2)
.execute(notifications)

println(result)
}

 */
data class NotificationDto(
    val id: Long,
    val type: String,
    val title: String?,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
)

class NotificationQueryPipeline {

    private val filters = mutableListOf<(NotificationDto) -> Boolean>()
    private var sortDescending: Boolean = true
    private var limitCount: Int? = null
    private var includeTypes: Set<String>? = null
    private var excludeTypes: Set<String>? = null

    fun unreadOnly() = apply {
        filters += { !it.isRead }
    }

    fun withinDays(days: Long) = apply {
        filters += { it.createdAt.isAfter(LocalDateTime.now().minusDays(days)) }
    }

    fun includeTypes(vararg types: String) = apply {
        includeTypes = types.toSet()
        filters += { it.type in includeTypes!! }
    }

    fun excludeTypes(vararg types: String) = apply {
        excludeTypes = types.toSet()
        filters += { it.type !in excludeTypes!! }
    }

    fun sortByCreated(descending: Boolean = true) = apply {
        sortDescending = descending
    }

    fun limit(count: Int) = apply {
        limitCount = count
    }

    fun execute(list: List<NotificationDto>): List<NotificationDto> {
        return list.asSequence()
            .filter { dto -> filters.all { it(dto) } }
            .let { seq ->
                if (sortDescending) seq.sortedByDescending { it.createdAt }
                else seq.sortedBy { it.createdAt }
            }
            .let { seq ->
                limitCount?.let { seq.take(it) } ?: seq
            }
            .toList()
    }
}

fun main() {
    val notifications = listOf(
        NotificationDto(1, "FOLLOW", "새 팔로워", LocalDateTime.now().minusDays(1), false),
        NotificationDto(2, "COUPON", "쿠폰 도착", LocalDateTime.now().minusHours(2), false),
        NotificationDto(3, "COMMENT", "댓글 알림", LocalDateTime.now().minusDays(2), true),
        NotificationDto(4, "FOLLOW", "다른 팔로워", LocalDateTime.now().minusDays(4), false),
        NotificationDto(5, "POST_POPULAR", "인기글 선정", LocalDateTime.now().minusHours(10), false),
        NotificationDto(6, "FOLLOW", "팔로워가 또 생김", LocalDateTime.now().minusMinutes(5), false),
    )

    val result = NotificationQueryPipeline()
        .unreadOnly()
        .withinDays(3)
        .excludeTypes("COUPON")
        .sortByCreated(descending = true)
        .limit(2)
        .execute(notifications)

    println(result)
}
