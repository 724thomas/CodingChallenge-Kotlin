package practice.q38

/**
 * 1번 연습 문제 – 비활성 사용자 조회 쿼리
 * 1️⃣ 현재 상황 (비즈니스 요구)
 * 최근 30일 동안 로그인하지 않은 활성 사용자 중에서
 * 마케팅 푸시 발송 대상이 되는 사용자 ID 목록을 조회하려고 한다.
 * 배치 작업에서 페이지 단위로 반복 조회하며 처리할 예정이다.
 * 👉 조건을 정확히 만족하면서도
 * 👉 수백만 row 환경에서 안전한 SQL을 작성하는 것이 목표다.
 *
 * 2️⃣ 테이블 구조
 * user
 * 컬럼명	타입	설명
 * id	BIGINT (PK)	사용자 ID
 * status	VARCHAR	ACTIVATION, DEACTIVATION
 * data_status	VARCHAR	USED, DELETED
 * created_at	DATETIME	가입일
 *
 * 인덱스
 * PK(id)
 * idx_status_data_status (status, data_status)
 *
 * user_login_history
 * 컬럼명	타입	설명
 * id	BIGINT (PK)
 * user_id	BIGINT (FK)	user.id
 * last_login_at	DATETIME	마지막 로그인 시각
 * data_status	VARCHAR	USED, DELETED
 *
 * 인덱스
 * idx_user_id (user_id)
 * idx_user_id_login (user_id, last_login_at)
 * idx_last_login (last_login_at)
 *
 * 3️⃣ 원하는 결과
 * 조건을 만족하는 user.id 목록
 * 오름차순 정렬
 * 배치 처리용 페이징
 * user.id > :lastUserId
 * LIMIT 1000
 *
 * 4️⃣ 조건 정리
 * ✅ 포함해야 할 사용자
 * user.status = 'ACTIVATION'
 * user.data_status = 'USED'
 *
 * ❌ 제외해야 할 사용자
 * 최근 30일 이내 로그인 기록이 있는 사용자
 * 로그인 이력이 아예 없는 사용자도 제외
 * 즉,
 * 로그인 기록은 존재해야 하고
 * 그 로그인 기록의 last_login_at < NOW() - INTERVAL 30 DAY
 *
 * 5️⃣ 데이터 규모 힌트 (중요)
 * user: 약 500만 row
 * user_login_history: 약 3천만 row
 * 배치는 하루 1~2회, 동시 API 트래픽 존재
 *
 * 6️⃣ 너가 할 일
 * 👉 효율적인 MySQL SQL을 작성해서 제출
 * 서브쿼리 / JOIN / EXISTS 무엇을 쓰든 자유
 * 단, 왜 이 방식이 안전한지 스스로 납득 가능한 구조여야 함
 * ⚠️ 아직 정답은 말하지 않는다
 * ⚠️ 네가 SQL을 제출하면, 아래 순서로 실무 기준 피드백을 한다
 */


/**
 * SELECT u.id
 * FROM user u
 * WHERE u.id > :lastUserId
 *   AND u.status = 'ACTIVATION'
 *   AND u.data_status = 'USED'
 *   AND EXISTS (
 *       SELECT 1
 *       FROM user_login_history ulh
 *       WHERE ulh.user_id = u.id
 *   )
 *   AND NOT EXISTS (
 *       SELECT 1
 *       FROM user_login_history ulh
 *       WHERE ulh.user_id = u.id
 *         AND ulh.last_login_at >= NOW() - INTERVAL 30 DAY
 *   )
 * ORDER BY u.id
 * LIMIT 1000;
 *
 */