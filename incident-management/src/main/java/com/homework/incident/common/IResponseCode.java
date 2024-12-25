package com.homework.incident.common;

import java.io.Serializable;

public interface IResponseCode extends Serializable {
    int getCode();

    String getMessage();
}