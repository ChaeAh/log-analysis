package com.tech.log;

import com.tech.log.model.LogRequest;
import com.tech.log.model.LogResponse;

import java.util.*;
import java.util.List;

/**
 * @author chaeah.kim
 */
public class LogAnalysis {

    public static LogResponse getOutPutLog(List<LogRequest> fileList) {
        int blogCnt = 0;
        int bookCnt = 0;
        int imageCnt = 0;
        int knowledgeCnt = 0;
        int newsCnt = 0;
        int vclipCnt = 0;

        int totalCount = 0;
        Map<String, Integer> apiMap = new HashMap<>();
        Map<String, Integer> browserMap = new HashMap<>();
        List<String> apiKeyList = new ArrayList<>();

        for (LogRequest target : fileList) {

            if (!"200".equals(target.getStatus())) continue;

            if (target.getUrl().startsWith("http://apis.daum.net/search/")) {
                String url = target.getUrl().replaceAll("http://apis.daum.net/search/", "");
                String[] serviceId = url.split("\\?");
                switch (serviceId[0]) {
                    case "blog":
                        apiMap.put("blog", blogCnt++);
                        break;
                    case "book":
                        apiMap.put("book", bookCnt++);
                        break;
                    case "image":
                        apiMap.put("image", imageCnt++);
                        break;
                    case "knowledge":
                        apiMap.put("knowledge", knowledgeCnt++);
                        break;
                    case "news":
                        apiMap.put("news", newsCnt++);
                        break;
                    case "vclip":
                        apiMap.put("vclip", vclipCnt++);
                        break;
                    default:
                        break;
                }

                if (serviceId[1].startsWith("apikey")) {
                    String[] query = serviceId[1].split("&");

                    for (String part : query) {
                        String[] keyVal = part.split("=");

                        if ("q".equals(keyVal[0])) continue;

                        apiKeyList.add(keyVal[1]);
                    }
                }
                int count = browserMap.getOrDefault(target.getBrowser(), 0);

                browserMap.put(target.getBrowser(), ++count);
            }
        }

        return LogResponse.builder()
                .apiMap(apiMap)
                .browserMap(browserMap)
                .apiKeyList(apiKeyList)
                .totalCnt(fileList.size())
                .build();
    }
}


