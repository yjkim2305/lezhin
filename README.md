# 작품 관리 API

---
## 프로젝트 개요
사용자의 작품 조회 및 구매 데이터를 관리하는 **API 서비스** 입니다.\
사용자는 회원가입 및 로그인 후 **작품 조회 이력 및 구매 내역을 추적**할 수 있습니다.\
또한, 사용자가 많이 조회한 작품과 많이 구매한 작품 **상위 10개**를 조회하는 기능을 제공합니다.\
특정 작품 삭제 시, 관련된 모든 조회 데이터를 **일괄 삭제**할 수 있습니다.

---
## 기술 스택
* Java 17
* Spring Boot 3.4.3
* JPA
* QueryDSL
* H2 Embedded In-memory
* Gradle
* JWT (Json Web Token)
* Junit5
* Mockito

---
## 기능 목록
1. 사용자 관리
    * **회원가입** 
        * 중복된 사용자 이메일은 가입할 수 없음
    * **로그인**
        * 사용자는 이메일과 비밀번호로 로그인 가능
        * 로그인 성공 시 JWT(Access Token 과 Refresh Token) 발급
        * JWT를 이용한 인증 및 권한 검증
    * **Access Token 갱신**
        * 쿠키에 저장된 Refresh Token으로 Access Token 갱신
2. 작품 관리
    * **작품 조회**
        * 사용자가 작품을 조회하고 관련 정보를 확인
        * 작품별로 언제, 어떤 사용자가 조회했는지 이력 기록
    * **작품 조회 이력 관리**
        * 작품별로 언제, 어떤 사용자가 조회했는지 이력 조회
    * **인기 작품 조회**
        * 사용자가 가장 많이 조회된 작품 상위 10개 조회
    * **작품 구매**
        * 사용자가 작품을 구매
    * **구매 인기 작품 조회**
       * 사용자가 가장 많이 구매한 작품 상위 10개 조회
    * **작품 및 조회 이력 삭제**
       * 특정 작품 삭제 시 관련된 조회 이력 삭제
---
## API 정의
1. 회원가입
   * Endpoint: `POST /api/v1/auth/signup`
   * Description: 새로운 회원을 등록합니다.
   * Request Body:
        ```
        {
            "memberEmail": "mbmer@example.com",  //필수
            "password": "password123",           //필수
            "memberName": "이름",        
            "birthDate": "20250101"              //필수(YYYYMMDD형식)
        }

        ```
   * Response:
     * 200 OK: 회원가입 성공
     * 400 Bad Request: 유효성 검사 실패(이메일 중복, 필수 값 누락)

2. 로그인
   * Endpoint: `POST /api/v1/auth/login`
   * Description: 사용자 이메일과 비밀번호로 로그인
   * Request Body:
        ```
        {
            "memberEmail": "mbmer@example.com",  //필수
            "password": "password123"            //필수 
        }

        ```
   * Response:
      * 200 OK: 회원가입 성공
     ```
        {
            "accessToken": "jwt-access-token",  
            "refreshToken": "jwt-refresh-token"         
        }

        ```
      * 401 Unauthorized: 로그인 실패(이메일 또는 비밀번호 불일치)

3. 작품 등록
   * Endpoint: `POST /api/v1/content`
   * Description: 새로운 작품을 등록합니다.
   * Request Headers: Authorization: Bearer {accessToken} (필수)
   * Request Body:
        ```
        {
            "title": "작품 제목",          //필수
            "author": "작가명",           //필수
            "contentType": "GENERAL",    //필수(GENERAL, ADULT 중 하나)
            "priceType": "FREE",         //필수(FREE, PAID 중 하나)
            "totalEpisodes": 10          //필수(1 이상)  
        }
        ```
   * Response:
      * 200 OK: 작품 등록 성공
      * 400 Bad Request: 유효성 검사 실패 (필수 값 누락)

4. 작품 조회
    * Endpoint: `GET /api/v1/content/{contentId}`
    * Description: 새로운 작품을 등록합니다.
    * Request Headers: Authorization: Bearer {accessToken} (필수)
    * Response:
        * 200 OK: 작품 정보 반환
        ```
        {
            "title": "작품 제목",          
            "author": "작가명",          
            "contentType": "GENERAL",   
            "priceType": "FREE",        
            "totalEpisodes": 10          
        }
        ```
        * 403 Forbidden: 사용자가 성인 작품에 접근할 수 없음
        * 404 Not Found: 존재하지 않는 작품

5. 작품 삭제 및 작품에 대한 조회 이력 삭제
    * Endpoint: `DELETE /api/v1/content/{contentId}`
    * Description: 특정 작품을 삭제하며, 해당 작품의 조회 이력도 삭제됩니다.
    * Request Headers: Authorization: Bearer {accessToken} (필수)
    * Path Parameter: contentId(Long): 삭제할 작품 ID(필수)
    * Response:
        * 200 OK: 작품 및 조회 이력 삭제 성공 
        * 404 Not Found: 존재하지 않는 작품

