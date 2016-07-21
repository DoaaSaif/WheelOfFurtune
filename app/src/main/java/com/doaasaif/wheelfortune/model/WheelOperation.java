package com.doaasaif.wheelfortune.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shabegt8040 on 7/18/2016.
 */

public class WheelOperation implements Parcelable {

    public static String msgSuccessTag="SUCCESS";

    private String[] availablePrizes;
    private String msg;
    private int winningPrizeId;

    public String[] getAvailablePrizes() {
        return availablePrizes;
    }

    public void setAvailablePrizes(String[] availablePrizes) {
        this.availablePrizes = availablePrizes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getWinningPrizeId() {
        return winningPrizeId;
    }

    public void setWinningPrizeId(int winningPrizeId) {
        this.winningPrizeId = winningPrizeId;
    }

    protected WheelOperation(Parcel in) {
        msg = in.readString();
        winningPrizeId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeInt(winningPrizeId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WheelOperation> CREATOR = new Parcelable.Creator<WheelOperation>() {
        @Override
        public WheelOperation createFromParcel(Parcel in) {
            return new WheelOperation(in);
        }

        @Override
        public WheelOperation[] newArray(int size) {
            return new WheelOperation[size];
        }
    };
}