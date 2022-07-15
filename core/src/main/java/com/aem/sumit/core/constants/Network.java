package com.aem.sumit.core.constants;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public final class Network {
    public static  String readJson(String url) throws IOException {
        try{
            /**
             * Get the URL object from the passed url string
             */
            URL requestUrl=new URL(url);
            /**
             * Creating an object of HttpURLConnection
             */
            HttpsURLConnection connection = (HttpsURLConnection) requestUrl.openConnection();
            /**
             * Setting the request method
             */
            connection.setRequestMethod("GET");
            /**
             * Setting the request property
             */
            connection.setRequestProperty("User-Agent",AppConstants.USER_AGENT);
            /**
             * Get the response code
             */
            int respCode = connection.getResponseCode();

            if(respCode==HttpsURLConnection.HTTP_OK);
            {
                /**
                 * Getting an instance of BufferedReader to read the response returned
                 */
                BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
                /**
                 * String which will read the response line by line
                 */
                String inputLine;
                /**
                 * StringBuffer object to append the string as a whole
                 */
                StringBuffer response = new StringBuffer();
                /**
                 * Read until empty line is encountered
                 */
                while ((inputLine=in.readLine())!=null){
                    /**
                     * Append each line to make the response as a whole
                     */
                    response.append(inputLine);
                }
                /**
                 * Closing the BufferedReader to avoid memory leaks
                 */
                in.close();
                /**
                 * Return the response
                 */
                return response.toString();
            }
        }
         catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
