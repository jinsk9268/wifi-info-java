<%@ page import="com.zerobase.wifi.dao.BookmarkDao" %>
<%@ page import="com.zerobase.wifi.dto.BookmarkDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
</head>
<body>
    <%
        BookmarkDao bookmarkDao = new BookmarkDao();
        List<BookmarkDto> list = bookmarkDao.selectBookmarkGroup();

        String deleteId = request.getParameter("delete-id");
        boolean isClickDeleted = deleteId == null;

        boolean isDeletedBookmarkGroup = false;
        if (!isClickDeleted) {
            isDeletedBookmarkGroup = bookmarkDao.deleteBookmarkGroupFromId(Integer.parseInt(deleteId));
        }
    %>
    <script>
        const goToBookmarkGroupAdd = () => {
            window.location.assign("bookmark-group-add.jsp");
        }

        if (<%=isDeletedBookmarkGroup%>) {
            alert("<%=deleteId%>번 북마크 삭제에 성공했습니다.");
            window.location.assign("bookmark-group.jsp");
        }
    </script>
    <h1>북마크 그룹 추가</h1>
    <nav>
        <a href="index.jsp">홈</a> |
        <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="bookmark.jsp">북마크 보기</a> |
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </nav>
    <section>
        <button class="add-margin-bottom" onclick="goToBookmarkGroupAdd()">북마크 그룹 이름 추가</button>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
            </thead>
            <tbody>
            <% if (list.size() == 0) { %>
                <tr>
                    <td class="td-require-data" colspan=6>데이터가 존재하지 않습니다.</td>
                </tr>
            <% } else { %>
                <% for (BookmarkDto bookmarkDto: list) { %>
                    <tr>
                        <td><%=bookmarkDto.getId()%></td>
                        <td><%=bookmarkDto.getBookmarkName()%></td>
                        <td><%=bookmarkDto.getBookmarkOrder()%></td>
                        <td><%=bookmarkDto.getRegisterDatetime()%></td>
                        <td>
                            <%=bookmarkDto.getModificationDatetime() == null ? "" : bookmarkDto.getModificationDatetime()%>
                        </td>
                        <td class="td-center">
                            <a href="bookmark-group-edit.jsp?id=<%=bookmarkDto.getId()%>">수정</a>
                            <a href="bookmark-group.jsp?delete-id=<%=bookmarkDto.getId()%>">삭제</a>
                        </td>
                    </tr>
                <% } %>
            <% } %>
            </tbody>
        </table>
    </section>
</body>
</html>
