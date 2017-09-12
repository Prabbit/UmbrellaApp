package com.weatherapp.umbrella.net.converters;

import com.weatherapp.umbrella.models.common.BaseModel;

/**
 * Created by CodeWord on 9/11/2017.
 */

public interface Convertible {
    BaseModel convert(String  response);
}
