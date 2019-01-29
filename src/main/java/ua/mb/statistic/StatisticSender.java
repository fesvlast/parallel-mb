package ua.mb.statistic;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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

    public void sendFinalStatusStatistic(boolean status){
        String url;
        if(status){
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
    private void sendGet(String url) throws IOException{

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        URL obj = new URL(url);
        try{
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setConnectTimeout(7000);

            // optional default is GET
            con.setRequestMethod("GET");


            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            con.disconnect();
        }catch (SocketTimeoutException ex){
            System.out.println(ex.getMessage());
        }

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
