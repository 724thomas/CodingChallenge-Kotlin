package practice.q35

/**
data class Notification(
val id: Long,
val type: String?,
val isRead: Boolean?,
val createdAt: Long?
)

fun filterNotifications(
notifications: List<Notification>,
allowedTypes: List<String>,
recentSeconds: Long
): List<Notification> {

val result = mutableListOf<Notification>()

for (n in notifications) {
if (n.type != null && allowedTypes.contains(n.type)) {
if (n.isRead != null && !n.isRead) {
if (n.createdAt != null && n.createdAt > System.currentTimeMillis() - recentSeconds * 1000) {
result.add(n)
}
}
}
}

return result
}
1) 위 코드를 Kotlin스럽게 리팩토링하라

filter, mapNotNull, takeIf, let 등 사용 자유
중첩 if 제거
null safe 처리

2) 필터링 로직을 확장 함수로 분리하라
 */

data class Notification(
    val id: Long,
    val type: String?,
    val isRead: Boolean?,
    val createdAt: Long?
)

// 확장 함수: Notification이 조건에 맞는지 판단
fun Notification.isAllowed(
    allowedTypes: List<String>,
    recentSeconds: Long
): Boolean {
    val now = System.currentTimeMillis()
    val threshold = now - recentSeconds * 1000

    return (type in allowedTypes) &&
            (isRead == false) &&
            (createdAt?.let { it > threshold } == true)
}

fun filterNotifications(
    notifications: List<Notification>,
    allowedTypes: List<String>,
    recentSeconds: Long
): List<Notification> {
    return notifications.filter { it.isAllowed(allowedTypes, recentSeconds) }
}
