package com.sanshengshui.server.common.data.security;

public interface DeviceCredentialsFilter {

    String getCredentialsId();

    DeviceCredentialsType getCredentialsType();
}
