<%@ page import="com.zerobase.wifi.dao.BookmarkDao" %>
<%@ page import="com.zerobase.wifi.dto.BookmarkDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <%
        String groupId = request.getParameter("id");
        String editName = request.getParameter("edit-name");
        String editOrder = request.getParameter("edit-order");

        BookmarkDao bookmarkDao = new BookmarkDao();
        BookmarkDto bookmarkDto = bookmarkDao.selectBookmarkGroupFromId(Integer.parseInt(groupId));

        boolean isEditBookmarkGroup = false;
        boolean isClickEditButton = editName == null;
        if (!isClickEditButton) {
            isEditBookmarkGroup = bookmarkDao.updateBookmarkGroup(Integer.parseInt(groupId), editName, Integer.parseInt(editOrder));
        }
    %>
    <script>
        const updateBookmarkGroup = () => {
            const editName = document.getElementById("bookmarkGroupEditName").value;
            const editOrder = document.getElementById("bookmarkEditOrder").value;

            if (editName == "" || editOrder == "") {
                return alert("수정 항목들이 비어있습니다. 다시 확인해주세요.");
            }

            const url = "bookmark-group-edit.jsp?id=" + <%=groupId%> + "&edit-name=" + editName + "&edit-order=" + editOrder;
            window.location.assign(url);
        }

        if (<%=isEditBookmarkGroup%>) {
            alert("수정에 성공했습니다. 북마크 그룹 페이지로 이동됩니다.")
            window.location.assign("bookmark-group.jsp");
        }
    </script>
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
                <td><input id="bookmarkGroupEditName" type="text" value="<%=bookmarkDto.getBookmarkName()%>"/></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input id="bookmarkEditOrder" type="number" value="<%=bookmarkDto.getBookmarkOrder()%>"/></td>
            </tr>
            <tr>
                <td class="td-require-data" colspan="2">
                    <a href="bookmark-group.jsp">돌아가기</a> |
                    <button onclick="updateBookmarkGroup()">수정</button>
                </td>
            </tr>
            </tbody>
        </table>
    </section>
</body>
</html>
