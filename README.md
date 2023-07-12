# madcamp_week2_front

# A. 개발 팀원

정성원

윤현우


# B. 개발 환경

OS : Android(minSdk: 24, targetSdk: 31)


Language : Java, javascript

front : Android Studio

Back : node.js mysql

Target Device : Galaxy S7


# C. 앱 소개

Vote

너무 많은 선택에 지친 현대인들을 위한 어플.
어디에 갈지 고민이 될 때, 어플을 통해 온라인으로 투표를 열면 유저들이 투표를 하여 내가 갈 길을 정해준다.

## 로그인 및 회원가입

![카카오 회원가입](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/9bd12e4b-c41e-4aab-9740-dda8f4cf8d42)

![로그인 실패](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/4f3d3478-6bf6-47bf-915d-4bcafdb3a529)

![회원가입](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/76e9d244-bff3-42e5-a637-9eed91ea4d6e)

### 주요 기능
서버와 연결하여 로그인과 회원가입이 가능하다.
id와 비밀번호, 닉네임, 장소를 적어 회원가입을 할 수 있는 기능을 만들었다.
만약 자신이 카카오에 계정이 있다면 이를 통해서도 로그인이 가능하다.
장소를 적을 때, 주소 검색을 이용한다.

### 기술 설명

node.js와 MySQL을 이용하여 각 유저들의 정보를 db에 저장할 수 있도록 하였다.
카카오의 로그인 api를 이용하여 카카오를 통해 oauth 2.0 방식의 로그인 기능을 구현할 수 있도록 하였다.
또한 장소를 적을 때, 카카오의 웹 api를 사용해 html파일에 대해 정보를 받아오는 것으로 구현하였다.


## Tab1. 커뮤니티


![게시판](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/356454c8-1944-403f-8c68-c465e6535424)
### 주요 기능
유저들이 제목과 내용, 날짜를 포함한 글을 적을 수 있는 게시판을 구현하였다.
유저들은 다른 유저들이 적은 글에 댓글을 달 수 있다.

### 기술 설명
안드로이드 스튜디오에서 Retrofit2을 이용해서 @POST와 @GET 요청을 이용하였다. 
node.js의 Body-parser와 함께 하여 서버에게 객체를 보내거나 객체를 받아오는 걸 자유자재로 할 수 있었다.
게시판에 필요한 db 구조를 잡아 활용하였다.

## Tab2. 투표


![KakaoTalk_20230712_202107178](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/d0e3d552-34a5-46b4-839a-8632a1cfbd85)
![카테고리](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/b85d8d29-beaa-467b-bca4-05ae640b81d9)
![불러온 주소](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/995dccc3-c817-4bf2-a08c-fb87dee57f87)

### 주요 기능
어플의 핵심기능인 투표가 가능하다. 한 유저가 투표를 올리기 위한 플로팅 버튼을 누룬다.
그러면 그 투표의 제목 내용 카테고리 위치등이 설정이 가능하다.
위치의 경우, 내 위치로 검색 가능하고
카테고리는 기본적으로 음식점, 카페, 피시방, 숙박시설 혹은 원하는 값으로 수정이 가능하다.
이러한 카테고리와 위치 정보를 바탕으로 근처의 시설들을 불렁ㄹ 수 있다.
이를 보는 사람은 투표를 하기 전엔 결과를 볼 수 없고 투표를 하면 결과를 볼 수 있게된다.



### 기술 설명
특히 위치의 경우, GPS의 위치 권한을 받아와서 좌표를 위도 경도로 받아온다.
이후 카카오의 REST API를 이용하여 Retrofit2로 URL로 요청을 날려 위치에 대응되는 주소를 받아 저장해주며
이후 좌표를 쿼리에 넣어 키워드를 통한 검색을 한다. 여기서 카테고리는 검색어가 된다.
마찬가지로 Retrofit에 GSON을 이용하여 깊은 JSON String이나 객체에 대해 대응이 가능했다.

## Tab 3. 프로필

DB에서 데이터를 불러올 수 있다. 로그아웃이 구현되어있다.
![프로필](https://github.com/YUNHYUNWOO/madcamp_week2_front/assets/122684695/b4fc4f8e-92d3-4680-96e9-5734232c1235)




