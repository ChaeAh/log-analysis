package com.tech.log.io;

import com.tech.log.model.LogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chaeah.kim
 */
@Slf4j
@RequiredArgsConstructor
public class InputLog {

    public static List<LogRequest> readInputLog() {
//        Resource resource = resourceLoader.getResource("classpath:input.log");
        String filePath = "/Users/kimchaeah/Documents/dktechin3/input.log";

        FileReader fileReader     = null;
        BufferedReader bufferedReader = null;
        List<LogRequest> fileList = new ArrayList<>();

        try {
            ClassPathResource resource = new ClassPathResource("input.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                String[] log = line.replaceAll("\\[","").split("\\]");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // 문자열 -> Date
                LocalDateTime date = LocalDateTime.parse(log[3], formatter);

                System.out.println("date: " + date);
                fileList.add(LogRequest.builder()
                        .status(log[0])
                        .url(log[1])
                        .browser(log[2])
                        .reqTime(LocalDateTime.parse(log[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build());

            }

            fileList.stream().forEach(System.out::println);

        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException {} ", e.getMessage());
        } catch (IOException e) {
            log.error("IOException {} ", e.getMessage());
        } catch(Exception e) {
            log.error("Exception {} ", e.getMessage());
        } finally {
            if (bufferedReader != null) try { bufferedReader.close(); } catch (Exception ex) { log.error("BufferReader Close Exception {} ", ex.getMessage());}
            if (fileReader != null) try { fileReader.close(); } catch (Exception ex) { log.error("fileReader Close Exception {} ", ex.getMessage());}
        }

        return fileList;
    }

}