6. 작품별 조회 이력
    * Endpoint: `GET /api/v1/content-history/{contentId}`
    * Description: 특정 작품의 조회 이력을 조회합니다.
    * Request Headers: Authorization: Bearer {accessToken} (필수)
    * Request Parameters: 
      * contentId (Long) : 조회할 작품 ID (필수)
      * page (int) : 페이지 번호 (기본값: 0)
      * size (int) : 한 페이지당 조회할 개수 (기본값: 10) 
    * Response:
        * 200 OK: 작품 및 조회 이력 반환
        ```
        {
            "content": [{
                "memberName": "테스트",
                "memberEmail": "test@kidaristudio.com",
                "viewDate": "2025-02-27T13:50:38.687352"
            }],
            "pageNumber": 0,
            "totalPages": 1,
            "totalElements": 1       
        }
        ```

7. 사용자가 가장 많이 조회한 작품 상위 10개 조회
    * Endpoint: `GET /api/v1/content-history/member/{memberId}/top-viewed`
    * Description: 사용자가 가장 많이 조회한 작품 상위 10개를 조회합니다.
    * Request Headers: Authorization: Bearer {accessToken} (필수)
    * Path Parameter: memberId(Long): 조회할 사용자의 ID(필수) 
    * Response:
        * 200 OK: 가장 많이 조회한 작품 상위 10개 반환
        ```
        "topContents": [{
            "title": "테스트 작품",
            "author": "테스트 작가",
            "viewCount": 1       
        }]
        ```

8. 작품 구매
    * Endpoint: `POST /api/v1/member-content`
    * Description: 특정 작품의 에피소드를 구매합니다.
    * Request Headers: Authorization: Bearer {accessToken} (필수)
    * Request Body:
       ```
       {
           "contentId": 1,              //필수(구매할 작품의 ID)
           "episodeNumber": 5           //필수(구매할 에피소드 번호) 
       }
       ```
    * Response:
        * 200 OK: 가장 많이 조회한 작품 상위 10개 반환
        * 403 Forbidden: 사용자가 성인 인증이 안된 경우, 존재하지 않는 에피소드를 구매하려는 경우
        * 409 Conflict: 사용자가 이미 해당 에피소드를 구매한 경우

9. 사용자가 가장 많이 구매한 작품 상위 10개 조회
    * Endpoint: `GET /api/v1/member-content/member/{memberId}/top-viewed`
    * Description: 사용자가 가장 많이 구매한 작품 상위 10개를 조회합니다.
    * Request Headers: Authorization: Bearer {accessToken} (필수)
    * Path Parameter: memberId(Long): 조회할 사용자의 ID(필수)
    * Response:
        * 200 OK: 가장 많이 조회한 작품 상위 10개 반환
        ```
        "topMemberContents": [{
            "title": "테스트 작품",
            "author": "테스트 작가",
            "contentEpisodeCount": 1       
        }]
        ```

10. 리프레시 토큰으로 액세스 토큰 재발급
    * Endpoint: `POST /api/v1/refresh/reissue`
    * Description: 액세스 토큰이 만료되었을 때, 쿠키에 저장된 리프레시 토큰을 이용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다. 
    * Response:
        * 200 OK: 새로운 액세스 토큰과 리프레시 토큰 재발급
        ```
        {
            "accessToken": "access-token",
            "refreshToken": "refresh-token"       
        }
        ```
      * 400 Bad Request: 리프레시 토큰이 쿠키에 저장되어있지 않을 경우
      * 401 Unauthorized: 리프레시 토큰이 유효하지 않거나 만료되었거나, 데이터베이스에 저장되지 않은 경우

---
## API 실행 방법
* 사전준비
   * IntelliJ에서 프로젝트 실행 후 HTTP Client로 API를 테스트할 수 있습니다.


* 테스트 실행방법
  * IntelliJ에서 `▶ Send Request` 버튼을 클릭


* **회원가입 테스트**
   * `auth.http` 파일을 열고 아래 요청을 실행합니다.
   ```
       ### 회원가입
       POST http://localhost:8080/api/v1/auth/signup
       Content-Type: application/json

       {
           "memberEmail": "test@kidaristudio.com",
           "password": "securePassword123",
           "memberName": "김영준",
           "birthDate": "19910828"
       }
   ``` 
   * 성공 시: HTTP 200 OK 응답이 반환되며 회원가입이 완료됩니다.


