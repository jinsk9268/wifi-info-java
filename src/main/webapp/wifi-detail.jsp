<%@ page import="com.zerobase.wifi.dao.PublicWifiDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1>와이파이 정보 구하기</h1>
    <nav>
        <a href="index.jsp">홈</a> |
        <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="bookmark.jsp">북마크 보기</a> |
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </nav>
    <section>
        <div class="box-bookmark-select">
            <select>
                <option value="none">북마크 그룹 이름 선택</option>
            </select>
            <button>북마크 추가하기</button>
        </div>
        <table>
            <colgroup>
                <col style="width: 20%" />
                <col style="width: 80%" />
            </colgroup>
            <tbody>
                <tr>
                    <th>거리(Km)</th>
                    <td></td>
                </tr>
                <tr>
                    <th>관리번호</th>
                    <td></td>
                </tr>
                <tr>
                    <th>자치구</th>
                    <td></td>
                </tr>
                <tr>
                    <th>와이파이명</th>
                    <td></td>
                </tr>
                <tr>
                    <th>도로명주소</th>
                    <td></td>
                </tr>
                <tr>
                    <th>상세주소</th>
                    <td></td>
                </tr>
                <tr>
                    <th>설치위치(층)</th>
                    <td></td>
                </tr>
                <tr>
                    <th>설치유형</th>
                    <td></td>
                </tr>
                <tr>
                    <th>설치기관</th>
                    <td></td>
                </tr>
                <tr>
                    <th>서비스구분</th>
                    <td></td>
                </tr>
                <tr>
                    <th>망종류</th>
                    <td></td>
                </tr>
                <tr>
                    <th>설치년도</th>
                    <td></td>
                </tr>
                <tr>
                    <th>실내외구분</th>
                    <td></td>
                </tr>
                <tr>
                    <th>WIFI접속환경</th>
                    <td></td>
                </tr>
                <tr>
                    <th>X좌표</th>
                    <td></td>
                </tr>
                <tr>
                    <th>Y좌표</th>
                    <td></td>
                </tr>
                <tr>
                    <th>작업일자</th>
                    <td></td>
                </tr>
            </tbody>
        </table>
    </section>
</body>
</html>