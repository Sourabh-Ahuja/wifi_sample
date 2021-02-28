package com.sourabh.openapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "wifi")
public class Wifi implements Parcelable {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name="wifiName")
    String wifiName;

    @ColumnInfo(name="wifiPassword")
    String wifiPassword;

    @ColumnInfo(name="signal")
    String signalStrength;

    public Wifi(@NotNull String wifiName, String wifiPassword, String signalStrength){
        this.wifiPassword = wifiPassword;
        this.wifiName = wifiName;
        this.signalStrength = signalStrength;
    }

    protected Wifi(Parcel in) {
        wifiName = in.readString();
        wifiPassword = in.readString();
        signalStrength = in.readString();
    }

    public static final Creator<Wifi> CREATOR = new Creator<Wifi>() {
        @Override
        public Wifi createFromParcel(Parcel in) {
            return new Wifi(in);
        }

        @Override
        public Wifi[] newArray(int size) {
            return new Wifi[size];
        }
    };

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    @NotNull
    @Override
    public String toString() {
        return "EntityWifi{" +
                "wifiName='" + wifiName + '\'' +
                ", wifiPassword='" + wifiPassword + '\'' +
                ", signalStrength='" + signalStrength + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wifiName);
        dest.writeString(wifiPassword);
        dest.writeString(signalStrength);
    }
}
