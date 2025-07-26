package practice.q41

/**
 * 주제: 대용량 콘텐츠 목록 + 정렬 + 필터
 * 1️⃣ 상황 설명 (비즈니스 요구)
 * 동네 커뮤니티 서비스에서 게시글 목록 조회 API가 있다.
 * 사용자는 다음 조건으로 게시글을 본다.
 * 특정 region_id (동네)
 * 삭제되지 않은 글만
 * 특정 카테고리 (선택)
 * 최신순 정렬
 * 스크롤 기반 무한 조회
 * 하루 중 가장 트래픽이 몰리는 API
 *
 * 2️⃣ 테이블 구조
 * CREATE TABLE post (
 *     id BIGINT NOT NULL AUTO_INCREMENT,
 *     region_id BIGINT NOT NULL,
 *     category VARCHAR(20) NOT NULL,     -- QUESTION / REVIEW / FREE
 *     title VARCHAR(200) NOT NULL,
 *     content TEXT NOT NULL,
 *     like_count INT NOT NULL,
 *     comment_count INT NOT NULL,
 *     is_deleted TINYINT(1) NOT NULL,
 *     created_at DATETIME NOT NULL,
 *     PRIMARY KEY (id)
 * );
 *
 * 3️⃣ 기존 인덱스 현황 (⚠️ 문제 있음)
 * PRIMARY KEY (id)
 * INDEX idx_region (region_id)
 * INDEX idx_created_at (created_at)
 *
 * 4️⃣ 데이터 규모 힌트
 * 전체 post row 수: 약 3천만
 * 특정 region_id 당:
 * 수십만 row
 *
 * 카테고리 분포:
 * QUESTION / REVIEW / FREE 거의 비슷
 * 삭제글(is_deleted = 1)은 전체의 약 10%
 *
 * 조회 API:
 * 초당 수백 ~ 천 단위 QPS
 * 앱 메인 화면에서 항상 호출됨
 *
 * 5️⃣ 조회 요구사항 (중요)
 * region_id = ?
 * is_deleted = 0
 * category IN ('QUESTION', 'REVIEW') ← 가끔 조건 있음
 *
 * 최신 글부터
 * 한 번에 30개
 * OFFSET ❌
 * filesort 발생하면 안 됨
 *
 * 6️⃣ 원하는 결과
 * 운영 환경에서 안전한 조회 SQL
 * 인덱스 설계 (복합 인덱스)
 * 트래픽 증가 시에도 성능이 급격히 무너지지 않는 구조
 *
 * val whereCondition = BooleanBuilder()
 *     .and(post.regionId.eq(regionId))
 *     .and(post.isDeleted.eq(false))
 *
 * if (categories.isNotEmpty()) {
 *     whereCondition.and(post.category.`in`(categories))
 * }
 *
 * if (cursorCreatedAt != null) {
 *     whereCondition.and(post.createdAt.lt(cursorCreatedAt))
 * }
 *
 * val result = queryFactory
 *     .selectFrom(post)
 *     .where(whereCondition)
 *     .orderBy(post.createdAt.desc())
 *     .limit(30)
 *     .fetch()
 *
 * SELECT *
 * FROM post
 * WHERE region_id = :regionId
 *   AND is_deleted = 0
 *   AND category IN ('QUESTION', 'REVIEW')
 *   AND created_at < :cursorCreatedAt
 * ORDER BY created_at DESC
 * LIMIT 30;
 *
 * create index idx_region_deleted_category_created
 * in post(region_id, is_deleted, category, created_at DESC)
 */
