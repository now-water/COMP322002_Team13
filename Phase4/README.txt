<제작 환경>
 - IntelliJ IDE환경에서 제작하였으며 node.JS express Server를 구축하고 postgreSQL 드라이버를 통해 postgreSQL과 연동하여 진행.

<실행 및 사용 방법.>
 - 첨부한 Phase4 프로젝트 파일을 열고 npm install 한 뒤  'node main'을 입력 후 Server와 Database가 연동이 되면 브라우저를 열어 localhost:3000 으로 접속한다.
 - 아래는 package.json의 dependencies정보이다.
 "dependencies": {
    "body-parser": "^1.19.0",
    "ejs": "^3.1.5",
    "express": "^4.17.1",
    "express-session": "^1.17.1",
    "pg": "^8.5.1",
    "pg-promise": "^10.8.1",
    "supervisor": "^0.12.0"
  } 

postgreSQL 정보 - Phase4 프로젝트 파일의 config/db.js의 client객체에 해당 내용과 동일한 지 확인한다.
 - user : postgres,
 - password : kwon0879,
 - database : phase3,
 - schema : knuMovie
 - host : localhost
 - port : 5432

postgreSQL의 DB는 phase2당시 제출한 DB정보와 동일하다.

 - 서버는 localhost:3000

<기능 설명>
	첫 localhost:3000 접속 시 로그인 화면
		- username, password입력 후 signIn 버튼 클릭 시 username과 password정보를 통해 DB에 질의 후 로그인 여부를 판별
		- signUp버튼 클릭 시 회원가입 폼이 나타나게 되며 회원정보를 입력 후 signUp버튼을 누르게 되면 디비에 회원정보가 저장된다.

	-> 사용자 로그인 (로그인 이후에는 아이디와 비밀번호가 브라우저 세션 정보에 남아있게 되어 추가적인 로그인을 할 필요가 없게 한다. 로그인 페이지 접속 시도시 index 페이지로 리다이렉트됨)
		- 관리자 로그인 시 관리자 기능[영화 등록 및 수정]이 왼쪽 사이드바에 나타나지만, 일반 유저 로그인 시 관리자 기능이 보이지 않는다. 
		- 우측 상단, 아이디 정보에 관한 메뉴를 클릭하면 [내 정보, 내가 평가한 영화 항목, 로그아웃 기능] 이 있고, 좌측 Movies와 Search탭에는 영화 list와 검색 기능을 제공한다.
			- 내 정보 : 로그인했던 정보를 통해 디비에 질의하여 내 계정 정보를 가져온다. 내 정보 페이지 안에는 내 정보수정, 회원 탈퇴 기능 또한 있으며
				정보 수정은 내 정보 페이지에 *로 표현된 수정불가 항목을 제외한 정보는 직접 수정이 가능하다. 수정 후 정보 수정 버튼을 누르면 수정 정보가 반영된다.
				이는 아이디를 제외한 비밀번호, 회원정보 등의 항목에 적용된다.
				회원 탈퇴는 탈퇴할 아이디 정보를 한번 더 입력하여 확인하고 입력된 아이디가 현재 로그인된 아이디와 같은경우 DB에서 해당 회원 정보를 삭제한다.

			- 평가 항목 : 내가 평가한 영화 항목들을 DB에 질의하여 테이블을 통해 보여준다. 테이블 페이지에서는 페이지 당 표시할 record 갯수 설정과 영상물 정보 검색 기능을 제공한다.

			- 로그아웃 : 아이디, 비밀번호 등의 세션정보를 모두 반환한 뒤 로그인 화면으로 리다이렉트된다.

	->관리자 로그인 : 관리자 로그인 시 왼쪽 탭에 영화 등록과 수정을 위한 register와 modify탭이 추가된다.
			-Register : 등록할 영화정보를 입력한 후 영화 등록버튼을 누르면 DB에 영화정보가 삽입된다. 등록한 영화는 DB에 즉시 반영되어 movies 탭이나 search등의 탭에서 검색 시 나타나게 된다.

			-Modify : 수정할 영화제목을 입력하여 로그인 된 관리자가 등록한 영화정보인지 확인 후 수정 가능 여부를 판단한다.
				영화 수정 등록 방식 또한, 회원 수정 기능과 같이 정보를 새로 입력 후 수정 등록 버튼을 클릭하면 영화 수정 정보가 DB에 즉시 반영된다.

	사용자, 관리자 공통 기능
			- 컬럼 헤드 클릭 시, 값이 내림차순 or 오름차순으로 정렬된다.
			- Movies : 현재 DB에 저장된 모든 영화정보를 테이블로 나타내주며 마찬가지로 페이지 당 record 갯수 설정과 간단한 검색 기능 또한 제공한다.

			- Search : 모든 영화 정보에 대해 특정 조건 등으로 검색할 수 있으며 영상물의 목록에서 영상물의 정보를 얻을 수 있다.
				 또한 테이블의 Do Rate column의 버튼을 클릭하면 영화의 평점을 직접 입력하여 반영할 수 있으며 내가 평가한 항목은 이후 검색 대상에서 제외된다.
				 평가 내역은 당연히 DB에 update되므로 로그아웃 및 Application 종료 후에도 유지된다.

 	신규 유저 추천 기능 (평가 기록이 하나도 없는 계정)
			- 컬럼 헤드 클릭 시, 값이 내림차순 or 오름차순으로 정렬된다.
			- 평가 기록이 하나라도 있는 계정은 신규 유저가 아니므로, 왼쪽 사이드바에서 Recommend 가 보이지 않는다.
			- 장르별 평점 상위 10개의 영상물을 보여준다. 드랍다운 버튼을 통해서 장르를 선택하면, 해당 장르에 대해 추천 영상물 리스트가 테이블에 나타난다.

<유의 사항>
 관리자 기능 영상 수정 및 등록 시 genre에 대한 내용을 Action은 1, SF는 2, Comedy는 3, Thriller는 4, Romance는 5라는 숫자로 입력해야 함.
 이는 phase2 당시 table schema설계 때 GENRE 테이블과 MOVIE 테이블을 설계할 때 GENRE 테이블의 PK를 sequence를 갖게하는 surrogate key로 구현한 뒤 GENRE테이블의 sequence의 숫자에 따라
 category(Action, SF, Comedy, Thriller, Romance)를 mapping해줬기 때문이다.
  
 phase4 프로젝트의 routes/routes.js의 db객체 (const db = pgp("postgres://postgres:kwon0879@localhost:5432/phase3");)에 password와 database에 대한 정보는 postgreSQL정보를 참조한다.

<추가 과제 수행 여부>
DML(INSERT, UPDATE, DELETE) Transaction 기능 적용하였음. 
(상세 내용은 Team13-extra_task1.txt 에 기술하였음)