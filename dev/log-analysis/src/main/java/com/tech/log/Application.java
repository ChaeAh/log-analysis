package com.tech.log;

import com.tech.log.io.InputLog;
import com.tech.log.io.OutputLog;
import com.tech.log.model.LogRequest;
import com.tech.log.model.LogResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 01. input.log 추출
       List<LogRequest> fileList = InputLog.readInputLog();

        // 02. 로그 분석
       LogResponse outputLogResponse = LogAnalysis.getOutPutLog(fileList);

        //03. output.log.
       OutputLog.writeOutPutLog(outputLogResponse);
    }

}
