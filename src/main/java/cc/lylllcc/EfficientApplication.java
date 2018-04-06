package cc.lylllcc;


import cc.lylllcc.config.ProxyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EfficientApplication {


    public static void main(String[] args) {

        SpringApplication.run(EfficientApplication.class, args);
        System.setProperty("http.proxyHost", ProxyConfig.proxyHost);
        System.setProperty("http.proxyPort", ProxyConfig.proxyPort);
        System.setProperty("https.proxyHost", ProxyConfig.proxyHost);
        System.setProperty("https.proxyPort", ProxyConfig.proxyPort);

    }
}
