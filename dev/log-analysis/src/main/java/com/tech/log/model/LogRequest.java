package com.tech.log.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class LogRequest {
    private String status;
    private String url;
    private String browser;
    private LocalDateTime reqTime;
    private Map<String, Integer> apiMap;

}
