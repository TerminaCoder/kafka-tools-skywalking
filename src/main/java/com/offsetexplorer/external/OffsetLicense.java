package com.offsetexplorer.external;

/**
 * @author created by LiuXF on 2024/06/05 01:08:46
 */

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class OffsetLicense {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws Exception {
        File tmp = saveTmp();
        String outPath = "/Users/lxf/hack.ktl";
        compressFile(tmp.getAbsolutePath(), outPath);
        tmp.deleteOnExit();
    }

    public static void compressFile(String inputFilePath, String outputFilePath) throws Exception {
        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(inputFilePath); FileOutputStream fos = new FileOutputStream(outputFilePath); GZIPOutputStream gzos = new GZIPOutputStream(fos);) {
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gzos.write(buffer, 0, len);
            }
            System.out.println("File compressed successfully.");
        }
    }

    public static File saveTmp() throws Exception {
        File tempFile = File.createTempFile(Long.toHexString(System.currentTimeMillis()), "license");
        String content = "%s=%s\n";
        String[] ks = new String[]{"name", "company", "version", "key", "exp", "support", "number", "type"};
        Map<String, String> val = new HashMap<>();
        val.put("name", "hack");
        val.put("company", "hack");
        val.put("version", "3.0");
        val.put("exp", "9999-12-31");
        val.put("support", "9999-12-31");
        val.put("number", "9999");
        val.put("type", "4");
        Charset charset = StandardCharsets.UTF_8;
        final String key = genKey(val.get("name").getBytes(charset), val.get("company").getBytes(charset), val.get("version").getBytes(charset), val.get("support").getBytes(charset), val.get("exp").getBytes(charset), val.get("number").getBytes(charset), val.get("type").getBytes(charset));
        val.put("key", key);
        try (FileWriter fw = new FileWriter(tempFile); BufferedWriter bw = new BufferedWriter(fw)) {
            for (String k : ks) {
                bw.write(String.format(content, k, val.get(k)));
            }
        }
        return tempFile;
    }

    public static String genKey(byte[] name, byte[] company, byte[]... bytes) {
        int len = name.length + company.length;
        for (byte[] bs : bytes) {
            len += bs.length;
        }
        byte[] ret = new byte[len];
        int counter = 0;
        for (byte b : name) {
            if (b % 2 == 0) {
                ret[counter++] = (byte) (b ^ 255);
            }
        }
        for (byte b : company) {
            if (b % 2 != 0) {
                ret[counter++] = (byte) (b ^ 255);
            }
        }
        for (byte[] bs : bytes) {
            for (byte b : bs) {
                ret[counter++] = (byte) (b ^ 255);
            }
        }
        byte[] finalRet = new byte[counter];
        System.arraycopy(ret, 0, finalRet, 0, counter);
        return bytesToHex(finalRet, counter);
    }

    public static String bytesToHex(byte[] bytes, int counter) {
        char[] hexChars = new char[counter * 2];
        for (int j = 0; j < counter; j++) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[(j * 2) + 1] = hexArray[v & 15];
        }
        return new String(hexChars);
    }
}

