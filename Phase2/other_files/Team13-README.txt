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
	- GENRE Table 생성
	- ACCOUNT Table 생성
	- Movie Table 생성
	- Rating Table 생성
	- Actor Table 생성
	- Version Table 생성

(2) Data population
	- GENRE Table 삽입
	- ACCOUNT Table 삽입
	- Movie Table 삽입
	- Rating Table 삽입
	- Actor Table 삽입
	- Version Table 삽입

