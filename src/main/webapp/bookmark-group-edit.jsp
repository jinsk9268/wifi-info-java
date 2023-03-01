<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1>북마크 그룹 수정</h1>
    <nav>
        <a href="index.jsp">홈</a> |
        <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="bookmark.jsp">북마크 보기</a> |
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </nav>
    <section>
        <table>
            <colgroup>
                <col style="width: 20%" />
                <col style="width: 80%" />
            </colgroup>
            <tbody>
            <tr>
                <th>북마크 이름</th>
                <td><input id="bookmarkGroupName" type="text" /></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input id="bookmarkOrder" type="number" /></td>
            </tr>
            <tr>
                <td class="td-require-data" colspan="2">
                    <a href="bookmark-group.jsp">돌아가기</a> |
                    <button>수정</button>
                </td>
            </tr>
            </tbody>
        </table>
    </section>
</body>
</html>
