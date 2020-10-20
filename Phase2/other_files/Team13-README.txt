[개발환경]
OS : Linux 18.04.5 LTS
Version : PostgreSQL 11
Used GUI Toolkit : pgAdmin4

[계층구초]
Servers -----(서버)
	postgres -----(관리자 계정 이름)
		phase2 -----(데이터베이스 이름)
			knuMovie -----(스키마 이름)
				ACCOUNT ----(테이블 이름)
				ACTOR
				EPISODE
				GENRE
				MOVIE
				RATING
				VERSION
			

[실행 순서]
(1) Table 생성
	- CREATE GENRE Table
	- CREATE ACCOUNT Table
	- CREATE Movie Table
	- CREATE Rating Table
	- CREATE Actor Table
	- CREATE Version Table


(2) Data population
	- INSERT GENRE Table
	- INSERT ACCOUNT Table
	- INSERT Movie Table
	- INSERT Rating Table
	- INSERT Actor Table
	- INSERT Version Table

