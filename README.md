# 리얼월드 프로젝트

## 프로젝트 개요

- Medium.com의 핵심 기능을 클론하는 프로젝트로 JWT 기반의 인증 시스템을 갖춘 REST API 서버를 Spring Boot로 구현하였습니다. 사용자들은 글을 작성하고, 다른 사용자를 팔로우하고, 태그를 통해 글을 분류할 수 있습니다.
- **개발 목표**:
    - DB 도메인 모델링 경험
    - Spring Boot, Spring Security, JPA 등 핵심 프레임워크에 대한 이해
    - RESTful API 설계 및 JWT 기반의 stateless 인증 시스템 구현 경험

## 기술 스택

- **언어**: Java 17
- **프레임워크**: Spring Boot, Spring Security, Spring Data JPA
- **데이터베이스**: H2
- **인증**: JWT
- **빌드 도구**: Gradle

## ERD
![gg.drawio.png](https://github.com/user-attachments/assets/c3086d6d-7b33-454c-a923-db4d14afcc6b)

## 객체지향 설계 UML
![image.png](https://github.com/user-attachments/assets/84e5a54b-6295-4073-996a-2f9fbc0549b5)

## API 명세

### 사용자 인증 & 계정

| 기능 | 메서드 | URL | 인증 필요 | 요청 본문 예시 | 응답 필드 예시 |
| --- | --- | --- | --- | --- | --- |
| 회원가입 | POST | `/api/users` | X | `{ "user": { "username": "jake", "email": "jake@jake.jake", "password": "jakejake" } }` | `{ "user": { "email": "...", "token": "...", "username": "..." } }` |
| 로그인 | POST | `/api/users/login` | X | `{ "user": { "email": "...", "password": "..." } }` | `{ "user": { "email": "...", "token": "...", "username": "..." } }` |
| 현재 사용자 정보 가져오기 | GET | `/api/user` | O | - | `{ "user": { "email": "...", "username": "...", "bio": "", "image": null } }` |
| 사용자 정보 수정 | PUT | `/api/user` | O | `{ "user": { "email": "...", "bio": "...", "image": "..." } }` | `{ "user": { ... } }` |



### 프로필

| 기능 | 메서드 | URL | 인증 필요 | 설명 |
| --- | --- | --- | --- | --- |
| 프로필 조회 | GET | `/api/profiles/:username` | O 또는 X | 특정 유저 프로필 조회 |
| 팔로우 | POST | `/api/profiles/:username/follow` | O | 유저 팔로우 |
| 언팔로우 | DELETE | `/api/profiles/:username/follow` | O | 유저 언팔로우 |



### 아티클 (게시글)

| 기능 | 메서드 | URL | 인증 필요 | 설명 |
| --- | --- | --- | --- | --- |
| 아티클 목록 | GET | `/api/articles` | O 또는 X | 전제 아티클 목록 (필터링 가능) |
| 피드 목록 | GET | `/api/articles/feed` | O | 팔로우한 유저의 아티클 목록 |
| 아티클 작성 | POST | `/api/articles` | O | 게시글 작성 |
| 아티클 조회 | GET | `/api/articles/:slug` | O 또는 X | 게시글 상세 보기 |
| 아티클 수정 | PUT | `/api/articles/:slug` | O | 게시글 수정 |
| 아티클 삭제 | DELETE | `/api/articles/:slug` | O | 게시글 삭제 |
| 좋아요 | POST | `/api/articles/:slug/favorite` | O | 게시글 좋아요 |
| 좋아요 취소 | DELETE | `/api/articles/:slug/favorite` | O | 게시글 좋아요 취소 |

**쿼리 파라미터 필터링**

- `tag=reactjs`
- `author=jake`


### 태그

| 기능 | 메서드 | URL | 인증 필요 | 설명 |
| --- | --- | --- | --- | --- |
| 태그 목록 | GET | `/api/tags` | X | 전체 태그 조회 |



### 댓글

| 기능 | 메서드 | URL | 인증 필요 | 설명 |
| --- | --- | --- | --- | --- |
| 댓글 조회 | GET | `/api/articles/:slug/comments` | ✅ 또는 ❌ | 특정 게시글의 댓글 조회 |
| 댓글 작성 | POST | `/api/articles/:slug/comments` | ✅ | 댓글 추가 |
| 댓글 삭제 | DELETE | `/api/articles/:slug/comments/:commentId` | ✅ | 댓글 삭제 |
---

## 회고
### Keep (좋았던 경험)

- API 명세 기반 개발
  - 공식 API 명세를 기준으로 개발을 시작해 목표와 방향성을 명확히 설정했습니다. 그 결과, 기능 구현에 집중할 수 있었고 생산성이 높아졌습니다.

- Spring Security로 인증/인가 구축
  - Spring Security를 활용해 JWT 기반 Stateless 인증을 구현하며 인증/인가 로직을 깊이 이해했습니다. 이를 통해 실무 서비스에서 필요한 보안 역량을 쌓을 수 있었습니다.

- 객체지향적 연관관계 설계
  - 비즈니스 로직을 직접 분석해 User, Article, Comment 엔티티의 연관관계를 설계했습니다. 이 과정에서 @ManyToOne, @OneToMany 등 JPA 연관관계 매핑에 대한 이해를 심화했습니다.

- 체계적인 예외 처리
  - @RestControllerAdvice와 커스텀 예외 클래스를 사용해 상황별 명확한 에러 응답을 제공하는 전역 예외 처리 시스템을 구축했습니다.


### Problem (겪은 문제와 배운 점)

- @JsonRootName과 테스트 복잡도
  - RealWorld 명세에 맞추기 위해 @JsonRootName으로 JSON을 감쌌지만, 통합 테스트에서 ObjectMapper 설정이 추가로 필요해 복잡도가 증가했습니다. 이 경험을 통해 DTO 래퍼 방식과의 장단점을 비교하며 더 나은 설계를 고민할 수 있었습니다.

- N+1 문제와 설계의 혼동
  - 초기에는 JPA의 N+1 문제를 간과했고, 이를 해결하는 과정에서 CQRS 패턴을 잘못 이해해 Comment 모델에서 ID 직접 참조 방식을 시도했습니다. 그러나 이는 CQRS의 핵심을 벗어나고 객체지향적 장점을 잃는 방법이었음을 깨달았습니다. 이 경험을 통해 조회 전략과 설계를 초기 단계에서 명확히 정의하는 것이 중요하다는 교훈을 얻었습니다.

- 프로젝트 구조 및 명명 규칙의 아쉬움
  - **폴더 구조**: 엔티티별로 세분화된 DTO 폴더(articleDtos/request, articleDtos/response 등)를 두었는데, 프로젝트 규모에 비해 과도한 분리였습니다. 다음에는 `dto/request`와 `dto/response`로 단순화하거나, `dto/request/article/NewArticleRequest`처럼 깊이를 통일하는 방식을 시도하고 싶습니다.
  - **파일명**: Entity와 DTO 클래스에 각각 `Entity`, `DTO`를 붙여 파일명이 불필요하게 길어졌습니다. 구분이 어려웠던 초기에는 유용했지만, 현재는 불필요하다고 느껴 개선이 필요합니다.
  - **네이밍 일관성**: 같은 계층 내에서도 매개변수와 메서드 이름이 제각각(entity vs user 등)이라 가독성이 떨어졌습니다. 명명 규칙을 초기에 정하는 것이 중요하다는 점을 배웠습니다.

- 설계에서 놓친 부분
  - **User와 Profile 분리**: User와 Profile을 하나의 엔티티로 두어 보안과 데이터 관리 측면에서 불편함이 있었습니다. Profile 레포지토리를 별도로 두는 방식이 더 적절하다는 점을 깨달았습니다.
  - **타임스탬프 누락**: User 엔티티에 `createdAt`, `updatedAt` 컬럼을 두지 않았습니다. Response 스펙에만 맞추다 보니 필수적인 컬럼을 
    간과한 
    점이 아쉬웠습니다.
  - **어노테이션 의존**: `@CreatedDate` 등 편리한 어노테이션에만 의존했는데, 이를 직접 구현해보며 동작 원리를 깊이 이해할 필요성을 느꼈습니다.

### Try (다음에 시도할 점)

- 테스트 주도 개발(TDD)
  - JUnit5을 사용하여 테스트를 먼저 작성하는 TDD를 도입해 요구사항 이해를 높이고 코드의 안정성과 유지보수성을 확보하는 방법을 배우고 싶습니다.

- DTO 변환 자동화
  - MapStruct를 사용해 반복적인 DTO 변환 로직을 줄이고 가독성을 높이고 싶습니다.

- Docker를 활용한 배포
  - 애플리케이션을 Dockerfile로 컨테이너화하고, docker-compose로 PostgreSQL과 함께 로컬 환경에서 실행해 컨테이너 기술과 배포 역량을 키우고 
    싶습니다.