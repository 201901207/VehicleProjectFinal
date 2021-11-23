package com.example.group2s3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Vehicle {

    @PrimaryKey(autoGenerate = true)
    private int id_vehicle;

    @ColumnInfo
    private String company;

    @ColumnInfo
    private String vehicle_model;

    @ColumnInfo
    private String vehicle_type;

    @ColumnInfo
    private String plat_statCode;

    @ColumnInfo
    private String plat_distCode;

    @ColumnInfo
    private String plat_codeNum;

    @ColumnInfo
    private String VIN;

    public Vehicle(int id_vehicle, String company, String vehicle_model, String vehicle_type, String plat_statCode, String plat_distCode, String plat_codeNum, String VIN) {
        this.id_vehicle = id_vehicle;
        this.company = company;
        this.vehicle_model = vehicle_model;
        this.vehicle_type = vehicle_type;
        this.plat_statCode = plat_statCode;
        this.plat_distCode = plat_distCode;
        this.plat_codeNum = plat_codeNum;
        this.VIN = VIN;
    }

    @Ignore
    public Vehicle(String company, String vehicle_model, String vehicle_type, String plat_statCode, String plat_distCode, String plat_codeNum, String VIN) {
        this.company = company;
        this.vehicle_model = vehicle_model;
        this.vehicle_type = vehicle_type;
        this.plat_statCode = plat_statCode;
        this.plat_distCode = plat_distCode;
        this.plat_codeNum = plat_codeNum;
        this.VIN = VIN;
    }

    public int getId_vehicle() {
        return id_vehicle;
    }

    public void setId_vehicle(int id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getPlat_statCode() {
        return plat_statCode;
    }

    public void setPlat_statCode(String plat_statCode) {
        this.plat_statCode = plat_statCode;
    }

    public String getPlat_distCode() {
        return plat_distCode;
    }

    public void setPlat_distCode(String plat_distCode) {
        this.plat_distCode = plat_distCode;
    }

    public String getPlat_codeNum() {
        return plat_codeNum;
    }

    public void setPlat_codeNum(String plat_codeNum) {
        this.plat_codeNum = plat_codeNum;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }
}
