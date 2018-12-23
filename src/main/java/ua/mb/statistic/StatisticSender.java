package ua.mb.statistic;

import javax.net.ssl.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class StatisticSender {

    private String deviceId;

    private final String STATISTIC_URL = "https://ua.traffim.com/iplg/";

    public StatisticSender(String deviceId) {
        this.deviceId = deviceId;
    }

    public void sendStartStatistic(){
        String url = STATISTIC_URL+"start-" + deviceId;

        try {
            this.sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFinalStatusStatistic(int status){
        String url;
        if(status == 1){
           url = STATISTIC_URL+"success-" + deviceId;
        }else {
            url = STATISTIC_URL+"fail-" + deviceId;
        }

        try {
            this.sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // HTTP GET request
    private void sendGet(String url) throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);


        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setConnectTimeout(10000);

        // optional default is GET
        con.setRequestMethod("GET");


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        con.disconnect();
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
