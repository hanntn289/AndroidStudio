/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.example.btlappchamsocsuckhoe.model;

import com.huawei.agconnect.cloud.database.CloudDBZoneObject;
import com.huawei.agconnect.cloud.database.Text;
import com.huawei.agconnect.cloud.database.annotations.DefaultValue;
import com.huawei.agconnect.cloud.database.annotations.EntireEncrypted;
import com.huawei.agconnect.cloud.database.annotations.NotNull;
import com.huawei.agconnect.cloud.database.annotations.Indexes;
import com.huawei.agconnect.cloud.database.annotations.PrimaryKeys;

import java.util.Date;

/**
 * Definition of ObjectType theodoi.
 *
 * @since 2021-11-24
 */
@PrimaryKeys({"My_id", "follow_id"})
public final class theodoi extends CloudDBZoneObject {
    private String My_id;

    private String follow_id;

    private Boolean status;

    public theodoi() {
        super(theodoi.class);
    }

    public void setMy_id(String My_id) {
        this.My_id = My_id;
    }

    public String getMy_id() {
        return My_id;
    }

    public void setFollow_id(String follow_id) {
        this.follow_id = follow_id;
    }

    public String getFollow_id() {
        return follow_id;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

}