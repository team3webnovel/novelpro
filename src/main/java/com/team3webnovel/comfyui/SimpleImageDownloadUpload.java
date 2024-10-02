package com.team3webnovel.comfyui;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleImageDownloadUpload {

    public static void downloadAndUpload() throws Exception {
        String imageUrl = "http://192.168.0.237:8188/view?filename=ComfyUI_00395_.png&subfolder=&type=output&nocache=1727339457572";
        String uploadUrl = "http://192.168.0.237:8188/upload/image";

        // 이미지 다운로드
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 이미지 다운로드 스트림으로 읽기
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageData = byteArrayOutputStream.toByteArray();
        inputStream.close();
        System.out.println("Image downloaded successfully.");

        // 이미지 업로드
        URL uploadUrlObj = new URL(uploadUrl);
        HttpURLConnection uploadConnection = (HttpURLConnection) uploadUrlObj.openConnection();
        uploadConnection.setDoOutput(true);
        uploadConnection.setRequestMethod("POST");
        uploadConnection.setRequestProperty("Content-Type", "image/png");

        OutputStream outputStream = uploadConnection.getOutputStream();
        outputStream.write(imageData);
        outputStream.flush();
        outputStream.close();

        int responseCode = uploadConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Image uploaded successfully.");
        } else {
            System.out.println("Failed to upload image. Response code: " + responseCode);
        }
    }
}
