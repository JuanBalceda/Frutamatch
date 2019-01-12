package com.apps.balceda.fruits.culqi;

import org.json.JSONObject;

public interface TokenCallback {

    void onSuccess(JSONObject token);

    void onError(Exception error);

}
