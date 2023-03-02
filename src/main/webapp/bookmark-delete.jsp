<%@ page import="com.zerobase.wifi.dao.PublicWifiDao" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <%
        String manageNo = request.getParameter("manage-no");
        PublicWifiDao publicWifiDao = new PublicWifiDao();
        Map<String, String> deleteData = publicWifiDao.selectDeleteBookmarkWifi(manageNo);

        String deleteManageNo = request.getParameter("delete-id");
        boolean isClickDeleteButton = deleteManageNo == null;

        boolean isDeleteBookmarkData = false;
        if (!isClickDeleteButton) {
            isDeleteBookmarkData = publicWifiDao.updateBookmarkDataNull(deleteManageNo);
        }
    %>
    <script>
        const onClickBookmarkDelete = () => {
            const url = "bookmark-delete.jsp?delete-id=<%=manageNo%>";
            window.location.assign(url);
        }

        if (<%=isDeleteBookmarkData%>) {
            alert("북마크 정보 삭제에 성공했습니다.");
            window.location.assign("bookmark.jsp");
        }
    </script>
    <h1>북마크 삭제기</h1>
    <nav>
        <a href="index.jsp">홈</a> |
        <a href="history.jsp">위치 히스토리 목록</a> |
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="bookmark.jsp">북마크 보기</a> |
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </nav>
    <section>
        <div class="add-margin-bottom">
            북마크를 삭제하시겠습니까?
        </div>
        <table>
            <colgroup>
                <col style="width: 20%" />
                <col style="width: 80%" />
            </colgroup>
            <tbody>
            <tr>
                <th>북마크 이름</th>
                <td><%=deleteData.get("bookmarkName")%></td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><%=deleteData.get("wifiName")%></td>
            </tr>
            <tr>
                <th>등록일자</th>
                <td><%=deleteData.get("registerDatetime")%></td>
            </tr>
            <tr>
                <td class="td-center" colspan="2">
                    <a href="bookmark.jsp">돌아가기</a> |
                    <button onclick="onClickBookmarkDelete()">삭제</button>
                </td>
            </tr>
            </tbody>
        </table>
    </section>
</body>
</html>
