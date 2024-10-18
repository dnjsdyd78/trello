# Trello 프로젝트
## 프로젝트 한 줄 소개
#####  Trello는 프로젝트 관리를 위한 협업 툴로, 직관적인 칸반 보드(Kanban Board) 방식으로 작업을 관리할 수 있어, 많은 기업에서 애용하는 도구입니다. 이번 프로젝트를 통해 Trello의 백엔드를 개발하며 실무에서 사용하는 여러 기술을 접하고 적용해보는 기회를 가질 것입니다.

## 팀원
| 이름     | 박지민                                                   | 정예지                                                   | 안예환                                                   | 이가연                                                   | 최원용                                                   |
| :---:    | :------------------------------------------------------ | :------------------------------------------------------ | :------------------------------------------------------ | :------------------------------------------------------ | :------------------------------------------------------ |
| 링크     | [링크](https://github.com/user-attachments/assets/8beb058a-eb50-4267-bc6c-b165b1807fd5) | [링크](https://github.com/user-attachments/assets/1c966822-ce50-4537-ae60-624e76b8ca86) | [링크](https://github.com/user-attachments/assets/54191531-efe9-4977-9788-13083edad61b) | [링크](https://github.com/user-attachments/assets/7ecdb9f7-6dad-4320-93a8-20e165148dc8) | [링크](https://github.com/user-attachments/assets/bf5485e9-7711-44d8-bd99-845c4a5e9f0d) |

## 프로젝트 기능

### Login & Signup (로그인 및 회원가입 기능)
* 회원가입 : 회원가입 기능
* 로그인 : 이메일과 비밀번호로 로그인

### Workspace (워크스페이스 기능)
* 워크스페이스 생성: 권한을 가진 유저가 워크스페이스 생성
* 워크스페이스 조회: 권한을 가진 유저가 워크스페이스 조회
* 워크스페이스 수정: 권한을 가진 유저가 워크스페이스 수정
* 워크스페이스 삭제: 권한을 가진 유저가 워크스페이스 삭제
* 워크스페이스 멤버 초대: 권한을 가진 유저가 워크스페이스 멤버 초대

### Board (보드 기능)
* 보드 생성: 권한을 가진 유저가 보드 생성
* 보드 조회: 권한을 가진 유저가 보드 조회
> * 보드 목록 조회
> * 보드 상세 조회 
* 보드 수정: 권한을 가진 유저가 보드 수정
* 보드 삭제: 권한을 가진 유저가 보드 삭제

### List (리스트 기능)
* 리스트 생성: 권한을 가진 유저가 보드 리스트 생성
* 리스트 수정: 권한을 가진 유저가 리스트 수정
* 리스트 삭제: 권한을 가진 유저가 리스트 삭제

### Card (카드 기능)
* 카드 생성: 권한을 가진 유저가 리스트 카드 삭제
* 카드 상세 조회: 모든 유저가 카드 상세 조회
> * 댓글 목록 조회
> * 매니저 목록 조회 
* 카드 수정: 권한을 가진 유저가 카드 수정
* 카드 삭제: 권한을 가진 유저가 카드 삭제

### Manager (담당자 기능)
* 담당자 생성: 권한을 가진 유저가 카드 담당자 등록
* 담당자 삭제: 권한을 가진 유저가 카드 담당자 삭제

### Search (검색 기능)
* 카드 검색: 키워드와 마감날짜 필터를 거쳐 카드 조회

### alert (알림 기능)
* 실시간 알림: 카드 생성, 멤버추가, 댓글 생성 이벤트 발생시 슬렉 API 통해 알림메시지 발송
* 알림 예약: 알림 예약 요청을 받아 예약 시간에 슬렉 API 통해 알림메시지 발송


## 와이어 프레임
![스크린샷 2024-10-18 오전 5 09 24](https://github.com/user-attachments/assets/ec7010c9-149d-459e-a592-cee1ccef2b2d)
![스크린샷 2024-10-18 오전 5 10 55](https://github.com/user-attachments/assets/cf904240-1063-4a61-8ec1-d5f3ff9a93a5)





## ERD
![스크린샷 2024-10-18 오전 4 23 28](https://github.com/user-attachments/assets/a7028d72-3258-4dec-b66e-5f1e8dc13f85)





