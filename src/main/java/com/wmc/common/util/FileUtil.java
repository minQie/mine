package com.wmc.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Objects;

/**
 * 文件工具类
 *
 *
 */
public class FileUtil {

    /**
     * 文件上传：获取文件名（不包括“.拓展名”）
     *
     * @param file
     * @return
     */
    public static String getFileName(MultipartFile file) {
        int lastPointIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        return file.getOriginalFilename().substring(0, lastPointIndex);
    }

    /**
     * 文件上传：获取文件拓展名（后缀名）
     *
     * @param file
     * @return
     */
    public static String getExtension(MultipartFile file) {
        int lastPointIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        return file.getOriginalFilename().substring(lastPointIndex + 1);
    }

    public byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            System.out.println("file too big...");  
            return null;  
        }  
        FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
        int offset = 0;  
        int numRead;
        while (offset < buffer.length  
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
            offset += numRead;  
        }  
        // 确保所有数据均被读取  
        if (offset != buffer.length) {  
            throw new IOException("Could not completely read file " + file.getName());
        }  
        fi.close();  
        return buffer;  
    }  
  
    /**
     * NIO way 
     * @param filename
     * @return 
     * @throws IOException 
     */  
    public static byte[] toByteArray2(String filename) throws IOException {  
        File f = new File(filename);
        if (!f.exists()) {  
            throw new FileNotFoundException(filename);  
        }  
  
        FileChannel channel = null;  
        FileInputStream fs = null;  
        try {  
            fs = new FileInputStream(f);  
            channel = fs.getChannel();  
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());  
            while ((channel.read(byteBuffer)) > 0) {  
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            try {
                assert channel != null;
                channel.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                fs.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能 
     *  
     * @param filename 
     * @return 
     * @throws IOException 
     */  
    public static byte[] toByteArray3(String filename) throws IOException {  
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();  
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            System.out.println(byteBuffer.isLoaded());  
            byte[] result = new byte[(int) fc.size()];  
            if (byteBuffer.remaining() > 0) {  
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }  
            return result;  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            try {
                assert fc != null;
                fc.close();
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }

}