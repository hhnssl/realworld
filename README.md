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

## API 명세

### 사용자 인증 & 계정

| 기능 | 메서드 | URL | 인증 필요 | 요청 본문 예시 | 응답 필드 예시 |
| --- | --- | --- | --- | --- | --- |
| 회원가입 | POST | `/api/users` | X | `{ "user": { "username": "jake", "email": "jake@jake.jake", "password": "jakejake" } }` | `{ "user": { "email": "...", "token": "...", "username": "..." } }` |
| 로그인 | POST | `/api/users/login` | X | `{ "user": { "email": "...", "password": "..." } }` | `{ "user": { "email": "...", "token": "...", "username": "..." } }` |
| 현재 사용자 정보 가져오기 | GET | `/api/user` | O | - | `{ "user": { "email": "...", "username": "...", "bio": "", "image": null } }` |
| 사용자 정보 수정 | PUT | `/api/user` | O | `{ "user": { "email": "...", "bio": "...", "image": "..." } }` | `{ "user": { ... } }` |

---

### 프로필

| 기능 | 메서드 | URL | 인증 필요 | 설명 |
| --- | --- | --- | --- | --- |
| 프로필 조회 | GET | `/api/profiles/:username` | O 또는 X | 특정 유저 프로필 조회 |
| 팔로우 | POST | `/api/profiles/:username/follow` | O | 유저 팔로우 |
| 언팔로우 | DELETE | `/api/profiles/:username/follow` | O | 유저 언팔로우 |

---

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

---

### 태그

| 기능 | 메서드 | URL | 인증 필요 | 설명 |
| --- | --- | --- | --- | --- |
| 태그 목록 | GET | `/api/tags` | X | 전체 태그 조회 |

---

## 회고

- **Keep (잘한 점, 계속 이어갈 점)**
  - 
- **Problem (아쉬운 점, 문제점)**
  - 
- **Try (다음에 시도해볼 점)**
  -