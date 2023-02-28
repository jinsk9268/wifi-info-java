<%@ page import="com.zerobase.wifi.service.PublicWifiRequest" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zerobase.wifi.dto.PublicWifiDto" %>
<%@ page import="com.zerobase.wifi.dao.PublicWifiDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 가져오기</title>
    <style>
        body {
            text-align: center;
        }
    </style>
</head>
<body>
    <%
        PublicWifiDao publicWifiDao = new PublicWifiDao();
        boolean isInsert = false;
        List<PublicWifiDto> dataList = new ArrayList<>();

        if (publicWifiDao.selectTotalCount() > 0) {
            publicWifiDao.deleteTotalPublicWifi();

            PublicWifiRequest wifiData = new PublicWifiRequest();
            dataList = wifiData.generateTotalPublicWifiList();
            isInsert = publicWifiDao.insertTotalPublicWifiData(dataList);
        }
    %>
    <% if (isInsert) { %>
        <h1><%=dataList.size()%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
    <% } else { %>
        <h1>와이파이 정보 가져오기에 실패했습니다. 홈으로 이동해서 다시 시도해주세요.</h1>
    <% } %>
    <a href="index.jsp">홈으로 가기</a>
</body>
</html>