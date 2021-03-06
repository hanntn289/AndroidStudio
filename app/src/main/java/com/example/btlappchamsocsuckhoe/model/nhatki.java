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
 * Definition of ObjectType nhatki.
 *
 * @since 2021-11-24
 */
@PrimaryKeys({"id"})
public final class nhatki extends CloudDBZoneObject {
    private String id;

    private String thoi_gian;

    private String tinh_trang;

    private String id_User;

    private String huyetap;

    private String nhiptim;

    private String nhietdo;

    private String cannang;

    private String chieucao;

    public nhatki() {
        super(nhatki.class);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setTinh_trang(String tinh_trang) {
        this.tinh_trang = tinh_trang;
    }

    public String getTinh_trang() {
        return tinh_trang;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getId_User() {
        return id_User;
    }

    public void setHuyetap(String huyetap) {
        this.huyetap = huyetap;
    }

    public String getHuyetap() {
        return huyetap;
    }

    public void setNhiptim(String nhiptim) {
        this.nhiptim = nhiptim;
    }

    public String getNhiptim() {
        return nhiptim;
    }

    public void setNhietdo(String nhietdo) {
        this.nhietdo = nhietdo;
    }

    public String getNhietdo() {
        return nhietdo;
    }

    public void setCannang(String cannang) {
        this.cannang = cannang;
    }

    public String getCannang() {
        return cannang;
    }

    public void setChieucao(String chieucao) {
        this.chieucao = chieucao;
    }

    public String getChieucao() {
        return chieucao;
    }

}
