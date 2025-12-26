package practice.q39

/**
 * 1️⃣ 현재 상황 (비즈니스 요구)
 *
 * 콘솔에서 생성된 알림(Notification) 중
 * 아직 푸시 발송이 되지 않은 알림을 조회해서
 * 배치로 순차 처리하려고 한다.
 *
 * ⚠️ 배치는 중복 발송이 절대 발생하면 안 됨
 * ⚠️ 동시에 API 트래픽도 존재함
 *
 * 2️⃣ 테이블 구조
 * notification
 * 컬럼명	타입	설명
 * id	BIGINT (PK)	알림 ID
 * target_user_id	BIGINT	수신 대상 사용자
 * status	VARCHAR	READY, PROCESSING, SENT
 * category	VARCHAR	MARKETING, SYSTEM, CONTENT
 * created_at	DATETIME	생성 시각
 * data_status	VARCHAR	USED, DELETED
 *
 * 인덱스
 * PK(id)
 * idx_status_created (status, created_at)
 * idx_target_user (target_user_id)
 *
 * push_log
 * 컬럼명	타입	설명
 * id	BIGINT (PK)
 * notification_id	BIGINT (FK)	notification.id
 * result	VARCHAR	SUCCESS, FAIL
 * created_at	DATETIME	발송 시각
 *
 * 인덱스
 * idx_notification_id (notification_id)
 * idx_notification_result (notification_id, result)
 *
 * 3️⃣ 원하는 결과
 * 아직 푸시 발송 이력이 없는 알림 ID
 * 상태는 READY
 * 최근 생성된 것부터 처리
 * 배치용 페이징
 * notification.id > :lastNotificationId
 * LIMIT 500
 *
 * 4️⃣ 조건 정리
 * ✅ 포함
 * notification.status = 'READY'
 * notification.data_status = 'USED'
 *
 * ❌ 제외
 * push_log에 해당 notification_id가 존재하는 경우
 * (SUCCESS / FAIL 관계없이 존재 자체만으로 제외)
 *
 * 5️⃣ 데이터 규모 힌트 (중요)
 * notification: 약 300만 row
 * push_log: 약 1,500만 row
 * 배치는 5초마다 반복 실행
 * 동시에 콘솔/API 알림 생성 트래픽 존재
 *
 * 6️⃣ 너가 할 일
 *
 * 👉 중복 발송 없이 안전한 MySQL SQL 작성
 * JOIN / NOT EXISTS / 서브쿼리 자유
 * 단,
 * 왜 이 방식이 중복에 안전한지
 * 왜 인덱스를 잘 타는지
 * 스스로 설명 가능해야 함
 *
 * val subquery = queryFactory
 *  .select(*)
 *  .from(pushLog p)
 *  .where(n.id.eq(p.notification.id)
 *      .and(
 *
 * SELECT *
 * FROM notification n
 * WHERE n.status = 'READY'
 *      AND NOT EXISTS (
 *      SELECT 1
 *      FROM push_log p
 *      WHERE n.id = p.notification_id)
 *      AND n.id > :lastNotificationId
 *      AND n.data_status = 'USED'
 * ORDER BY created_at
 * LIMIT 500
 *
 *
 * 개선안:
 * SELECT n.id
 * FROM notification n
 * WHERE n.status = 'READY'
 *   AND n.data_status = 'USED'
 *   AND n.id > :lastNotificationId
 *   AND NOT EXISTS (
 *       SELECT 1
 *       FROM push_log p
 *       WHERE p.notification_id = n.id
 *   )
 * ORDER BY n.id
 * LIMIT 500;
 *
 * 실무적:
 * UPDATE notification
 * SET status = 'PROCESSING'
 * WHERE status = 'READY'
 *   AND data_status = 'USED'
 *   AND id > :lastNotificationId
 *   AND NOT EXISTS (
 *       SELECT 1
 *       FROM push_log p
 *       WHERE p.notification_id = notification.id
 *   )
 * ORDER BY id
 * LIMIT 500;
 *
 * fun findReadyNotifications(
 *      lastNotificationId: Long,
 *      size: Long,
 * ): List<Long> {
 *      val subquery = JPAExpression
 *          .selectOne()
 *          .from(pushLog)
 *          .where(pushLog.notificationId.eq(notification.id))
 *          .notExists()
 *
 *      val result = queryFactory
 *          .select(notification.id)
 *          .from(notification)
 *          .where(
 *              notification.status.eq(NotificationStatus.READY),
 *              notification.dataStatus.eq(DataStatus.USED),
 *              notification.id.gt(lastNotificationId),
 *              subquery
 *          )
 *          .orderBy(notification.id.asc())
 *          .limit(size)
 *          .fetch()
 */

