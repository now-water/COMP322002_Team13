/**
 *	< knuMovieDB_Phase1_Conceptual_Schema_Design >
 *	Team 13
 *	팀장 : 2016115613_권현수
 *	팀원 : 2016110556_이정열
*/

1. Entity
 (1) MOVIE : 영상물을 의미한다.
 (2) GENRE : 영상물의 장르를 나타낸다.
 (3) EPISODE : 영상물 내에 포함된 에피소드를 의미한다.
 (4) ACTOR : 영상물 내에 출연하는 배우 정보를 의미한다. 
 (5) VERSION : 상영되는 나라에서의 영상물의 정보를 의미한다.
 (6) RATING : 고객이 남긴 영상물에 대한 평가를 나타낸다. 
 (7) ACCOUNT : 'knuMovieDB' 의 사용자를 나타낸다.
 (8) ADMIN : ACCOUNT 의 Weak Entity로, 관리자 계정을 의미한다.
 (9) CUSTOMER : ACCOUNT 의 Weak Entity로, 고객 계정을 의미한다.

2. Attribute
 (1) Attribute of MOVIE
  - Title : 영화의 제목에 대한 정보를 알려준다. | VARCHAR(20)
  - Director : 영화감독이 누구인지 알려준다.   | VARCHAR(10)
  - Runtime : 영화 상영 시간		| VARCHAR(10)
  - Production_year : 제작연도		| INTEGER
  - Viewing_class : 관람등급 	| VARCHAR(10)
  - Country : 상영국가	| VARCHAR(20)

 (2) Attribute of GENRE
  - Category : multivalued 형태의 장르 (ex) 액션/스릴러, 범죄/스릴러, 범죄/액션 ... | VARCHAR(20)
 
 (3) Attribute of EPISODE
  - Season_num : 영화의 에피소드가 포함된 시즌 정보를 나타낸다. | INTEGER
  - Episode_num : 영화의 에피소드 중 몇 번째 에피소드인지 알려준다. | INTEGER
  - Series : 에피소드가 포함된 시리즈의 정보를 나타낸다. | VARCHAR(20)
  - Round : 에피소드 내에 포함된 회차 수를 나타낸다. | VARCHAR(10)
  - Episode_title : 에피소드 제목을 의미한다. | VARCHAR(20)

 (4) Attribute of ACTOR
  - Actor_num : 배우 정보를 구분하기 위한 번호를 의미한다. | INTEGER
  - Name : 배우의 이름을 의미한다. | VARCHAR(20)
  - Birth_date : 배우의 생년월일을 의미한다. | VARCHAR(20)
  - Age : 배우의 나이를 의미한다. | INTEGER
  - Character : 배우가 맡은 극 중 캐릭터를 의미한다.
  - C_name : 배우가 맡은 극 중 캐릭터의 의름을 의미한다. | VARCHAR(20)
  - C_role : 배우가 맡은 극 중 캐릭터의 역할을 의미한다. | VARCHAR(20)
  - C_introduction : 배우가 맡은 극 중 캐릭터의 인물 소개를 의미한다. | VARCHAR(100)

 (5) Attribute of VERSION
  - Title_ID : 버전 정보를 구분하기 위한 번호를 의미한다. | VARCHAR(10)
  - Region : 해당 버전이 배포된 국가를 의미한다. | VARCHAR(10)
  - Language : 해당 버전에서 사용되는 언어를 의미한다. | VARCHAR(20)
  - Sub_title : 해당 버전에 각색된 영화의 부제목을 의미한다. | VARCHAR(10)
  - Title : 해당 버전에 각색된 영화의 제목을 의미한다. | VARCHAR(20)

 (6) Attribute of RATING
  - Rating_num : 평가 정보를 구분하기 위한 번호를 의미한다. | INTEGER
  - Expert_rate : 영화 전문가, 평론가의 평가를 나타낸다. | INTEGER
  - Common_rate : 일반인 평가를 나타낸다. | INTEGER
  - Count_of_vote : 총 등록된 평가의 수를 의미한다. | INTEGER

 (7) Attribute of ACCOUNT
  - ID : 계정 아이디를 의미한다. | VARCHAR(10)
  - Password : 계정 비밀번호를 의미한다. | VARCHAR(10)
  - Age : 계정 소유자의 나이를 의미한다. | INTEGER
  - Birth_date : 계정 소유자의 생년월일을 의미한다. | INTEGER
  - Name : 계정 소유자의 이름을 의미한다. | VARCHAR(10)
  - Gender : 계정 소유자의 성별을 의미한다. | VARCHAR(5)
  - Phone_num : 계정 소유자의 휴대전화 번호를 의미한다. | VARCHAR(20)
  - Job : 계정 소유자의 직업을 의미한다. | VARCHAR(10)

 (8) Attribute of ADMIN
  - Admin_id : 관리자 계정을 구분하기 위한 번호를 의미한다. | INTEGER
  - Count_of_register : 관리자 계정이 게시한 영상물의 개수를 의미한다. | INTEGER

 (9) Attribute of CUSTOMER
  - Customer_id : 고객 계정을 구분하기 위한 번호를 의미한다. | INTEGER
  - Membership : 고객 계정의 멤버쉽 가입 정보를 의미하는 Composite attribute 이다.
  - M_type : 멤버쉽 유형을 의미한다. | VARCHAR(15)
  - M_start_date : 멤버쉽 가입 시작일을 나타낸다. | INTEGER
  - M_end_date : 멤버쉽 유효기간 종료일을 나타낸다. | INTEGER

