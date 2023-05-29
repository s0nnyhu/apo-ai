package org.shu.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HTTPClient {
    public static String get(String url, Map<String, String> headers) throws IOException {
        URL requestUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                connection.setRequestProperty(key, value);
            }
        }

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } else {
            throw new IOException("GET request failed with response code: " + responseCode);
        }
    }

    public static String post(String url, String requestBody, Map<String, String> headers) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            // Create URL object
            URL requestUrl = new URL(url);

            // Open connection
            connection = (HttpURLConnection) requestUrl.openConnection();

            // Set request method
            connection.setRequestMethod("POST");

            // Set request headers
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // Enable output and set request body
            connection.setDoOutput(true);
            connection.getOutputStream().write(requestBody.getBytes());

            // Read response
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // Close connections and readers
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    public static InputStream postAudio(String requestUrl, Map<String, String> headers, String requestBody) throws IOException {
        System.out.println(requestUrl);
        System.out.println(requestBody);
        // Create a URL object with the request URL
        URL url = new URL(requestUrl);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");

        // Set custom headers, if provided
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // Enable input and output streams on the connection
        connection.setDoInput(true);
        connection.setDoOutput(true);

        // Write the request body, if required
        if (requestBody != null) {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
        }

        // Get the response code
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        // Handle the response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Return the input stream from the connection
            return connection.getInputStream();
        } else {
            System.out.println("Failed to get audio stream. Response Code: " + responseCode);
            return null;
        }
    }

    public static void download(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        int responseCode = httpConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            FileOutputStream outputStream = new FileOutputStream(savePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            System.out.println("File downloaded successfully.");
        } else {
            System.out.println("Failed to download file.");
        }
        httpConnection.disconnect();
    }

}
