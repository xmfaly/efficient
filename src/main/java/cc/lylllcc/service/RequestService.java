package cc.lylllcc.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class RequestService {

    public String get(String url,String param) throws IOException {
        String charset = "UTF-8";
        URLConnection connection = new URL(url + "?" + param).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(response));
        String read;

        while((read=br.readLine()) != null) {
            sb.append(read);
        }
        br.close();
        System.out.println(sb.toString());
        return sb.toString();
    }
}
