package com.kk.common_lib.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.util.Arrays;

public class ZipUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

//    public static void main(String[] args) throws IOException {
//        String originalString = "This is a sample string to compress using Snappy.";
//
//        // Compress
//        byte[] compressed = compress(originalString);
//        System.out.println("Original: " + Arrays.toString(compressed));
//
//        // Decompress
//        String decompressed = uncompress(compressed, String.class);
//
//        System.out.println("Original: " + originalString);
//        System.out.println("Decompressed: " + decompressed);
//    }

    public static byte[] compress(Object obj) {
        try {
            return Snappy.compress(mapper.writeValueAsBytes(obj));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T uncompress(byte[] compressed, Class<T> clazz) {
        try {
            var decompressed = Snappy.uncompress(compressed);
            return mapper.readValue(decompressed, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
