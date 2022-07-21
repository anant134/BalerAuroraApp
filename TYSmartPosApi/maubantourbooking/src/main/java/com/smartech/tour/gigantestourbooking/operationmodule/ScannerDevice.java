package com.smartech.tour.gigantestourbooking.operationmodule;

import android.os.Bundle;

import com.whty.smartpos.tysmartposapi.scanner.ScannerConfig;
import com.whty.smartpos.tysmartposapi.scanner.ScannerConstrants;

/**
 * Created by zengyahui on 2017-10-26.
 */

public class ScannerDevice extends OperationInter {

    public static String scanData;

    @Override
    public String execute(int index) {
        result.delete(0, result.length());
        switch (index) {
            case 0:
                result.append(">>> setScanConfig <<<\n");
                Bundle bundle = new Bundle();
                bundle.putBoolean(ScannerConfig.PREVIEW, true);
                boolean res = smartPosApi.setScanConfig(bundle);
                result.append("result : " + res);
                break;
            case 1:
                scanData = null;
                smartPosApi.startScan(activity, 1, ScannerConstrants.BACK);
                while (scanData == null){
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                result.append(scanData);
                break;
            case 2:
                result.append(">>> stopScan <<<\n");
                smartPosApi.stopScan();
                break;
        }
        return result.toString();
    }
}
