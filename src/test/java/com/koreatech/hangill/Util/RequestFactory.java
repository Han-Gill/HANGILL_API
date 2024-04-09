package com.koreatech.hangill.Util;


import com.koreatech.hangill.dto.request.CreateBuildingRequest;

public class RequestFactory {
    public static CreateBuildingRequest createBuildingRequest(String name, String description) {
        return new CreateBuildingRequest(name, description, null, null);
    }

}
