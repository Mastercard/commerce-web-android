package com.us.masterpass.merchantapp.data.device;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Payment method object stored by Merchant
 */

public class MerchantPaymentMethod implements Parcelable {
    private byte[] paymentMethodLogo;
    private String paymentMethodId;
    private String paymentMethodName;
    private String paymentMethodLastFourDigits;
    private String pairingTransactionId;

    public MerchantPaymentMethod(byte[] paymentMethodLogo, String paymentMethodId,
                                 String paymentMethodName, String paymentMethodLastFourDigits,
        String pairingTransactionId) {
        this.paymentMethodLogo = paymentMethodLogo;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.paymentMethodLastFourDigits = paymentMethodLastFourDigits;
        this.pairingTransactionId = pairingTransactionId;
    }

    protected MerchantPaymentMethod(Parcel in) {
        paymentMethodLogo = in.createByteArray();
        paymentMethodId = in.readString();
        paymentMethodName = in.readString();
        paymentMethodLastFourDigits = in.readString();
        pairingTransactionId = in.readString();
    }

    public static final Creator<MerchantPaymentMethod> CREATOR =
            new Creator<MerchantPaymentMethod>() {
                @Override
                public MerchantPaymentMethod createFromParcel(Parcel in) {
                    return new MerchantPaymentMethod(in);
                }

                @Override
                public MerchantPaymentMethod[] newArray(int size) {
                    return new MerchantPaymentMethod[size];
                }
            };

    public byte[] getPaymentMethodLogo() {
        return paymentMethodLogo;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public String getPaymentMethodLastFourDigits() {
        return paymentMethodLastFourDigits;
    }

    public String getPairingTransactionId(){
        return pairingTransactionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(paymentMethodLogo);
        parcel.writeString(paymentMethodId);
        parcel.writeString(paymentMethodName);
        parcel.writeString(paymentMethodLastFourDigits);
        parcel.writeString(pairingTransactionId);
    }

}
