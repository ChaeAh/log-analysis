package com.tech.log.io;

import com.tech.log.model.LogResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author chaeah.kim
 */
@Slf4j
public class OutputLog {

    public static void writeOutPutLog(LogResponse logResponse){
        Map<String, Integer> countMap = new HashMap<>();

        for (String temp : logResponse.getApiKeyList()) {
            int count = countMap.getOrDefault(temp, 0);
            countMap.put(temp, ++count);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("최다호출 APIKEY \n");

        countMap.keySet().stream()
                .sorted((o1, o2) -> countMap.get(o2).compareTo(countMap.get(o1)))
                .limit(1)
                .forEach(x -> {
                    sb.append(String.format("%s%n", x));
                });

        sb.append(System.getProperty("line.separator"));
        sb.append("상위 3개의 API Service ID와 각각의 요청 수" + System.getProperty("line.separator"));
        // 상위 3개의 apiService Id와 각각의 요청수
        List<String> keySet = new ArrayList<>(logResponse.getApiMap().keySet());

        keySet.stream()
                .sorted((o1, o2) -> logResponse.getApiMap().get(o2).compareTo(logResponse.getApiMap().get(o1)))
                .limit(3)
                .forEach(x -> {
                    sb.append(String.format("%s : %s %n", x, logResponse.getApiMap().get(x)));
                });
//
        Map<String,Integer> browserMap = logResponse.getBrowserMap();
//        browserMap.keySet().stream()
//                .sorted((o1, o2) -> browserMap.get(o2).compareTo(browserMap.get(o1)))
//                .forEach(x -> {
//                    sb.append(String.format("%s : %s %n", x, browserMap.get(x)));
//                });

        sb.append(System.getProperty("line.separator"));
        sb.append("웹브라우저별 사용 비율" + System.getProperty("line.separator"));

        int sum = browserMap.values().stream().mapToInt(Integer::intValue).sum();

        browserMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(browser -> {
                    double rate = ((double) browser.getValue() / (double) sum) * 100;
                    String pattern = "0";

                    DecimalFormat form = new DecimalFormat(pattern);

                    sb.append(String.format("%s : %s %n", browser.getKey(), form.format(rate) + "%"));
                });

        System.out.println(sb.toString());

        File file = new File("/Users/kimchaeah/Documents/dktechin/output.log");

        if(file.exists()) {
            file.delete();
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));

            bw.write(sb.toString());
            bw.newLine();

            bw.flush();
        }catch (Exception e) {
            log.error("error : {}",e.getMessage());
        }finally {
            if (bw != null) try { bw.close(); } catch (Exception ex) { log.error("BufferReader Close Exception {} ", ex.getMessage());}
        }

    }
}


