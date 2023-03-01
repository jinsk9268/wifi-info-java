<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1>북마크 목록</h1>
    <nav>
        <a href="index.jsp">홈</a> |
        <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="bookmark.jsp">북마크 보기</a> |
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </nav>
    <section>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>북마크 이름</th>
                    <th>와이파이명</th>
                    <th>등록일자</th>
                    <th>비고</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="td-require-data" colspan=5>데이터가 존재하지 않습니다.</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="td-center"><a>삭제</a></td>
                </tr>
            </tbody>
        </table>
    </section>
</body>
</html>
