package com.smartech.tour.gigantestourbooking.operationmodule;

import android.app.Activity;

import com.smartech.tour.gigantestourbooking.MainActivity;
import com.smartech.tour.gigantestourbooking.module.tripData;
import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;

/**
 * Created by zengyahui on 2017-10-26.
 */

public abstract class OperationInter {

    private static OperationInter operation;

    protected static Activity activity;
    protected static tripData tripData;
    public StringBuffer result = new StringBuffer();

    public ITYSmartPosApi smartPosApi = MainActivity.smartPosApi;

    public static OperationInter getDevice(int groupPosition) {
        switch (groupPosition) {
            case 0:
                if (operation == null || !(operation instanceof PosTerminal)) {
                    operation = new PosTerminal();
                }
                break;
            case 1:
                if (operation == null || !(operation instanceof CardReaderDevice)) {
                    operation = new CardReaderDevice();
                }
                break;
            case 2:
                if (operation == null || !(operation instanceof EMVDevice)) {
                    operation = new EMVDevice();
                }
                break;
            case 3:
                if (operation == null || !(operation instanceof PinPadDevice)) {
                    operation = new PinPadDevice();
                }
                break;
            case 4:
                tripData=MainActivity.printtripdata;
                if (operation == null || !(operation instanceof PrinterDevice)) {
                    operation = new PrinterDevice();
                }
                break;
            case 5:
                if (operation == null || !(operation instanceof ScannerDevice)) {
                    operation = new ScannerDevice();
                }
                break;
            case 6:
                if (operation == null || !(operation instanceof LedDevice)) {
                    operation = new LedDevice();
                }
                break;
            case 7:
                if (operation == null || !(operation instanceof BeeperDevice)) {
                    operation = new BeeperDevice();
                }
                break;
        }
        return operation;
    }

    public static void setActivity(Activity activity){
        OperationInter.activity = activity;
    }

    public abstract String execute(int index);
}
