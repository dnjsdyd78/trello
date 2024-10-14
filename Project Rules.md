# Code Convention

## 1. 리스폰반환시 ApiResponse 객체에 감싸서 반환
- ## controller 리턴시 ApiResponse.onSuccess() <= 감싸서반환
### ex) return ApiResponse.onSuccess(Entity.create(request));

## 2. 글로벌 익셉션 커스텀 및 예외 설계시 ApiException 통해 예외문 발생시키기

- ## ErrorStatus 클래스에서 비즈니스 로직에 맞게 예외문 커스텀
### _NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "404", "존재하지 않는 카드입니다."),
### _FORBIDDEN(HttpStatus.FORBIDDEN, "403", "접근 권한이 없습니다.");

- ## 예외문 설계시 ApiException 클래스 통해 예외 발생시키기
### ex) cardRepository.findById(card).orElseThrow(() -> new ApiException(ErrorStatus.NOT_FOUND)


## 3. 어플리케이션 설정파일에  SQL,REDIS,JWT secret key 각자 환경변수로 설정
### 설정파일내부에서 직접입력 X
