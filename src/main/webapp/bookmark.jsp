<%@ page import="com.zerobase.wifi.dao.BookmarkDao" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <%
        BookmarkDao bookmarkDao = new BookmarkDao();
        List<Map> joinList = bookmarkDao.selectBookmarkWithPublicWifi();
    %>
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
                <% if (joinList.size() == 0) { %>
                    <tr>
                        <td class="td-require-data" colspan=5>데이터가 존재하지 않습니다.</td>
                    </tr>
                <% } else { %>
                    <% for (Map data: joinList) { %>
                        <tr>
                            <td><%=data.get("id")%></td>
                            <td><%=data.get("bookmarkName")%></td>
                            <td><%=data.get("wifiName")%></td>
                            <td><%=data.get("registerDatetime")%></td>
                            <td class="td-center">
                                <a href="bookmark-delete.jsp?manage-no=<%=data.get("manageNo")%>">삭제</a>
                                <%=data.get("manageNo")%>
                            </td>
                        </tr>
                    <% } %>
                <% } %>
            </tbody>
        </table>
    </section>
</body>
</html>
