<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <script>
        let latitude;
        let longitude;

        const getLocation = () => {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(getCoordinates);
            } else {
                alert("위치찾기를 지원하지 않습니다. 브라우저를 다시 확인하거나 위치 찾기 설정을 다시 확인해주세요")
            }
        };

        const getCoordinates = (position) => {
            latitude = position.coords.latitude;
            longitude = position.coords.longitude;

            document.getElementById("latitude").value = latitude;
            document.getElementById("longitude").value = longitude;
        }
    </script>
    <h1>와이파이 정보 구하기</h1>
    <nav>
        <a href="index.jsp">홈</a> |
        <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="bookmark.jsp">북마크 보기</a> |
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </nav>
    <div>
        LAT: <input id="latitude" type="text" /> ,
        LNT: <input id="longitude" type="text" />
        <button onClick="getLocation()">내 위치 가져오기</button>
        <button>근처 WIFI 정보 보기</button>
    </div>
    <section>
        <table>
            <thead>
                <tr>
                    <th>거리(Km)</th>
                    <th>관리번호</th>
                    <th>자치구</th>
                    <th>와이파이명</th>
                    <th>도로명주소</th>
                    <th>상세주소</th>
                    <th>설치위치(층)</th>
                    <th>설치유형</th>
                    <th>설치기관</th>
                    <th>서비스구분</th>
                    <th>망종류</th>
                    <th>설치년도</th>
                    <th>실내외구분</th>
                    <th>WIFI접속환경</th>
                    <th>X좌표</th>
                    <th>Y좌표</th>
                    <th>작업일자</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="td-require-location-data" colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
    </section>
</body>
</html>