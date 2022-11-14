# 지우기
임포트 모듈 관리
예외처리
두시간 정도 더개발하고 문서 마무리
신청서 수정 처리하기


# 요구사항 정리
회원 최대 5개 지원서
중복지원서는 가능하지만 최종 지원 완료 지원서는 1개
지원완료 상태는 수정 삭제 불가



# STARTERS 취업부트캠프 2기 중간평가(JAVA)
- 로그인 / 회원가입은 따로 구현 X
- 관리자와 일반 회원
- 회원정보 - ID, 비밀번호, 회원이름, 회원구부느 전화번호
- 수업 생성은 구현 X, 생성되어있는 수업
- 수업은 수업명, 모집기간 수강 기간
- 수업별로 지원서 접수받을 수 있으며
- 지원서 기본값 - 회원이름, 아이디, 전화번호, 지원동기, 추후 진로, 상태값
- 지원서 상태값 - 지원중, 지원완료, 합격, 불합격

## 기능 요구 사항
- 회원은 최대 5개 지원서
  - 수업 별 중복 지원서 존재 가능, 최종 지원 완료 지원서 1개
  - 수업의 모집 기간 내에만 지원서 작성 모집 기간 전 후 불가
- 지원서 - 지원중, 지원완료, 합격, 불합격 총 4가지
  - 지원중일때 임시 저장 상태로 일반회원이 지원완료 전까지 수정 가능
  - 지원완료 상태는 지원서 제출 이후 수정 삭제 불가능
- 모집기간 종료 -> 지원중의 지원서는 불합격
  - 지원서 -> 불합격이면 카운팅 X
  - 모집기간 동일한 수업 3개 이상의 지원서 지원 완료 X
  - 교육기간 동일한 수업 3개 이상의 지원서 지원완료 X

- 관리자는 각 회원별 지원서의 상태 확인 가능
- 지원완료된 지원서의 경우 지원서 내용 및 합격/불합격 상태 변경 가능
- 완료된 지원서는 회원이름으로 검색 가능

## ERD
- 한 수업에 중복지원서가 있을 수 있다해서 다대다 관계로 설정할까 헷갈렸는데
한개의 지원서는 한개의 수업에만 지원할 수 있고 한 수업은 여러개의 지원을 받을 수 있으므로
다대일 관계로 설정
- 지원서 하나는 유저 하나가 대응되므로 일대일 관계로 설정

## 추가 사항
- 패스워드 암호화
- 이메일 중복 X
- 생성자 주입 
- 빌더패턴
- 회원가입 과정이 없으므로 유저, 지원서, 수업 id를 받아오게했습니다.
- 1번 유저는 일반, 2번 유저는 어드민 인터셉터에서 세션 추가
- 지원완료나 합격인경우는 재지원불가

## API 명세서

### Student


### `POST`   /user/application 

** 지원서 작성하기 **

- 주의사항

request

```jsx
{

}
```

response

```jsx
// 성공 여부를 data에 메세지로 반환

{
  "status": 0,
  "message": "string",
  "data": "string",
  "timestamp": 0
}
```

### `PUT`  /student/application

지원서 수정하기 및 제출하기

- 제출하기는 지원서의 status 수정하므로 put에 해당

request

```jsx
{

}
```

response

```jsx
// data에 검색에 성공한 멤버의 정보를 반환
{
  "status": 0,
  "message": "string",
  "data":
    "profileImageURL": "string",
    "email": "string",
    "nickname": "string",
    "statusMessage": "string",
    "invitationLink": "string",
    "refreshToken": "string"
  },
  "timestamp": 0
}
```

### ADMIN

### `GET`  /admin/application

지원서 조회하기

- 지원서 조회

request

```jsx
{

}
```

response

```jsx
// data에 검색에 성공한 멤버의 정보를 반환
{
  "status": 0,
  "message": "string",
  "data":
    "profileImageURL": "string",
    "email": "string",
    "nickname": "string",
    "statusMessage": "string",
    "invitationLink": "string",
    "refreshToken": "string"
  },
  "timestamp": 0
}
```

지원서 상태 변경하기

- 지원완료된 지원서중 합격 / 불합격 변경하기

request

```jsx
{

}
```

response

```jsx
// data에 검색에 성공한 멤버의 정보를 반환
{
  "status": 0,
  "message": "string",
  "data":
    "profileImageURL": "string",
    "email": "string",
    "nickname": "string",
    "statusMessage": "string",
    "invitationLink": "string",
    "refreshToken": "string"
  },
  "timestamp": 0
}
```
