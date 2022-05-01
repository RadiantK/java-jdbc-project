# java-jdbc-project(컴퓨터 부품 판매 사이트)

## 사용한 환경
- JAVA(JDK 11)
- Eclipse IDE
- ORACLE 18c XE
- ER다이어그램(MYSQL사용)

## ORACLE설정
```sql

ALTER SESSION SET “_oracle_script”=true; -- 생성할 사용자명에 C##을 붙이지 않기위해서 사용

CREATE USER temp identified by temp;
GRANT CONNECT, RESOURCE, DBA TO temp;
```
<br/>

테이블 설정은 SQL폴더의 computer.sql 파일로 확인 가능하다.

## ER다이어그램
![jdbc-project-erd](https://user-images.githubusercontent.com/95058915/166146836-c748cde2-4549-4bc0-a186-52c408d5eead.png)

