package cn.threegiants.sprusermanager.comm.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description:
 * @author: he.l
 * @create: 2019-04-30 11:35
 **/
public class HttpUrlConnection {

    private static final int mTimeout = 10000; // 超时时间
    /**
     * get访问
     */
    public static String[] requestJson(String url) {
        return request(url);
    }
    private static String[] request(String connurl) {
        String[] resultStr = new String[]{"", ""};
        StringBuilder resultData = new StringBuilder("");
        HttpURLConnection conn = null;
        try {
            URL url = new URL(connurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setConnectTimeout(mTimeout);
            conn.connect();
            int resultCode = conn.getResponseCode();
            InputStreamReader in;
            if (resultCode == 200) {
                in = new InputStreamReader(conn.getInputStream());
                BufferedReader buffer = new BufferedReader(in);
                String inputLine;
                while ((inputLine = buffer.readLine()) != null) {
                    resultData.append(inputLine);
                    resultData.append("\n");
                }
                buffer.close();
                in.close();
            }
            resultStr[0] = resultData.toString();
            resultStr[1] = resultCode + "";
            System.out.println(resultData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return resultStr;
    }

}
