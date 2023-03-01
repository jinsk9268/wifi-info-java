<%@ page import="java.util.List" %>
<%@ page import="com.zerobase.wifi.dto.PublicWifiDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.zerobase.wifi.dao.PublicWifiDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link href="style/common.css?v=3" rel="stylesheet" type="text/css"/>
</head>
<body>
    <%
        List<PublicWifiDto> nearWifiList = new ArrayList<>();

        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");
        boolean isClickSelectNearWifiListButton = lat == null || lat.equals("");

        if (!isClickSelectNearWifiListButton) {
            PublicWifiDao publicWifiDao = new PublicWifiDao();
            nearWifiList = publicWifiDao.selectNearWifi(Double.parseDouble(lat), Double.parseDouble(lnt));
        }
    %>
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

        const getSelectNearWifiList = () => {
            if (latitude === undefined || longitude === undefined) {
                latitude = <%=lat%> === null ? 0 : <%=lat%>;
                longitude = <%=lnt%> === null ? 0 : <%=lnt%>;
            }
            const url = "?lat="+latitude+"&lnt="+longitude;
            window.location.assign(url);
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
        LAT: <input id="latitude" type="text" value="<%=lat == null ? 0.0 : lat%>"/> ,
        LNT: <input id="longitude" type="text" value="<%=lnt == null ? 0.0 : lnt%>"/>
        <button onClick="getLocation()">내 위치 가져오기</button>
        <button onClick="getSelectNearWifiList()">근처 WIFI 정보 보기</button>
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
                <% if(nearWifiList.size() == 0) { %>
                    <tr>
                        <td class="td-require-data" colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
                    </tr>
                <% } else { %>
                    <% for (PublicWifiDto row: nearWifiList) { %>
                        <tr>
                            <td><%=row.getDistance()%></td>
                            <td><%=row.getManageNo()%></td>
                            <td><%=row.getBorough()%></td>
                            <td><%=row.getWifiName()%></td>
                            <td><%=row.getAddressDetail()%></td>
                            <td><%=row.getAddressStreet()%></td>
                            <td><%=row.getFloor()%></td>
                            <td><%=row.getInstallType()%></td>
                            <td><%=row.getInstallAgency()%></td>
                            <td><%=row.getServiceText()%></td>
                            <td><%=row.getNetType()%></td>
                            <td><%=row.getInstallYear()%></td>
                            <td><%=row.getInoutDoor()%></td>
                            <td><%=row.getWifiConnectionEnv()%></td>
                            <td><%=row.getLongitude()%></td>
                            <td><%=row.getLatitude()%></td>
                            <td><%=row.getWorkDatetime()%></td>
                        </tr>
                    <% }%>
                <% } %>
            </tbody>
        </table>
    </section>
</body>
</html>