package com.app.agnie.owl.Util.DBUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHandler {

    public String sendGetRequest(String requestURL) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sb.toString();
    }
}