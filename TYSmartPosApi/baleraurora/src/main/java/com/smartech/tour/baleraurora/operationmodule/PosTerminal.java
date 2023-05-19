package com.smartech.tour.baleraurora.operationmodule;

import android.util.Log;

import com.whty.smartpos.tysmartposapi.OperationResult;
import com.whty.smartpos.tysmartposapi.pos.DeviceInfo;
import com.whty.smartpos.tysmartposapi.pos.DeviceVersion;
import com.whty.smartpos.tysmartposapi.pos.deviceupdate.HardwareUpgradeListener;

import java.io.InputStream;

/**
 * Created by zengyahui on 2017-10-26.
 */

public class PosTerminal extends OperationInter {

    @Override
    public String execute(int index) {
        result.delete(0, result.length());
        switch (index) {
            case 0:
                String sn = smartPosApi.getDeviceSN();
                result.append(sn);
                break;
            case 1:
                //result.append(">>> getDevicePN <<<\n");
                OperationResult operationResult = smartPosApi.getDevicePN();
                result.append(operationResult.getData());
                /*result.append("status code : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");*/
                break;
            case 2:
                /*result.append(">>> getDeviceKSN <<<\n");
                operationResult = smartPosApi.getDeviceKSN();
                result.append("status code : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");*/
                OperationResult operationResultskn = smartPosApi.getDeviceKSN();

                result.append(operationResultskn.getData());
                break;
            case 3:
                result.append(">>> getDeviceVersion <<<\n");
                DeviceVersion deviceVersion = smartPosApi.getDeviceVersion();
                result.append("android version : " + deviceVersion.getAndroidVersion() + "\n");
                result.append("custom version : " + deviceVersion.getCustomVersion() + "\n");
                result.append("posservice version : " + deviceVersion.getPosServiceVersion() + "\n");
                result.append("boot version : " + deviceVersion.getBootVerion() + "\n");
                result.append("soft version : " + deviceVersion.getSoftVersion() + "\n");
                result.append("sub version : " + deviceVersion.getSubVersion() + "\n");
                break;
            case 4:
                result.append(">>> getDeviceInfo <<<\n");
                DeviceInfo deviceInfo = smartPosApi.getDeviceInfo();
                result.append("vendor : " + deviceInfo.getVendor() + "\n");
                result.append("model : " + deviceInfo.getModel() + "\n");
                break;
            case 5:
                result.append(">>> getMerchantAndTerminalNo <<<\n");
                String[] data = smartPosApi.getMerchantAndTerminalNo();
                if (data != null && data.length > 0) {
                    result.append("merchant number : " + data[0] + "\n");
                    result.append("terminal number : " + data[1] + "\n");
                } else {
                    result.append("getMerchantAndTerminalNo error " + data + "\n");
                }
                break;
            case 6:
                result.append(">>> updateMerchantAndTerminalNo <<<\n");
                result.append("param merchant number : " + "123456789123456" + "\n");
                result.append("param terminal number : " + "12345678" + "\n");
                int res = smartPosApi.updateMerchantAndTerminalNo("123456789123456", "12345678");
                result.append("result : " + res + "\n");
                break;
            case 7:
                result.append(">>> getBatchAndFlowNo <<<\n");
                data = smartPosApi.getBatchAndFlowNo();
                if (data != null && data.length > 0) {
                    result.append("batch number : " + data[0] + "\n");
                    result.append("flow number : " + data[1] + "\n");
                } else {
                    result.append("getBatchAndFlowNo error " + data + "\n");
                }
                break;
            case 8:
                result.append(">>> updateMerchantAndTerminalNo <<<\n");
                result.append("param batch number : " + "000001" + "\n");
                result.append("param flow number : " + "123456" + "\n");
                res = smartPosApi.updateBatchAndFlowNo("000001", "123456");
                result.append("result : " + res + "\n");
                break;
            case 9:
                result.append(">>> installApp <<<\n");
                result.append("param app name : " + "HttpsTest" + "\n");
                boolean ret = smartPosApi.installApp("/system/data/HttpsTest.apk");
                result.append("result : " + ret + "\n");
                break;
            case 10:
                result.append(">>> uninstallApp <<<\n");
                result.append("param package name : " + "com.whty.smartpos.httpstest" + "\n");
                ret = smartPosApi.uninstallApp("com.whty.smartpos.httpstest");
                result.append("result : " + ret + "\n");
                break;
            case 11:
                result.append(">>> upgradeDevice <<<\n");
                smartPosApi.transceive("F08A0000734859594aF0F1A6EBFFAABBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000100000101010102010203049F269F279F109F379F360095009A009C9F025F2A00829F1A9F039F339F349F359F1E00849F099F41FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0701009c5c");
                byte[] mainFile = getFromAssets("pkg_p20lX_V01.00.00.R11208_0_0.dat");
                byte[] childFile = getFromAssets("SubApp00_Release_V1.00.00.R11343__20190423_1355_NoPuk.sig");
                smartPosApi.upgradeDevice(mainFile, childFile, new MyHardwareUpgradeListener());
                break;
            case 12:
                result.append(">>> setSystemClock <<<\n");
                ret = smartPosApi.setSystemClock("20080808080808");
                result.append("result : " + ret + "\n");
                break;
            case 13:
                try {
                    result.append(">>> reboot <<<\n");
                    smartPosApi.reboot();
                } catch (Exception e) {
                    result.append(e.getMessage() + "\n");
                }
                break;
            case 14:
                try {
                    result.append(">>> setAPN <<<\n");
                    ret = smartPosApi.setAPN("天喻专用APN", "tianyu", "", "");
                    result.append("result : " + ret + "\n");
                } catch (Exception e) {
                    result.append(e.getMessage() + "\n");
                }
                break;
            case 15:
                result.append(">>> writeCustomData <<<\n");
                result.append("param positon : " + 0 + "\n");
                result.append("param data : " + "123456" + "\n");
                res = smartPosApi.writeCustomData(0, "123456");
                result.append("result : " + res + "\n");
                break;
            case 16:
                result.append(">>> readCustomData <<<\n");
                result.append("param positon : " + 0 + "\n");
                operationResult = smartPosApi.readCustomData(0);
                result.append("status code : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");
                break;
            case 17:
                result.append(">>> transceive <<<\n");
                result.append("param cmd : " + "fe01010600" + "\n");
                operationResult = smartPosApi.transceive("fe01010600");
                result.append("status code : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");
                break;
        }
        return result.toString();
    }