* **로그인 테스트**
   * `auth.http` 파일을 열고 아래 요청을 실행합니다.
   ```
       ### 로그인
       POST localhost:8080/api/v1/auth/login
       Content-Type: application/json

       {
           "memberEmail": "test@kidaristudio.com",
           "password": "securePassword123"
       }
       > {%
            client.global.set("authorizationToken", "Bearer " + response.body.data.accessToken);
            client.global.set("refreshToken", response.body.data.refreshToken);
            client.log("Auth Token: " + client.global.get("authorizationToken"));
            client.log("Refresh Token: " + client.global.get("refreshToken"));
       %}
   ```
   * 성공 시: HTTP 200 OK 응답이 반환되며 `accessToken` 및 `refreshToken`이 발급됩니다.
   * 발급된 토큰은 전역변수(authorization, refreshToken)로 자동 저장됩니다.
 

* **작품 등록 테스트**
  * `content.http` 파일을 열고 아래 요청을 실행합니다. 
   ```
       ### 작품 등록
       POST http://localhost:8080/api/v1/content
       Content-Type: application/json
       Authorization: {{ authorizationToken }}

       {
           "title": "테스트 작품",
           "author": "테스트 작가",
           "contentType": "GENERAL",
           "priceType": "FREE",
           "totalEpisodes": 10
       }
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 작품이 등록됩니다.


* **작품 조회 테스트**
    * `content.http` 파일을 열고 아래 요청을 실행합니다. 
   ```
       ### 작품 조회
       GET http://localhost:8080/api/v1/content/1 
       Authorization: {{ authorizationToken }}
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 작품 정보가 반환됩니다.

* **특정 작품 삭제 및 작품 조회 이력 삭제 테스트**
    * `content.http` 파일을 열고 아래 요청을 실행합니다.
   ```
       ### 특정 작품 삭제 및 작품 조회 이력 삭제
       DELETE http://localhost:8080/api/v1/content/1 
       Authorization: {{ authorizationToken }}
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 작품 및 해당 작품의 조회 이력이 삭제됩니다.

* **작품별 조회 이력 확인 테스트**
    * `contentViewHistory.http` 파일을 열고 아래 요청을 실행합니다. 
   ```
       ### 작품 조회 이력 확인
       GET http://localhost:8080/api/v1/content-history/1?page=0&size=10 
       Authorization: {{ authorizationToken }} 
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 작품 조회 이력이 반환됩니다.


* **사용자가 가장 많이 조회한 작품 상위 10개 조회 테스트**
    * `contentViewHistory.http` 파일을 열고 아래 요청을 실행합니다.
   ```
       ### 사용자가 가장 많이 조회한 작품 상위 10개 조회
       GET http://localhost:8080/api/v1/content-history/member/1/top-viewed 
       Authorization: {{ authorizationToken }} 
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 상위 10개 조회 작품 목록이 반환됩니다.

* **작품 구매 테스트**
* `memberContent.http` 파일을 열고 아래 요청을 실행합니다.
   ```
       ### 작품 에피소드 구매
       POST http://localhost:8080/api/v1/member-content
       Content-Type: application/json
       Authorization: {{ authorizationToken }}
  
       {
           "contentId": 1,
           "episodeNumber": 1
       }
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 에피소드가 구매됩니다.

