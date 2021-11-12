package com.codechallenge.common.controller.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallDataResponse {
    private String message = "";
}
