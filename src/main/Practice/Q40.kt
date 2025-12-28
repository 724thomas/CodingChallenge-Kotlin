package practice.q40

/**
 * 1️⃣ 현재 상황 (비즈니스 요구)
 *
 * 특정 채널(channel) 에 새로운 글이 올라왔다.
 * 이 채널을 팔로우하고 있는 사용자 중, 아래 조건을 만족하는 사용자에게
 * 알림을 보내려고 한다.
 *
 * 발송 대상 조건
 * 해당 채널을 팔로우 중
 *
 * 사용자 상태:
 * user.status = 'ACTIVATION'
 * user.data_status = 'USED'
 * 최근 30일 이내 로그인한 사용자
 * 이미 이 게시글(post_id)에 대한 알림을 받은 사용자는 제외
 * 커서 기반 페이징
 * 한 번에 500명씩
 *
 * 2️⃣ 테이블 구조
 * 📌 user
 * 컬럼명	타입
 * id	BIGINT PK
 * status	VARCHAR(20)
 * data_status	VARCHAR(20)
 *
 * 📌 channel_follow
 * 컬럼명	타입
 * id	BIGINT PK
 * channel_id	BIGINT
 * user_id	BIGINT
 * data_status	VARCHAR(20)
 *
 * 한 유저는 여러 채널 팔로우 가능
 *
 * 📌 user_login_history
 * 컬럼명	타입
 * id	BIGINT PK
 * user_id	BIGINT
 * last_login_at	DATETIME
 * data_status	VARCHAR(20)
 *
 * 한 유저당 여러 row
 *
 * 가장 최근 로그인 기록 기준
 *
 * 📌 notification_send_log
 * 컬럼명	타입
 * id	BIGINT PK
 * user_id	BIGINT
 * post_id	BIGINT
 * notification_type	VARCHAR(30)
 * sent_at	DATETIME
 * 3️⃣ 데이터 규모 힌트 (⚠️ 중요)
 *
 * user: 수백만
 * channel_follow: 수천만
 * user_login_history: 수천만
 * notification_send_log: 수억
 * 👉 JOIN 순서 / 서브쿼리 형태 / EXISTS vs JOIN 선택이 성능을 좌우
 */

/**
 * SELECT u.id
 * FROM user u
 * WHERE
 *      u.status = 'ACTIVATION'
 *      AND u.data_status = 'USED'
 *      AND EXISTS (
 *          SELECT 1
 *          FROM channel_follow cf
 *          WHERE cf.channel_id = 채널Id
 *              AND cf.user_id = u.user_id
 *      ) -- create index channel_id_user_id on channel_follow (channel_id, user_id)
 *      AND EXISTS (
 *          SELECT 1
 *          FROM user_login_history ulh
 *          WHERE ulh.user_id = u.userId
 *              AND last_login_at > NOW() - 30DAYS
 *      ) -- create index user_id_last_login_at on user_login_history (user_id, last_login_at)
 *      AND NOT EXISTS (
 *          SELECT 1
 *          FROM notification_send_log nsl
 *          WHERE nsl.user_id = u.id
 *              AND nsl.post_id = 포스트 ID
 *      ) -- create index user_id_post_id on notification_send_log (user_id, post_id)
 *      AND u.id > 마지막 유저 ID
 * ORDER BY u.id asc
 * LIMIT 500;
 *
 *
 *
 *
 * SELECT u.id
 * FROM user u -- CREATE INDEX (status, data_status, id);
 * WHERE u.status = 'ACTIVATION'
 *   AND u.data_status = 'USED'
 *   AND u.id > :lastUserId
 *
 *   AND EXISTS (
 *       SELECT 1
 *       FROM channel_follow cf -- CREATE INDEX (channel_id, data_status, user_id);
 *       WHERE cf.channel_id = :channelId
 *         AND cf.user_id = u.id
 *         AND cf.data_status = 'USED'
 *   )
 *
 *   AND EXISTS (
 *       SELECT 1
 *       FROM (
 *           SELECT ulh.user_id, MAX(ulh.last_login_at) AS last_login_at
 *           FROM user_login_history ulh -- CREATE INDEX (user_id, last_login_at DESC, data_status);
 *           WHERE ulh.data_status = 'USED'
 *           GROUP BY ulh.user_id
 *       ) t
 *       WHERE t.user_id = u.id
 *         AND t.last_login_at >= NOW() - INTERVAL 30 DAY
 *   )
 *
 *   AND NOT EXISTS (
 *       SELECT 1
 *       FROM notification_send_log nsl -- CREATE INDEX (user_id, post_id, notification_type);
 *       WHERE nsl.user_id = u.id
 *         AND nsl.post_id = :postId
 *         AND nsl.notification_type = 'POST'
 *   )
 * ORDER BY u.id ASC
 * LIMIT 500;
 *
 *
 * fun findActiveFollowersForPostNotification(
 *     channelId: Long,
 *     postId: Long,
 *     lastUserId: Long?,
 *     size: Long,
 * ): List<Long> {
 *
 *     val u = QUser.user
 *     val cf = QChannelFollow.channelFollow
 *     val ulh = QUserLoginHistory.userLoginHistory
 *     val nsl = QNotificationSendLog.notificationSendLog
 *
 *     val whereCondition = BooleanBuilder()
 *         .and(u.status.eq(UserStatus.ACTIVATION))
 *         .and(u.dataStatus.eq(DataStatus.USED))
 *
 *     if (lastUserId != null) {
 *         whereCondition.and(u.id.gt(lastUserId))
 *     }
 *
 *     return queryFactory
 *         .select(u.id)
 *         .from(u)
 *         .where(
 *             whereCondition,
 *             JPAExpressions
 *                 .selectOne()
 *                 .from(cf)
 *                 .where(
 *                     cf.channelId.eq(channelId)
 *                         .and(cf.userId.eq(u.id))
 *                         .and(cf.dataStatus.eq(DataStatus.USED))
 *                 )
 *                 .exists(),
 *             JPAExpressions
 *                 .select(ulh.lastLoginAt.max())
 *                 .from(ulh)
 *                 .where(
 *                     ulh.userId.eq(u.id)
 *                         .and(ulh.dataStatus.eq(DataStatus.USED))
 *                 )
 *                 .gt(LocalDateTime.now().minusDays(30)),
 *             JPAExpressions
 *                 .selectOne()
 *                 .from(nsl)
 *                 .where(
 *                     nsl.userId.eq(u.id)
 *                         .and(nsl.postId.eq(postId))
 *                         .and(nsl.notificationType.eq(NotificationType.POST))
 *                 )
 *                 .notExists()
 *         )
 *         .orderBy(u.id.asc())
 *         .limit(size)
 *         .fetch()
 * }
 *
 */
