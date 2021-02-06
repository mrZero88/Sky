package com.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Utils {

    private Utils() {
    }

    public static InputStream cloneInputStream(InputStream inputStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // Code simulating the copy
            // You could alternatively use NIO
            // And please, unlike me, do something about the Exceptions :D
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            // Open new InputStreams using recorded bytes
            // Can be repeated as many times as you wish
            InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
            return is1;
        } catch (Exception e) {

        }
        return null;
    }

}
