package com.smartech.tour.baleraurora.operationmodule;

import android.os.Bundle;

import com.smartech.tour.baleraurora.MainActivity;
import com.whty.smartpos.tysmartposapi.OperationResult;
import com.whty.smartpos.tysmartposapi.pinpad.PinPadConfig;
import com.whty.smartpos.tysmartposapi.pinpad.PinPadConstrants;
import com.whty.smartpos.tysmartposapi.pinpad.PinResult;
import com.whty.smartpos.utils.GPMethods;

/**
 * Created by zengyahui on 2017-10-26.
 */

public class PinPadDevice extends OperationInter {

    public static PinResult pinResult;
    private String random;

    @Override
    public String execute(int index) {
        result.delete(0, result.length());
        switch (index) {
            case 0:
                result.append(">>> updateMainKey <<<\n");
                result.append("param key : " + "F616DD76F290635EF616DD76F290635E" + "\n");
                result.append("param check valueupdateMainKey : " + "82E13665" + "\n");
                int res = smartPosApi.updateMainKey("F616DD76F290635EF616DD76F290635E", "82E13665");
                result.append("result : " + res + "\n");
                break;
            case 1:
                result.append(">>> updateWorkKey <<<\n");
                result.append("param pin key : " + "950973182317F80B950973182317F80B" + "\n");
                result.append("param check value : " + "00962B60" + "\n");
                res = smartPosApi.updateWorkKey(PinPadConstrants.PINKEY, "950973182317F80B950973182317F80B", "00962B60");
                result.append("result : " + res + "\n");
                result.append("param td key : " + "950973182317F80B950973182317F80B" + "\n");
                result.append("param check value : " + "00962B60" + "\n");
                smartPosApi.updateWorkKey(PinPadConstrants.TDKEY, "950973182317F80B950973182317F80B", "00962B60");
                result.append("result : " + res + "\n");
                result.append("param mac key : " + "950973182317F80B950973182317F80B" + "\n");
                result.append("param check value : " + "00962B60" + "\n");
                smartPosApi.updateWorkKey(PinPadConstrants.MACKEY, "950973182317F80B950973182317F80B", "00962B60");
                result.append("result : " + res + "\n");
                break;
            case 2:
                result.append(">>> updateTransKey <<<\n");
                result.append("param trans key : " + "383933333663626233646131343162376263626166373162" + "\n");
                res = smartPosApi.updateTransKey("383933333663626233646131343162376263626166373162");
                result.append("result : " + res + "\n");
                break;
            case 3:
                result.append(">>> calculateMac <<<\n");
                result.append("param data : " + "0000000000000000" + "\n");
                result.append("param cal mode : " + 0 + "\n");
                OperationResult operationResult = smartPosApi.calculateMac(GPMethods.str2bytes("0000000000000000"), 0);
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                result.append("result data : " + operationResult.getData() + "\n");
                break;
            case 4:
                result.append(">>> getRandom <<<\n");
                operationResult = smartPosApi.getRandom();
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                result.append("result data : " + operationResult.getData() + "\n");
                break;
            case 5:
                result.append(">>> setPinPadConfig <<<\n");
                Bundle bundle = new Bundle();
//                bundle.putString(PinPadConfig.CARD_NUM, "6241662312653212");
                bundle.putString(PinPadConfig.AMOUNT, "1");
                bundle.putString(PinPadConfig.TIMEOUT, "60");
                bundle.putString(PinPadConfig.MIN_LEN, "4");
                bundle.putString(PinPadConfig.MAX_LEN, "6");
                bundle.putBoolean(PinPadConfig.WITH_SOUND, true);
                bundle.putBoolean(PinPadConfig.WITH_DISPLAY_BAR, true);
                boolean ret = smartPosApi.setPinPadConfig(bundle);
                result.append("result : " + ret + "\n");
                break;
            case 6:
                result.append(">>> startPinPad <<<\n");
                pinResult = null;
                smartPosApi.startPinPad(MainActivity.mContext, 0);
                while (pinResult == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                result.append("result resultCode: " + pinResult.getResultCode() + "\n");
                result.append("result pinblock: " + pinResult.getPinblock() + "\n");
                break;
            case 7:
                result.append(">>> cancelPinPad <<<\n");
                smartPosApi.startPinPad(activity, 0);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                smartPosApi.cancelPinPad();
                break;
            case 8:
                result.append(">>> selectKeyGroup <<<\n");
                result.append("param index : " + 0 + "\n");
                res = smartPosApi.selectKeyGroup(0);
                result.append("result : " + res + "\n");
                break;
            case 9:
                result.append(">>> encryptData <<<\n");
                result.append("param keyId : " + PinPadConstrants.KEY_ID_FIRST_PINKEY + "\n");
                result.append("param data : " + "12345678" + "\n");
                result.append("param calMode : " + PinPadConstrants.ENCRYPT + "\n");
                operationResult = smartPosApi.encryptData(PinPadConstrants.KEY_ID_FIRST_PINKEY, GPMethods.str2bytes("1234567887654321"), PinPadConstrants.ENCRYPT);
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                result.append("result data : " + operationResult.getData() + "\n");
                break;
            case 10:
                result.append(">>> encryptKSN <<<\n");
                operationResult = smartPosApi.getDeviceKSN();
                operationResult = smartPosApi.encryptKSN(operationResult.getData(), "12345678");
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                result.append("result data : " + operationResult.getData() + "\n");
                break;
            case 11:
                result.append(">>> DUKPT getRandom <<<\n");
                operationResult = smartPosApi.dukptUpdateKSN(PinPadConstrants.DUKPT_RANDOM);
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                result.append("result data : " + operationResult.getData() + "\n");
                random = operationResult.getData();
                random = GPMethods.desecb("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", random, 0);
                break;
            case 12:
                result.append(">>> DUKPT identifyData <<<\n");
                operationResult = smartPosApi.dukptUpdateKSN(PinPadConstrants.DUKPT_VERIFY, random + "FFFF9876543210E00001");
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                break;
            case 13:
                result.append(">>> DUKPT increaceKSN <<<\n");
                operationResult = smartPosApi.dukptUpdateKSN(PinPadConstrants.DUKPT_INCREMENT);
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                break;
            case 14:
                result.append(">>> DUKPT updateKSN <<<\n");
                operationResult = smartPosApi.dukptUpdateKSN(PinPadConstrants.KSN_UPDATE, "FFFF9876543210E00001");
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                break;
            case 15:
                result.append(">>> DUKPT getKSN <<<\n");
                operationResult = smartPosApi.dukptUpdateKSN(PinPadConstrants.GET_KSN);
                result.append("result status code : " + operationResult.getStatusCode() + "\n");
                result.append("result data : " + operationResult.getData() + "\n");
                break;
            case 16:
                result.append(">>> DUKPT UpdateIPEK <<<\n");
                res = smartPosApi.dukptUpdateIPEK("F616DD76F290635EF616DD76F290635E82E13665");
                result.append("result status code : " + res + "\n");
                break;
        }
        return result.toString();
    }
}
