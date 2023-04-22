package com.wrappers.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallReport {
    private String callType;
    private String startTime;
    private String endTime;
    private String duration;
    private String cost;
}
