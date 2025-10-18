package Leetcode.kotlin


/*
🧩 문제 13 — 채널(Channel)의 운영 상태를 sealed class로 모델링하시오
📜 시나리오

운영 중인 채널은 여러 “운영 상태”를 가질 수 있습니다 👇
Active — 정상 운영 중
Suspended — 일시 정지 (사유 필요)
Closed — 영구 종료됨 (종료일 필요)
현재 Channel 엔티티는 아래와 같이 정의되어 있습니다 👇

@Entity
@Table(name = "channel")
class Channel(
    // ...
    @Enumerated(EnumType.STRING)
    var type: ChannelType,
    var handle: String,
    var name: String,
)

이제 여기에 **운영 상태 필드(status)**를 sealed class 기반으로 리팩터링하세요.
 */

/**
 * 채널 엔티티 (Channel)
 * - type: ChannelType (enum)
 * - status: ChannelStatus (sealed class + converter)
 */
@Entity
@Table(name = "channel")
@Comment("채널 엔티티")
class Channel(
    @Column(nullable = false, unique = true)
    @Comment("채널 핸들")
    var handle: String,

    @Column(nullable = false)
    @Comment("채널 이름")
    var name: String,

    @Convert(converter = ChannelStatusConverter::class)
    @Comment("채널 운영 상태")
    var status: ChannelStatus = ChannelStatus.Active
)

/**
 * sealed class 기반 채널 상태 (ChannelStatus)
 * - ACTIVE / SUSPENDED(reason) / CLOSED(closedDate)
 */
sealed class ChannelStatus {
    data object Active : ChannelStatus()
    data class Suspended(val reason: String) : ChannelStatus()
    data class Closed(val closedDate: LocalDate) : ChannelStatus()
}

/**
 * ChannelStatus ↔ String 변환기
 * JPA에서 DB에는 문자열로 저장
 */
@Converter(autoApply = true)
class ChannelStatusConverter : AttributeConverter<ChannelStatus, String> {

    override fun convertToDatabaseColumn(attribute: ChannelStatus?): String? = when (attribute) {
        null -> null
        ChannelStatus.Active -> "ACTIVE"
        is ChannelStatus.Suspended -> "SUSPENDED:${attribute.reason}"
        is ChannelStatus.Closed -> "CLOSED:${attribute.closedDate}"
    }

    override fun convertToEntityAttribute(dbData: String?): ChannelStatus? {
        if (dbData == null) return null
        return when {
            dbData == "ACTIVE" -> ChannelStatus.Active
            dbData.startsWith("SUSPENDED:") ->
                ChannelStatus.Suspended(dbData.removePrefix("SUSPENDED:"))
            dbData.startsWith("CLOSED:") ->
                ChannelStatus.Closed(LocalDate.parse(dbData.removePrefix("CLOSED:")))
            else -> throw IllegalArgumentException("Unknown ChannelStatus format: $dbData")
        }
    }
}

/**
 * 상태를 사람이 읽기 쉬운 문장으로 변환
 */
fun ChannelStatus.describe(): String = when (this) {
    ChannelStatus.Active -> "운영 중"
    is ChannelStatus.Suspended -> "일시 정지 (사유: $reason)"
    is ChannelStatus.Closed -> "종료됨 (종료일: $closedDate)"
}