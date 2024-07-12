# jqueryt-Ajax Comment

jquery와 ajax를 사용한 댓글 CRUD

jquery : 3.6.0

java : 17

spring boot : 3.2.7

mysql : 8.3.0

junit : 4.13.3

querydsl : 5.0.0

## 좋아요 👍
<!--좋아요 gif-->
<img src="https://github.com/laipuni/jQuery-Ajax-Study/assets/144443568/73af0843-4f01-4bb3-aaeb-1c63a7047854" style="width:900px">
</img>

<!--좋아요 query => ajax 방식 vs form 방식-->
### 비동기 방식 vs 동기화 방식

ajax : 1(좋아요 insert) + 1(board 좋아요 개수 update) = 2번 쿼리

form : 1(좋아요 insert) + 1(board 좋아요 개수 update) + 2번(새로고침 댓글,게시판 조회) = 4번 쿼리

## 댓글 무한 스크롤 📜
<!--댓글 무한 스크롤 gif-->
<img src="https://github.com/laipuni/jQuery-Ajax-Study/assets/144443568/54043d31-c550-45a8-8d36-aefa07e68259" style="width:900px">
</img>

## 게시글 댓글 작성 📝
<!--댓글 작성 gif-->
<img src="https://github.com/laipuni/jQuery-Ajax-Study/assets/144443568/567c927d-a1d9-4f7a-b934-24a9225ea833" style="width:900px">
</img>

<!--댓글 작성 query => ajax 방식 vs form 방식-->
### 비동기 방식 vs 동기화 방식

ajax : 1(참조를 위해 게시판 조회) + 1(댓글 insert) = 2번 쿼리

form : 1(참조를 위해 게시판 조회) + 1(댓글 insert) + 2번(새로고침 댓글,게시판 조회) = 4번 쿼리

## 댓글 답장 📝
<!--댓글 답장 gif-->
<img src="https://github.com/laipuni/jQuery-Ajax-Study/assets/144443568/85a91f5b-ac2a-44a4-b94a-7bbb1035555e" style="width:900px">
</img>

<!--댓글 답장 query => ajax 방식 vs form 방식-->
### 비동기 방식 vs 동기화 방식

ajax : 1(참조를 위해 댓글 조회) + 1(참조를 위해 게시판 조회) + 1(답글 insert) = 3번 쿼리

form : 1(참조를 위해 댓글 조회) + 1(참조를 위해 게시판 조회) + 1(답글 insert) + 2번(새로고침 댓글,게시판 조회) = 5번 쿼리

## 답글 불러오기 ☎️ 
<!--답글 불러오기 gif-->
<img src="https://github.com/laipuni/jQuery-Ajax-Study/assets/144443568/5fe72e25-156e-40ce-a9f5-3bc1a75b3ccc" style="width:900px">
</img>


## 댓글 수정 ✏️ 
<!--댓글 수정 gif-->
<img src="https://github.com/laipuni/jQuery-Ajax-Study/assets/144443568/effdf576-5478-4163-9643-c8732bdbc9fe" style="width:900px">
</img>

<!--댓글 수정 query => ajax 방식 vs form 방식-->
### 비동기 방식 vs 동기화 방식

ajax : 1(수정할 댓글 조회) + 1(댓글 수정 update) = 2번 쿼리

form : 1(수정할 댓글 조회) + 1(댓글 수정 update) + 1(답글 insert) + 2번(새로고침 댓글,게시판 조회) = 4번 쿼리


## 댓글 삭제 ❌
<!--댓글 삭제 gif-->
<img src="https://github.com/laipuni/jQuery-Ajax-Study/assets/144443568/ac1fafdd-dd4a-4fdd-b19d-d32cf28b7b15" style="width:900px">
</img>

<!--댓글 삭제 query => ajax 방식 vs form 방식-->
### 댓글 삭제 문제점 발견
이 프로젝트에선 댓글, 답글 기능을 위해 Comment 도메인은 self join 방식으로 도메인 설계를 했다.

self join으로 연관관게를 Cascade.remove 설정했기 때문에 1 + N의 문제가 발생했다. 예를들면 1개의 댓글에 답글이 20개가 달렸다고 가정하자.

JpaRepository에서 제공하는 deleteById로 삭제할 경우, 댓글 조회1 + 답글 조회20 + 댓글 삭제1 + 답글 삭제20 댓글과 답글의 개수만큼 query를 보낸다.

그래서 우선 답글들을 먼저 delete하고, 그 후 댓글을 delete하는 jpql문을 직접 작성하였다. 답글 삭제1 + 댓글 삭제1로 쿼리가 줄었지만 답글이 없는 댓글도 2번 발생한다.

jpa가 제공하는 deleteById를 사용해 답글이 없는 댓글을 삭제하는 경우에도 쿼리를 최소 cascade.remove때문에 조회1 + 삭제1로 2번 발생한다.

### 비동기 방식 vs 동기화 방식

ajax : 1(댓글 삭제) + 1(답글 삭제) = 2번 쿼리

form : 1(댓글 삭제) + 1(답글 삭제) + 2번(새로고침 댓글,게시판 조회) = 4번 쿼리


## 직접 느낀 비동기 방식 장,단점
장점 : 
1. 페이지 리로딩할 때마다 다시 불러오지 않아서 성능이 좋아졌다.
2. 사용자가 좋아요만 눌렀는데 화면이 리로딩되는 불편한 경험이 사라졌다
3. 무한 스크롤같이 동기화 방식으로 불가능했던 기능을 개발할 수 있다.
4. 다양한 ui를 개발할 수있다.

단점:
1. 스크립트로 개발하니 디버깅할 때 유연한 느낌이 없었다.
2. 데이터를 한번에 많이 보낼 수 있어 서버 부하를 일으킬 수 있다고 느꼈다.

