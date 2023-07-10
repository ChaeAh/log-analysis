package com.tech.log.model;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chaeah.kim
 */
@Getter
@Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class LogResponse {

    @Builder.Default
    private Map<String, Integer> apiMap = new HashMap<>();

    @Builder.Default
    private List<String> apiKeyList = new ArrayList<>();

    @Builder.Default
    private Map<String, Integer> browserMap = new HashMap<>();

    private int totalCnt;

}


