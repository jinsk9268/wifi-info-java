<%@ page import="com.zerobase.wifi.dao.BookmarkDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <%
        String bookmarkName = request.getParameter("name");
        String bookmarkOrder = request.getParameter("order");
        boolean isClickAddButton = bookmarkName == null || bookmarkName.equals("");

        BookmarkDao bookmarkDao = new BookmarkDao();
        boolean isAddBookmarkGroup = false;
        if (!isClickAddButton) {
            isAddBookmarkGroup = bookmarkDao.insertBookmarkGroup(bookmarkName, Integer.parseInt(bookmarkOrder));
        }
    %>
    <script>
        const onClickAdd = () => {
            const bookmarkGroupName = document.getElementById("bookmarkGroupName").value;
            const bookmarkGroupOrder = document.getElementById("bookmarkOrder").value;

            if (bookmarkGroupName == "" || bookmarkGroupOrder == "") {
                return alert("모든 데이터가 입력되었는지 다시 확인해주세요.");
            }

            const url = "bookmark-group-add.jsp?name=" + bookmarkGroupName + "&order=" + bookmarkGroupOrder;
            window.location.assign(url);
        }

        if (<%=isAddBookmarkGroup%>) {
            alert("북마크 구룹 추가에 성공했습니다.");
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
                    <button onclick="onClickAdd()">추가</button>
                </td>
            </tr>
            </tbody>
        </table>
    </section>
</body>
</html>