* **사용자가 가장 많이 조회한 작품 상위 10개 조회 테스트**
    * `contentViewHistory.http` 파일을 열고 아래 요청을 실행합니다.
   ```
       ### 사용자가 가장 많이 구매한 작품 상위 10개 조회
       GET http://localhost:8080/api/v1/member-content/member/1/top-viewed 
       Authorization: {{ authorizationToken }} 
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 상위 10개 구매 작품 목록이 반환됩니다.

* **액세스 토큰 재발급 테스트**
    * `refresh.http` 파일을 열고 아래 요청을 실행합니다.
   ```
       ### 리프레시 토큰으로 액세스 토큰 재발급
       POST http://localhost:8080/api/v1/refresh/reissue
       Content-Type: application/json
       Cookie: refresh={{refreshToken}}

       > {%
            client.global.set("authorizationToken", "Bearer " + response.body.data.accessToken);
            client.global.set("refreshToken", response.body.data.refreshToken);
            client.log("Auth Token: " + client.global.get("authorizationToken"));
            client.log("Refresh Token: " + client.global.get("refreshToken"));
       %}
   ```
    * 성공 시: HTTP 200 OK 응답이 반환되며 새로운 액세스 토큰과 리프레시 토큰이 갱신됩니다.
    * 발급된 토큰은 전역 변수(authorizationToken, refreshToken)로 자동 저장됩니다.
---
## 프로젝트 구조 및 아키텍처
이 프로젝트는 **도메인형 레이어드 아키텍처**를 기반으로 설계되었습니다.

### 도메인형 레이어드 아키텍처
도메인형 레이어드 아키텍처는 각 기능별(도메일별)로 계층을 분리하여 설계하는 방식 입니다. \
각 도메인은 독립적인 비즈니스 로직을 가지며, 특정 도메인에 대한 변경이 다른 도메인에 영향을 최소화하도록 설계 됩니다.

### 프로젝트 구조
```
lezhin
├─ common
│  ├─ api
│  │  └─ response
│  ├─ config
│  ├─ converter
│  ├─ domain
│  │  └─ entity
│  ├─ event
│  ├─ exception
│  └─ util
├─ content
│  ├─ api
│  │  ├─ exception
│  │  ├─ request
│  │  └─ response
│  ├─ application
│  │  ├─ dto
│  │  ├─ repository
│  │  └─ service
│  ├─ domain
│  │  └─ enums
│  ├─ facade
│  └─ infrastructure
│     ├─ entity
│     └─ repository
├─ contentViewHistory
│  ├─ api
│  │  └─ response
│  ├─ application
│  │  ├─ dto
│  │  ├─ repository
│  │  └─ service
│  ├─ domain
│  └─ infrastructure
│     ├─ entity
│     └─ repository
├─ member
│  ├─ api
│  │  ├─ exception
│  │  ├─ request
│  │  └─ response
│  ├─ application
│  │  ├─ dto
│  │  ├─ repository
│  │  └─ service
│  ├─ domain
│  │  └─ enums
│  ├─ facade
│  └─ infrastructure
│     ├─ entity
│     └─ repository
├─ memberContent
│  ├─ api
│  │  ├─ exception
│  │  ├─ request
│  │  └─ response
│  ├─ application
│  │  ├─ dto
│  │  ├─ repository
│  │  └─ service
│  ├─ domain
│  ├─ facade
│  └─ infrastructure
│     ├─ entity
│     └─ repository
└─ refresh
   ├─ api
   │  ├─ exception 
   │  └─ response
   ├─ application
   │  ├─ dto
   │  ├─ repository
   │  └─ service
   ├─ domain  
   └─ infrastructure
      ├─ entity
      └─ repository

```
### 아키텍처 설명
#### **1. API Layer (컨트롤러 계층)**
- HTTP 요청을 받아 적절한 서비스를 호출하는 역할.
- RESTful API를 제공.

#### **2. Application Layer (비즈니스 로직 계층)**
- 도메인 로직을 처리하고, 여러 도메인 간의 상호작용을 조율.
- 핵심적인 비즈니스 로직을 구현.

#### **3. Domain Layer (도메인 모델 계층)**
- 도메인 비즈니스 로직 정의
- 주요 도메인 및 Enum을 포함.

#### **4️. Infrastructure Layer (데이터 저장소 및 외부 연동)**
- 데이터베이스 및 외부 API 연동을 담당.

#### **5️. Facade Layer (통합 서비스)**
- 여러 서비스 로직을 조합하여 하나의 API로 제공하는 역할.

---
## ERD
`member` : 사용자 테이블    
`content` : 작품에 대한 테이블(무료, 유료, 성인 일반 작품 구분)   
`content_view_history` : 작품 조회 이력 테이블      
`member_content` : 작품 구매 테이블\
`refresh` : refresh 토큰 테이블    

![Image](https://github.com/user-attachments/assets/eb687617-69a4-4b46-9b09-5351e1e16091)

---
## 고려했던 상황과 해결 방안
1. 작품 조회 시 이력 기록
   * `고려했던 사항`
     * 작품을 조회할 때, 작품 데이터를 반환하는 것뿐만 아니라 사용자의 조회 이력을 기록해야함
     * 비동기 처리 필요
       * 사용자의 작품 조회 기록을 남길 때, 작품 조회가 지연되지 않도록 해야함
     * 트랜잭션 분리 필요
       * 작품 조회는 성공했지만, 조회 이력 저장이 실패하는 경우 조회 자체가 실패해서는 안됨
     * `해결 방법`
       * 이벤트 발행을 통해 조회 이력 저장을 비동기 이벤트로 처리
       * 이벤트 리스너를 이용해 조회 이력을 저장하므로, 저장 과정에서 오류가 발생하더라도 작품 조회 자체는 정상적으로 수행됨
2. 작품 구매 시 동시성 제어
   * `고려했던 사항`
     * 작품 구매할 시 동시에 사용자가 같은 작품의 에피소드를 구매하는 경우 발생할 수 있는 문제
     * 중복 구매 방지
       * 동일 사용자가 같은 작품의 에피소드를 중복 구매하지 않도록 검증이 필요
       * 여러 요청이 동시에 들어오면 중복 체크 후 구매 처리까지 완료되기 전에 다른 요청이 들어와 중복 구매가 발생할 가능성이 있음
   * `해결 방법`
     * 동시성 제어하기 위해 데이터베이스 락의 비관적 락을 사용