package com.example.smartdevicemanipulator.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.smartdevicemanipulator.client.DeviceDto;

public class DeviceDtoParceable implements Parcelable {

    private String name;
    private String uuid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(uuid);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<DeviceDtoParceable> CREATOR = new Parcelable.Creator<DeviceDtoParceable>() {
        public DeviceDtoParceable createFromParcel(Parcel in) {
            return new DeviceDtoParceable(in);
        }

        public DeviceDtoParceable[] newArray(int size) {
            return new DeviceDtoParceable[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private DeviceDtoParceable(Parcel in) {
        name = in.readString();
        uuid = in.readString();
    }

    public DeviceDtoParceable() {
    }

    public static DeviceDto toDto(DeviceDtoParceable parceable) {
        return new DeviceDto(parceable.name, null, parceable.uuid);
    }

    public static DeviceDtoParceable fromDto(DeviceDto deviceDto) {
        DeviceDtoParceable deviceDtoParceable = new DeviceDtoParceable();
        deviceDtoParceable.name = deviceDto.getName();
        deviceDtoParceable.uuid = deviceDto.getUuid();
        return deviceDtoParceable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
