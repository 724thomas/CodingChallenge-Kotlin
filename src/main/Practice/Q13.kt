package practice.q13

import java.time.LocalDate

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
 * 채널 엔티티 (Channel) - 간단한 버전
 * JPA 애노테이션은 실제로는 javax.persistence나 jakarta.persistence에서 import해야 합니다
 * 여기서는 연습용으로 간단하게 만듭니다
 */
data class Channel(
    var handle: String,
    var name: String,
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
 * ChannelStatus ↔ String 변환 유틸리티
 */
object ChannelStatusConverter {

    fun toDatabaseColumn(attribute: ChannelStatus?): String? = when (attribute) {
        null -> null
        ChannelStatus.Active -> "ACTIVE"
        is ChannelStatus.Suspended -> "SUSPENDED:${attribute.reason}"
        is ChannelStatus.Closed -> "CLOSED:${attribute.closedDate}"
    }

    fun toEntityAttribute(dbData: String?): ChannelStatus? {
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

fun main() {
    println("=== Q13 실행 예제 ===")
    
    val channel1 = Channel("tech-news", "기술 뉴스", ChannelStatus.Active)
    println("채널1: ${channel1.name} - ${channel1.status.describe()}")
    
    val channel2 = Channel("gaming", "게임 채널", ChannelStatus.Suspended("정책 위반"))
    println("채널2: ${channel2.name} - ${channel2.status.describe()}")
    
    val channel3 = Channel("old-channel", "옛날 채널", ChannelStatus.Closed(LocalDate.of(2023, 12, 31)))
    println("채널3: ${channel3.name} - ${channel3.status.describe()}")
    
    // 변환 테스트
    val dbValue = ChannelStatusConverter.toDatabaseColumn(channel2.status)
    println("\nDB 저장 값: $dbValue")
    
    val restored = ChannelStatusConverter.toEntityAttribute(dbValue)
    println("복원된 상태: ${restored?.describe()}")
    
    println("\n=== 실행 완료 ===")
}