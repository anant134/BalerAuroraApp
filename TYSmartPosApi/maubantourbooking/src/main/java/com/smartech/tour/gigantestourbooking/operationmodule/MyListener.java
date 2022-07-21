package com.smartech.tour.gigantestourbooking.operationmodule;

import android.util.Log;

import com.whty.smartpos.service.Balance;
import com.whty.smartpos.service.EMVCardLog;
import com.whty.smartpos.service.EMVTermConfig;
import com.whty.smartpos.tysmartposapi.ISmartPosListener;
import com.whty.smartpos.tysmartposapi.OperationResult;
import com.whty.smartpos.tysmartposapi.cardreader.CardInfo;
import com.whty.smartpos.tysmartposapi.pinpad.PinResult;
import com.whty.smartpos.tysmartposapi.pos.DeviceInfo;
import com.whty.smartpos.tysmartposapi.pos.DeviceVersion;
import com.whty.smartpos.tysmartposapi.scanner.ScanResult;

import java.util.List;

/**
 * Created by zengyahui on 2017-11-2.
 */

public class MyListener implements ISmartPosListener {

    @Override
    public void onSetLed(int light, boolean isOn) {

    }

    @Override
    public void onGetDeviceSN(String deviceSn) {

    }

    @Override
    public void onGetDevicePN(OperationResult operationResult) {

    }

    @Override
    public void onGetDeviceKSN(OperationResult operationResult) {

    }

    @Override
    public void onGetDeviceVersion(DeviceVersion deviceVersion) {

    }

    @Override
    public void onGetDeviceInfo(DeviceInfo deviceInfo) {

    }

    @Override
    public void onSetTermConfig(boolean isSuccess) {

    }

    @Override
    public void onGetTermConfig(EMVTermConfig emvTermConfig) {

    }

    @Override
    public void onGetMerchantAndTerminalNo(String[] data) {

    }

    @Override
    public void onUpdateMerchantAndTerminalNo(int result) {

    }

    @Override
    public void onGetBatchAndFlowNo(String[] data) {

    }

    @Override
    public void onUpdateBatchAndFlowNo(int result) {

    }

    @Override
    public void onInstallApp(boolean isSuccess) {

    }

    @Override
    public void onUninstallApp(boolean isSuccess) {

    }

    @Override
    public void onSetSystemClock(boolean isSuccess) {

    }

    @Override
    public void onDisableStatusBar(boolean isSuccess) {

    }

    @Override
    public void onSetAPN(boolean isSuccess) {

    }

    @Override
    public void onWriteCustomData(int result) {

    }

    @Override
    public void onReadCustomData(OperationResult operationResult) {

    }

    @Override
    public void onTransceive(OperationResult operationResult) {

    }

    @Override
    public void onReadCardType(CardInfo cardInfo) {

    }

    @Override
    public void onReadCard(CardInfo cardInfo) {

    }

    @Override
    public void onCancelReadCard(boolean isSuccess) {

    }

    @Override
    public void onStartSearchCard(int result) {

    }

    @Override
    public void onStopSearchCard(int result) {

    }

    @Override
    public void onGetMagCardData(CardInfo cardInfo) {

    }

    @Override
    public void onPowerOn(OperationResult operationResult) {

    }

    @Override
    public void onPowerOff(int result) {

    }

    @Override
    public void onHalt(int result) {

    }

    @Override
    public void onActive(int result) {

    }

    @Override
    public void onIsCardExists(int result) {

    }

    @Override
    public void onTransmitIC(OperationResult operationResult) {

    }

    @Override
    public void onTransmitRF(OperationResult operationResult) {

    }

    @Override
    public void onTransmitPSAM(OperationResult operationResult) {

    }

    @Override
    public void onOpenPSAMCard(OperationResult operationResult) {

    }

    @Override
    public void onClosePSAMCard(int result) {

    }

    @Override
    public void onVerifyKeyA(int i) {

    }

    @Override
    public void onVerifyKeyB(int i) {

    }

    @Override
    public void onReadBlock(OperationResult operationResult) {

    }

    @Override
    public void onWriteBlock(int i) {

    }

    @Override
    public void onWriteValue(int i) {

    }

    @Override
    public void onReadValue(byte[] money) {

    }

    @Override
    public void onIncreaseValue(int i) {

    }

    @Override
    public void onReadMifareUltralightCard(OperationResult operationResult) {

    }

    @Override
    public void onWriteMifareUltralightCard(int i) {

    }

    @Override
    public void onTradeResponse(int result) {

    }

    @Override
    public void onClearAID(boolean isSuccess) {

    }

    @Override
    public void onClearCAPK(boolean isSuccess) {

    }

    @Override
    public void onUpdateAID(boolean isSuccess) {

    }

    @Override
    public void onUpdateCAPK(boolean isSuccess) {

    }

    @Override
    public void onGetAID(List<byte[]> aidList) {

    }

    @Override
    public void onGetCAPK(List<byte[]> capkList) {

    }

    @Override
    public void onGetTlv(byte[] tlv) {

    }

    @Override
    public void onGetTlvValue(byte[] value) {

    }

    @Override
    public void onGetTlvList(byte[] list) {

    }

    @Override
    public void onSetTlv(int result) {

    }

    @Override
    public void onQueryECBalance(Balance balance) {

    }

    @Override
    public void onGetEmvCardLog(List<EMVCardLog> emvCardLogList) {

    }

    @Override
    public void onGetTlvEncrypted(String data) {

    }

    @Override
    public void onGetTlvValueEncrypted(String data) {

    }

    @Override
    public void onGetTlvListEncrypted(String data) {

    }

    @Override
    public void onSelectKeyGroup(int result) {

    }

    @Override
    public void onUpdateMainKey(int result) {

    }

    @Override
    public void onUpdateWorkKey(int result) {

    }

    @Override
    public void onUpdateTransKey(int result) {

    }

    @Override
    public void onCalculateMac(OperationResult operationResult) {

    }

    @Override
    public void onGetRandom(OperationResult operationResult) {

    }

    @Override
    public void onEncryptData(OperationResult operationResult) {

    }

    @Override
    public void onEncryptKSN(OperationResult operationResult) {

    }

    @Override
    public void onKeyDown(int count) {
        Log.d("MyListener", "number count :" + count);
    }

    @Override
    public void onGetPinResult(PinResult pinResult) {
        PinPadDevice.pinResult = pinResult;
    }

    @Override
    public void onDukptUpdateKSN(int result) {

    }

    @Override
    public void onDukptUpdateIPEK(int result) {

    }

    @Override
    public void onInitPrinter(boolean isSuccess) {

    }

    @Override
    public void onSetPrintParameters(boolean isSuccess) {

    }

    @Override
    public void onSetYSpace(boolean isSuccess) {

    }

    @Override
    public void onGetStatus(int status) {

    }

    @Override
    public void onScanResult(ScanResult scanResult) {

    }
}
