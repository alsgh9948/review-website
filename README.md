# 스프링 및 도커 학습 용 간단 리뷰 게시판
스프링과 도커 학습을 목표로 함

+ 도커를 이용하여 웹 배포
+ 도커를 이용하여 디비서버 구축

## TODO
- [x] JWT Authentication
   + JWT를 이용한 로그인
   + role에 따른 API 접근권한 분리
   + Redis로 refresh, blacklist token 관리
- [ ] Logger
   + request 정보에 대한 logging 추가함
     + request body logging 추가 필요 
   + 정의한 exception logging 추가함
     + 이외의 공통 exception logging 추가 필요
 - [ ] DB Manage
   + Jpa -> Query DSL로 변경
 - [x] DTO
