package cc.lylllcc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProxyConfig {

    public static String proxyHost;

    public static String proxyPort;

    @Value("${socket.host}")
    private String host;

    @Value("${socket.port}")
    private String port;

    @PostConstruct
    public void initData(){
        proxyHost = host;
        proxyPort= port;
    }

}
