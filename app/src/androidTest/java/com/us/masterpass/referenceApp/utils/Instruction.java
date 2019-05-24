package com.us.masterpass.referenceApp.utils;

import android.os.Bundle;

public abstract class Instruction {

    private Bundle dataContainer = new Bundle();

    public final void setData(Bundle dataContainer) {
        this.dataContainer = dataContainer;
    }

    public final Bundle getDataContainer() {
        return dataContainer;
    }

    public abstract String getDescription();

    public abstract boolean checkCondition();
}