3. Relationship
 (1) CONTAIN : MOVIE entity와 VERSION entity 가 참여하는 관계 유형으로, 하나의 MOVIE는 여러 개의 VERSION을 가질 수 있으며, 여러 개의 VERSION은 하나의 MOVIE에 포함되어 1:N 관계가 보장된다.

 (2) CLASSIFY : MOVIE entity와 GENRE entity 가 참여하는 관계 유형으로, 하나의 MOVIE는 여러 개의 GENRE 를 가질 수 있으며, 여러 GENRE 는 하나의 MOVIE에 특정될 수 있다. 따라서 1:N관계가 보장된다.

 (3) CONSIST : MOVIE entity와 EPISODE entity 가 참여하는 관계 유형으로, 하나의 MOVIE는 여러 EPISODE를 구성할 수 있으며, 여러 EPISODE는 하나의 MOVIE 와 관계를 구성할 수 있다. 따라서 1:N관계를 가진다.

 (4) APPEAR : MOVIE entity와 ACTOR entity가 참여하는 관계 유형으로, 하나의 MOVIE는 여러 ACTOR를 출연시키며, 다른 하나의 MOVIE도 여러 명의 ACTOR를 출연시키므로 1:N 관계가 항상 보장된다.
 
 (5) IN_REG_LIST : MOVIE entity와 ADMIN entity가 참여하는 관계 유형으로, 하나의 ADMIN 계정은 한개의 '게시한 영상물 리스트'를 가질 수 있다. '게시한 영상물 리스트'에는 ADMIN 계정이 MOVIE를 게시한 내역이 저장된다. 또한 여러 개의 MOVIE 를 게시할 수 있다. 그리고 다양한 MOVIE들이 각각의 ADMIN 계정의 '게시한 영상물 리스트'에 저장될 수 있다.

 (6) IN_RATED_LIST : MOVIE entity와 CUSTOMER entity가 참여하는 관계 유형으로, 하나의 CUSTOMER 계정은 한 개의 '평가한 영상물 리스트'를 가질 수 있다. '평가한 영상물 리스트'는 CUSTOMER계정이 평가를 완료한 MOVIE 들이 저장된다. 다양한 MOVIE들이 각각의 CUSTOMER 계정의 '평가한 영상물 리스트'에 저장될 수 있다.

 (7) IN_WISH_LIST : MOVIE entity와 CUSTOMER entity가 참여하는 관계 유형으로, 하나의 CUSTOMER 계정은 한 개의 '찜 목록' 을 가질 수 있다. '찜 목록'은 CUSTOMER 계정이 보고 싶은 영상물을 저장해두고 확인할 수 있는 리스트이다. '찜 목록'은 CUSTOMER 계정당 한 개를 가질 수 있다. '찜 목록' 은 여러 개의 MOVIE를 저장할 수 있으며, 다양한 MOVIE들이 각각의 CUSTOMER 계정의 '찜 목록'에 저장될 수 있다.

 (8) HAS : MOVIE entity와 RATING entity가 참여하는 관계 유형으로, 하나의 MOVIE는 여러 개의 RATING을 가질 수 있다. 다른 하나의 MOVIE에도 여러 개의 RATING을 포함할 수 있어서 1:N관계가 보장된다.

 (9) PREVILEGED : ACCOUNT entity와 ADMIN entity가 참여하는 관계 유형으로, ACCOUNT는 여러 개의 ADMIN 계정을 가질 수 있고, 여러 개의 ADMIN 계정은 관리자 권한을 가질 수 있다. 따라서 N:M 관계가 보장된다.

 (10) NON_PREVILEGED : ACCOUNT entity와 CUSTOMER entity가 참여하는 관계 유형으로, ACCOUNT는 여러 개의 CUSTOMER 계정을 가질 수 있고, 여러 개의 CUSTOMER 계정은 고객 권한을 가질 수 있다. 따라서 N:M 관계가 보장된다.
