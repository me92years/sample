# sample
영화 페이지 구현 연습

Spring Boot와 React를 사용하여 간단한 영화 페이지를 구현합니다.

사용한 의존성 목록
JPA, OAuth2-client, security, web, jjwt, querydsl, lombok, devtolls, h2, mariadb, spring jdbc ...

H2 DB를 활용하여 개발에 사용하였으며, MariaDB를 실제 운영 시 사용할 DB로 선택.

entity 구성
  Base: 생성날짜과 수정날짜
  Movie: 제목, 포스터 Url
  MovieDetail: 개요, 장르
  MovieReview: 리뷰내용, 작성자
  User: 구글 로그인을 위한 email, picture, nicname, refresh, role