    //从assets 文件夹中获取文件并读取数据
    public byte[] getFromAssets(String fileName) {
        byte[] result = null;
        try {
            InputStream in = activity.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            result = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    class MyHardwareUpgradeListener implements HardwareUpgradeListener {

        @Override
        public void upgradeDeviceSuccess() {
            Log.d("HardwareUpgradeListener", "upgradeDeviceSuccess in");
        }

        @Override
        public void upgradeDeviceStart(byte type) {
            Log.d("HardwareUpgradeListener", "upgradeDeviceStart in");
        }

        @Override
        public void showProgress(byte type, int progressValue) {
            Log.d("HardwareUpgradeListener", "showProgress in " + progressValue);
        }

        @Override
        public void upgradeFail(int errorCode) {
            Log.d("HardwareUpgradeListener", "upgradeFail in " + errorCode);
        }

        @Override
        public void showVersion(boolean mainFlag, String currentVersion, String targetVersion) {
            Log.d("HardwareUpgradeListener", "showVersion in");
        }

        @Override
        public void error(String msg) {
            Log.d("HardwareUpgradeListener", "error in " + msg);
        }

        @Override
        public void updateMainTime(long time1, long time2, long time3) {
            Log.d("HardwareUpgradeListener", "updateMainTime in ");
        }

        @Override
        public void updateChildTime(long time) {
            Log.d("HardwareUpgradeListener", "updateChildTime in ");
        }

        @Override
        public void isUpdate(byte type, boolean isSuccess) {
            Log.d("HardwareUpgradeListener", "isUpdate in " + isSuccess);
        }
    }
}
