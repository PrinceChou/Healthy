package android.microanswer.healthy.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.data;

/**
 * 该类里面是一些简单的从网络获取的信息
 *
 * @author Micro
 */
public class InternetServiceTool {

    /**
     * 天气
     *
     * @author Micro
     */
    public static final class Weather {

        /**
         * 获取当前的天气预报信息<br/>
         * {@linkplain @http://apistore.baidu.com/apiworks/servicedetail/478.html}
         *
         * @param cityName 城市名字【国内的支持中英文，国外支持英文】
         * @return
         */
        public static final String get(String cityName) {
            String httpUrl = "http://apis.baidu.com/heweather/weather/free";
            String httpArg = "city=" + cityName;
            return baiduApiRequest(httpUrl, httpArg, "GET");
        }

    }

    /**
     * 历史上的今天
     *
     * @author Micro
     */
    public static class HistoryToday {

        /**
         * 获取指定日期在历史上发生的大事件
         */
        public static String get(int month, int day, int page, int count) {
            String httpArg = "";
            if (count > 50) {
                count = 50;
            }
            httpArg = "yue=" + month + "&ri=" + day + "&type=1&page=1&rows=" + count + "&dtype=JOSN&format=false";
            return baiduApiRequest("http://apis.baidu.com/avatardata/historytoday/lookup", httpArg, "GET");
        }
    }

    private static final String baiduApiRequest(String httpUrl, String httpArg, String requestMethod) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("apikey", ApiKey.BAIDU_APIKEY);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
//				sbf.append(System.lineSeparator());
            }
            result = sbf.toString();
        } catch (Exception e) {
            result = "error:" + e.toString();
        } finally {
            try {
                reader.close();
            } catch (Exception e2) {
                result = "error:" + e2.toString();
            }
        }
        return result;
    }

    /**
     * 请求一个网址，返回一个结果
     *
     * @param urla
     * @return 文字的结果
     */
    public static final String request(String urla) {
        String result = "";
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urla);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            StringBuffer sbf = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sbf.append(line);
            }
            result = sbf.toString();
        } catch (Exception e) {
            result = "error-->[" + e.toString() + "]";
        } finally {
            try {
                if (inputStreamReader != null)
                    inputStreamReader.close();
            } catch (Exception e2) {
                result = "error-->[" + e2.toString() + "]";
            }
        }
        return result;
    }

}