CREATE TABLE member( -- 회원
    id VARCHAR2(20) PRIMARY KEY, -- 아이디
    pwd VARCHAR2(20) NOT NULL, -- 비밀번호
    mname VARCHAR2(15) NOT NULL, -- 이름
    email VARCHAR2(30) NOT NULL UNIQUE, -- 이메일
    phone VARCHAR2(20) NOT NULL UNIQUE, -- 전화번호
    regdate DATE DEFAULT SYSDATE, -- 가입일
    available NUMBER(2) DEFAULT 1, -- 회원가입탈퇴여부
    authority VARCHAR2(10) DEFAULT 'general' -- 권한
);

INSERT INTO MEMBER VALUES('admin', '1234', '관리자', 'admin@admin.com', '010-1234-5678', '2020/01/01', 1, 'admin');

ALTER TABLE member ADD
    CONSTRAINT CK_AUTH CHECK(authority IN('admin', 'general'));

CREATE TABLE components ( -- 부품정보
    cnum NUMBER(5) PRIMARY KEY, -- 부품번호
    type VARCHAR2(20) NOT NULL, -- 부품종류
    cname VARCHAR2(80) NOT NULL UNIQUE, -- 부품이름
    price NUMBER(10) NOT NULL, -- 가격
    regdate DATE DEFAULT SYSDATE, -- 부품입고일
    cnt NUMBER(5) NOT NULL -- 재고
);

CREATE TABLE shippinginfo ( -- 배송정보
    snum NUMBER(5) PRIMARY KEY, -- 배송번호
    id VARCHAR2(20) REFERENCES member(id), -- 주문아이디
    cname VARCHAR2(80) REFERENCES components(cname), --부품이름
    sname VARCHAR2(15) NOT NULL, -- 수신자이름
    address VARCHAR2(30) NOT NULL, -- 주소
    startdate DATE, -- 배송시작일
    enddate DATE, -- 배송도착일
    status VARCHAR2(15) -- 배송상태
);

CREATE TABLE payment ( -- 결재정보
    pnum NUMBER(5) PRIMARY KEY, -- 결재정보번호
    id VARCHAR2(20) REFERENCES member(id), -- 회원아이디
    cname VARCHAR2(80) REFERENCES components(cname), -- 결재한상품
    cnt NUMBER(5) NOT NULL, -- 수량
    amount NUMBER(10) NOT NULL, -- 결재금액
    means VARCHAR2(10) NOT NULL, -- 결재수단
    status VARCHAR2(15)
);

CREATE TABLE board(
    bnum NUMBER(5) PRIMARY KEY, -- 게시물번호
    id VARCHAR2(20) REFERENCES member(id), -- 작성자아이디
    title VARCHAR2(20) NOT NULL, -- 제목
    regdate DATE DEFAULT SYSDATE, -- 등록일
    content VARCHAR2(100) -- 내용
);

CREATE SEQUENCE SEQ_components;
CREATE SEQUENCE SEQ_shippinginfo;
CREATE SEQUENCE SEQ_payment;
CREATE SEQUENCE SEQ_BOARD;
CREATE SEQUENCE SEQ_warehouse;