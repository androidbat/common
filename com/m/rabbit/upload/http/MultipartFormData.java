package com.m.rabbit.upload.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

public class MultipartFormData {
    private final String CRLF = "\r\n";
    private Vector<String[]> postData;
    private Vector<Object[]> fileDate;
    private String contentType = "multipart/form-data; boundary=";
    private String boundary;

    public MultipartFormData() {
        postData = new Vector<String[]>();
        fileDate = new Vector<Object[]>();
        boundary = String.valueOf(new Date().getTime());
    }

    public String getContentType() {
        return contentType + boundary;
    }

    public byte[] buildMultipartFormDataPostBody() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = null;
        try {
            for (String[] data : postData) {
                out.write(("--" + boundary + CRLF).getBytes());
                out.write(("Content-Disposition: form-data; name=\"" + data[0] + "\"" + CRLF + CRLF).getBytes());
                out.write((data[1] + CRLF).getBytes());
            }
            for (Object[] data : fileDate) {
                out.write(("--" + boundary + CRLF).getBytes());
                out.write(("Content-Disposition: form-data; name=filePart; filename=\"" + String.valueOf(data[0])
                        + "\"" + CRLF).getBytes());
                
                out.write(("Content-Type: application/octet-stream" + CRLF + CRLF).getBytes());
//                out.write(("Content-Type: application/octet-stream;name=\"uploadfile" + CRLF + CRLF).getBytes());
                out.write((byte[]) data[1]);
                out.write(CRLF.getBytes());
            }
            out.write(("--" + boundary + "--" + CRLF).getBytes());
            b = out.toByteArray();
        } catch (IOException e) {
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
        }
        return b;
    }

    public void setPostData(String key, String value) {
        String[] data = new String[2];
        if (key != null) {
            data[0] = key;
            data[1] = (value == null ? "" : value);
        }
        postData.add(data);
    }

    public void setfileData(String fileName, byte[] value) {
        Object[] data = new Object[2];
        if (fileName != null) {
            data[0] = fileName;
            data[1] = value;
        }
        fileDate.add(data);
    }
}
