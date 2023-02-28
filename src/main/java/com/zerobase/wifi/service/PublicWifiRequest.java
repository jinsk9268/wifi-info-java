package com.zerobase.wifi.service;

import com.google.gson.*;
import com.zerobase.wifi.dto.PublicWifiDto;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PublicWifiRequest {
    public String getPublicWifiData(int startIdx, int endIdx) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode("42777357666a696e3130354f586c6747","UTF-8") );
        urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") );
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startIdx),"UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode(String.valueOf(endIdx),"UTF-8"));
        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return sb.toString();
    }

    public JsonObject getPublicWifiInfoObject(String data) {
        return JsonParser.parseString(data).getAsJsonObject()
                .get("TbPublicWifiInfo").getAsJsonObject();
    }

    public int getTotalCount(String data) {
        return getPublicWifiInfoObject(data).get("list_total_count").getAsInt();
    }

    public List generateTotalPublicWifiList() {
        List<PublicWifiDto> rowList = new ArrayList<>();

        try {
            int totalCount = getTotalCount(getPublicWifiData(1, 1));

            int startIdx = 1;
            while (startIdx <= totalCount) {
                String data = getPublicWifiData(startIdx, Math.min(startIdx + 999, totalCount));
                JsonArray row = getPublicWifiInfoObject(data).get("row").getAsJsonArray();

                for (int i = 0; i < row.size(); i++) {
                    PublicWifiDto publicWifiDto = new PublicWifiDto();
                    JsonObject rowData = row.get(i).getAsJsonObject();

                    publicWifiDto.setManageNo(rowData.get("X_SWIFI_MGR_NO").getAsString());
                    publicWifiDto.setBorough(rowData.get("X_SWIFI_WRDOFC").getAsString());
                    publicWifiDto.setWifiName(rowData.get("X_SWIFI_MAIN_NM").getAsString());
                    publicWifiDto.setAddressDetail(rowData.get("X_SWIFI_ADRES1").getAsString());
                    publicWifiDto.setAddressStreet(rowData.get("X_SWIFI_ADRES2").getAsString());
                    publicWifiDto.setFloor(rowData.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    publicWifiDto.setInstallType(rowData.get("X_SWIFI_INSTL_TY").getAsString());
                    publicWifiDto.setInstallAgency(rowData.get("X_SWIFI_INSTL_MBY").getAsString());
                    publicWifiDto.setServiceText(rowData.get("X_SWIFI_SVC_SE").getAsString());
                    publicWifiDto.setNetType(rowData.get("X_SWIFI_CMCWR").getAsString());
                    publicWifiDto.setInstallYear(rowData.get("X_SWIFI_CNSTC_YEAR").getAsString());
                    publicWifiDto.setInoutDoor(rowData.get("X_SWIFI_INOUT_DOOR").getAsString());
                    publicWifiDto.setWifiConnectionEnv(rowData.get("X_SWIFI_REMARS3").getAsString());
                    publicWifiDto.setLatitude(rowData.get("LAT").getAsDouble());
                    publicWifiDto.setLongitude(rowData.get("LNT").getAsDouble());
                    publicWifiDto.setWorkDatetime(rowData.get("WORK_DTTM").getAsString());

                    rowList.add(publicWifiDto);
                }
                startIdx += 1000;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowList;
    }
}
