package com.smartech.tour.baleraurora.operationmodule;

import android.os.Bundle;
import android.util.Log;

import com.whty.smartpos.service.Balance;
import com.whty.smartpos.service.EMVCardLog;
import com.whty.smartpos.service.EMVTransData;
import com.whty.smartpos.service.OnlineResult;
import com.whty.smartpos.service.PINResult;
import com.whty.smartpos.tysmartposapi.emv.EMVConstrants;
import com.whty.smartpos.tysmartposapi.emv.EMVTransListener;
import com.whty.smartpos.tysmartposapi.pinpad.PinPadConfig;
import com.whty.smartpos.tysmartposapi.pinpad.PinPadConstrants;
import com.whty.smartpos.tysmartposapi.pos.logutil.TYLog;
import com.whty.smartpos.utils.GPMethods;

import java.util.List;

/**
 * Created by zengyahui on 2017-10-26.
 */

public class EMVDevice extends OperationInter {

    @Override
    public String execute(int index) {
        result.delete(0, result.length());
        switch (index) {
            case 0:
                result.append(">>> setTermConfig <<<\n");
                result.append("param emvTermConfig : null\n");
//                smartPosApi.setTermConfig(null);
                break;
            case 1:
                result.append(">>> getTermConfig <<<\n");
//                EMVTermConfig emvTermConfig = smartPosApi.getTermConfig();
//                if (emvTermConfig == null) {
                result.append("getTermConfig error\n");
//                } else {
//                    result.append("emvTermConfig : " + emvTermConfig.toString() + "\n");
//                }
                break;
            case 2:
                result.append(">>> clearAID <<<\n");
                boolean res = smartPosApi.clearAID();
                result.append("result : " + res + "\n");
                break;
            case 3:
                result.append(">>> updateAID <<<\n");
                res = smartPosApi.updateAID(GPMethods.str2bytes("9f0608a000000333010103df0101009f08020020df1105d84000a800df1205d84004f800df130500100000009f1b0400000000df150400000000df160199df170199df14039f3704df1801019f7b06000000000000df1906000000000000df2006000999999999df2106000999999999"));
                result.append("result : " + res + "\n");
                break;
            case 4:
                result.append(">>> getAID <<<\n");
                List<byte[]> list = smartPosApi.getAID();
                result.append("aid count : " + list.size() + "\n");
                break;
            case 5:
                result.append(">>> clearCAPK <<<\n");
                res = smartPosApi.clearCAPK();
                result.append("result : " + res + "\n");
                break;
            case 6:
                result.append(">>> updateCAPK <<<\n");
                res = smartPosApi.updateCAPK(GPMethods.str2bytes("9f0605a0000003339f22010adf05083230333031323330df060101df070101df028180b2ab1b6e9ac55a75adfd5bbc34490e53c4c3381f34e60e7fac21cc2b26dd34462b64a6fae2495ed1dd383b8138bea100ff9b7a111817e7b9869a9742b19e5c9dac56f8b8827f11b05a08eccf9e8d5e85b0f7cfa644eff3e9b796688f38e006deb21e101c01028903a06023ac5aab8635f8e307a53ac742bdce6a283f585f48efdf040103df0314c88be6b2417c4f941c9371ea35a377158767e4e3"));
                result.append("result : " + res + "\n");
                break;
            case 7:
                result.append(">>> getCAPK <<<\n");
                list = smartPosApi.getCAPK();
                result.append("capk count : " + list.size() + "\n");
                break;
            case 8:
                result.append(">>> setTlv <<<\n");
                result.append("param tag : " + "0x9f02\n");
                result.append("param value:" + "\n");
                int ret = smartPosApi.setTlv(0x9f02, new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x01});
                result.append("result : " + ret + "\n");
                break;
            case 9:
                result.append(">>> getTlv <<<\n");
                result.append("param tag : " + "0x0057\n");
                byte[] value = smartPosApi.getTlv(0x0057);
                result.append("value : " + GPMethods.bytesToHexString(value) + "\n");
                break;
            case 10:
                result.append(">>> getTlvList <<<\n");
                value = smartPosApi.getTlvList(new int[]{
                        0x9f26, 0x9f37, 0x9f36, 0x0082, 0x9f10,
                        0x0095, 0x9f33, 0x9f02, 0x009a, 0x009c,
                        0x5f2a, 0x9f27, 0x9f1a, 0x9f03
                });
                result.append("value : " + GPMethods.bytesToHexString(value) + "\n");
                break;
            case 11:
                result.append(">>> emvProcess <<<\n");
                EMVTransData emvTransData = new EMVTransData();
                emvTransData.setAmount(1);
                emvTransData.setTransType((byte) 0x00);
                emvTransData.setTransDate("20171230");
                emvTransData.setTransTime("103211");
                emvTransData.setSupportSM(false);
                emvTransData.setCardAuth(false);
                emvTransData.setForceOnline(true);
                emvTransData.setSupportEC(false);
                emvTransData.setSupportCVM(true);
                emvTransData.setFlow(EMVConstrants.STANDARD_PBOC);
                emvTransData.setChannelType(EMVConstrants.CONTACT);
                result.append("param emvTransData : " + emvTransData.toString() + "\n");
                smartPosApi.startSearchCard(true, true, true, 60);
                smartPosApi.emvProcess(emvTransData, new MyEmvListener());
                break;
            case 12:
                result.append(">>> queryECBalance <<<\n");
                smartPosApi.startSearchCard(true, true, true, 60);
                Balance balance = smartPosApi.queryECBalance(EMVConstrants.CONTACTLESS);
                smartPosApi.stopSearchCard();
                result.append("first currency code : " + balance.getFirstCurrencyCode() + "\n");
                result.append("first currency balance : " + balance.getFirstCurrencyBalance() + "\n");
                result.append("second currency code : " + balance.getSecondCurrencyCode() + "\n");
                result.append("second currency balance : " + balance.getSecondCurrencyBalance() + "\n");
                break;
            case 13:
                result.append(">>> getEmvCardLog <<<\n");
                smartPosApi.startSearchCard(true, true, true, 60);
                List<EMVCardLog> logs = smartPosApi.getEmvCardLog(EMVConstrants.CONTACTLESS, new MyEmvListener());
                smartPosApi.stopSearchCard();
                result.append("emvlog count : " + logs.size() + "\n");
                break;
            case 14:
                result.append(">>> getTlvEncrypted <<<\n");
                result.append("param tag : " + "0x0057\n");
                result.append("param keyId : " + PinPadConstrants.DUKPT_DAT_KEY_REQ + "\n");
                String data = smartPosApi.getTlvEncrypted(0x0057, PinPadConstrants.DUKPT_DAT_KEY_REQ);
                result.append("value : " + data + "\n");
                break;
            case 15:
                result.append(">>> getTlvValueEncrypted <<<\n");
                result.append("param tag : " + "0x0057\n");
                result.append("param keyId : " + PinPadConstrants.DUKPT_DAT_KEY_REQ + "\n");
                data = smartPosApi.getTlvValueEncrypted(0x0057, PinPadConstrants.DUKPT_DAT_KEY_REQ);
                result.append("value : " + data + "\n");
                break;
            case 16:
                result.append(">>> getTlvListEncrypted <<<\n");
                result.append("param keyId : " + PinPadConstrants.DUKPT_DAT_KEY_REQ + "\n");
                data = smartPosApi.getTlvListEncrypted(new int[]{
                        0x9f26, 0x9f37, 0x9f36, 0x0082, 0x9f10, 0x0095, 0x9f33, 0x9f02, 0x009a, 0x009c,
                        0x5f2a, 0x9f27, 0x9f1a, 0x9f03, 0x9F16, 0x5F24, 0x004F, 0x9F34, 0x9F06, 0x9F21,
                        0x9F12, 0x005A, 0x0057, 0x9F4E, 0x0082, 0x008E, 0x5F25, 0x9F07, 0x9F0D, 0x9F0E,
                        0x9F0F, 0x9F39, 0x9F40, 0x009B, 0x0084, 0x5F34, 0x9F09, 0x9F1E, 0x9F35, 0x9F41,
                        0x9F53, 0x5F20, 0x5F30, 0x5F28, 0x9F4C, 0x0050, 0x9F08, 0x9F01, 0x9F15, 0x9F1C
                }, PinPadConstrants.DUKPT_DAT_KEY_REQ);
                result.append("value : " + data + "\n");
                break;
        }
        return result.toString();
    }

    class MyEmvListener implements EMVTransListener {

        private String cardNum;

        @Override
        public int onWaitAppSelect(List<String> appNameList, boolean isFirstSelect) {
            return 1;
        }

        @Override
        public int onConfirmEC() {
            return 0;
        }

        @Override
        public int onConfirmCardNo(String cardNO) {
            cardNum = cardNO;
            return 0;
        }

        @Override
        public int onCertVerfiy(String certType, String certValue) {
            return 0;
        }

        @Override
        public PINResult onCardHolderPwd(boolean isOnlinePin, int offlinePinLeftTimes) {
            Log.i("EMVDevice", "isOnlinePin : " + isOnlinePin);
            Log.i("EMVDevice", "offlinePinLeftTimes : " + offlinePinLeftTimes);
            Bundle bundle = new Bundle();
            bundle.putString(PinPadConfig.CARD_NUM, cardNum);
            bundle.putString(PinPadConfig.AMOUNT, "1");
            bundle.putString(PinPadConfig.TIMEOUT, "60");
            bundle.putString(PinPadConfig.MIN_LEN, "4");
            bundle.putString(PinPadConfig.MAX_LEN, "6");
            bundle.putBoolean(PinPadConfig.WITH_SOUND, true);
            bundle.putBoolean(PinPadConfig.WITH_DISPLAY_BAR, true);
            smartPosApi.setPinPadConfig(bundle);
            PinPadDevice.pinResult = null;
            smartPosApi.startPinPad(activity, 0);
            while (PinPadDevice.pinResult == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            PINResult pinResult = new PINResult();
            pinResult.setResultCode(PinPadDevice.pinResult.getResultCode());
            pinResult.setPindata(GPMethods.str2bytes(PinPadDevice.pinResult.getPinblock()));
            return pinResult;
        }

        @Override
        public OnlineResult onOnlineProc() {
            String track2 = smartPosApi.getTlvValueEncrypted(0x0057, PinPadConstrants.DUKPT_DAT_KEY_REQ, new byte[]{
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
            });
            byte[] cardSequence = smartPosApi.getTlvValue(0x5f34);
            byte[] cardNo = smartPosApi.getTlvValue(0x005A);
            byte[] name = smartPosApi.getTlvValue(0x5F20);
            String field_55 = smartPosApi.getTlvListEncrypted(new int[]{
                    0x9f26, 0x9f37, 0x9f36, 0x0082, 0x9f10, 0x0095, 0x9f33, 0x9f02, 0x009a, 0x009c,
                    0x5f2a, 0x9f27, 0x9f1a, 0x9f03, 0x9F16, 0x5F24, 0x004F, 0x9F34, 0x9F06, 0x9F21,
                    0x9F12, 0x005A, 0x0057, 0x9F4E, 0x0082, 0x008E, 0x5F25, 0x9F07, 0x9F0D, 0x9F0E,
                    0x9F0F, 0x9F39, 0x9F40, 0x009B, 0x0084, 0x5F34, 0x9F09, 0x9F1E, 0x9F35, 0x9F41,
                    0x9F53, 0x5F20, 0x5F30, 0x5F28, 0x9F4C, 0x0050, 0x9F08, 0x9F01, 0x9F15, 0x9F1C
            }, PinPadConstrants.DUKPT_DAT_KEY_REQ, new byte[]{
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
            });
            byte[] field_55Clear = smartPosApi.getTlvList(new int[]{
                    0x9f26, 0x9f37, 0x9f36, 0x0082, 0x9f10, 0x0095, 0x9f33, 0x9f02, 0x009a, 0x009c,
                    0x5f2a, 0x9f27, 0x9f1a, 0x9f03, 0x9F16, 0x5F24, 0x004F, 0x9F34, 0x9F06, 0x9F21,
                    0x9F12, 0x005A, 0x0057, 0x9F4E, 0x0082, 0x008E, 0x5F25, 0x9F07, 0x9F0D, 0x9F0E,
                    0x9F0F, 0x9F39, 0x9F40, 0x009B, 0x0084, 0x5F34, 0x9F09, 0x9F1E, 0x9F35, 0x9F41,
                    0x9F53, 0x5F20, 0x5F30, 0x5F28, 0x9F4C, 0x0050, 0x9F08, 0x9F01, 0x9F15, 0x9F1C
            });
            TYLog.i("EMVTransListener", "track2 : " + track2);
            TYLog.i("EMVTransListener", "cardSequence : " + GPMethods.bytesToHexString(cardSequence));
            TYLog.i("EMVTransListener", "cardNo : " + GPMethods.bytesToHexString(cardNo));
            TYLog.i("EMVTransListener", "name : " + GPMethods.bytesToHexString(name));
            TYLog.i("EMVTransListener", "field_55 : " + field_55);
            TYLog.i("EMVTransListener", "field_55Clear : " + GPMethods.bytesToHexString(field_55Clear));

            OnlineResult onlineResult = new OnlineResult();
            onlineResult.setResultCode(0);
            onlineResult.setField39(new byte[]{0x30, 0x30});
            onlineResult.setField55(new byte[]{});
            return onlineResult;
        }

        @Override
        public void onTransResult(int code, final String desc) {
            smartPosApi.stopSearchCard();
            Log.i("EMVDevice", "code : " + code + "\ndesc : " + desc);
        }

        @Override
        public void onOffLinePinVerify(boolean isPinOK) {

        }

        @Override
        public void onOffLinePinLastTimeVerify(int number) {

        }
    }
}
