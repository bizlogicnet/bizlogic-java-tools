package com.bizlogic.tools.functions;

import com.bizlogic.tools.model.ApiResponse;

public class ApiTools {
  
  public static ApiResponse buildApiResponse(Object data) {
    ApiResponse result = new ApiResponse();
    result.setData(data);
    return result;
  }

}
