package cn.qingfeng.myimageloaderlibrary.net;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2016/10/26 12:57
 * @DESC: $TODO
 * @VERSION: V1.0
 */
public class NetDownload {
    public static InputStream getInputStream(String path) throws IOException {
        HttpURLConnection connection = null;
        InputStream in = null;
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                in = connection.getInputStream();
                return in;
            } else {
                throw new RuntimeException();
            }
        } finally {
            if(in != null){
                in.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
