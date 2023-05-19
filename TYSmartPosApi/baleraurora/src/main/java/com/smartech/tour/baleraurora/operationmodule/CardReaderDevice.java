package com.smartech.tour.baleraurora.operationmodule;

import com.whty.smartpos.tysmartposapi.OperationResult;
import com.whty.smartpos.tysmartposapi.cardreader.CardInfo;
import com.whty.smartpos.tysmartposapi.cardreader.CardReaderConstrants;
import com.whty.smartpos.utils.GPMethods;

/**
 * Created by zengyahui on 2017-10-26.
 */

public class CardReaderDevice extends OperationInter {

    @Override
    public String execute(int index) {
        result.delete(0, result.length());
        switch (index) {
            case 0:
                result.append(">>> setReadCardConfig <<<\n");
                result.append("param msr : " + true + "\n");
                result.append("param ic : " + true + "\n");
                result.append("param contactlessIc : " + true + "\n");
                smartPosApi.setReadCardConfig(true, true, true);
                break;
            case 1:
               /* result.append(">>> readCard <<<\n");
                result.append("param amount : " + "1" + "\n");
                result.append("param tradeTime : " + "20171026101010" + "\n");
                result.append("param tradeType : " + 0 + "\n");
                result.append("param swipeTimeout : " + 60 + "\n");*/
                CardInfo cardInfo = smartPosApi.readCard("1", "20171026101010", (byte) 0, (byte) 60);
                smartPosApi.startSearchCard(true, true, true, 60);
                OperationResult ss= smartPosApi.transceive("f0c3000300");
                if(ss.getStatusCode()==0){
                    result.append(ss.getData());
                }else{
                    result.append("");
                }

                break;
            case 2:
                result.append(">>> tradeResponse <<<\n");
                int res = smartPosApi.tradeResponse("", "");
                result.append("result : " + res + "\n");
                break;
            case 3:
                result.append(">>> cancelReadCard <<<\n");
                boolean rec = smartPosApi.cancelReadCard();
                result.append("result : " + rec + "\n");
                break;
            case 4:
                result.append(">>> startSearchCard <<<\n");
                res = smartPosApi.startSearchCard(true, true, true, 60);
                result.append("card type : " + res + "\n");
                break;
            case 5:
                result.append(">>> stopSearchCard <<<\n");
                res = smartPosApi.stopSearchCard();
                result.append("resut : " + res + "\n");
                break;
            case 6:
                result.append(">>> getMagCardData <<<\n");
                cardInfo = smartPosApi.getMagCardData();
                result.append("card information : " + cardInfo.toString() + "\n");
                break;
            case 7:
                result.append(">>> powerOn <<<\n");
                OperationResult operationResult = smartPosApi.powerOn();
                result.append("status : " + operationResult.getStatusCode() + "\n");
                result.append("atr : " + operationResult.getData() + "\n");
                break;
            case 8:
                result.append(">>> powerOff <<<\n");
                res = smartPosApi.powerOff();
                result.append("result : " + res + "\n");
                break;
            case 9:
                result.append(">>> active <<<\n");
                res = smartPosApi.active();
                result.append("result : " + res + "\n");
                break;
            case 10:
                result.append(">>> halt <<<\n");
                res = smartPosApi.halt();
                result.append("result : " + res + "\n");
                break;
            case 11:
                result.append(">>> isCardExists <<<\n");
                res = smartPosApi.isCardExists();
                result.append("isCardExists : " + res + "\n");
                break;
            case 12:
                result.append(">>> transmitIC <<<\n");
                operationResult = smartPosApi.transmitIC(new byte[]{
                        0x00, (byte) 0xa4, 0x04, 0x00, 0x08, (byte) 0xa0, 0x00, 0x00, 0x03, 0x33, 0x01, 0x01, 0x02});
                result.append("status : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");
                break;
            case 13:
                result.append(">>> transmitRF <<<\n");
                operationResult = smartPosApi.transmitRF(new byte[]{
                        0x00, (byte) 0xa4, 0x04, 0x00, 0x0e, 0x32, 0x50, 0x41, 0x59, 0x2e, 0x53, 0x59, 0x53, 0x2e, 0x44, 0x44, 0x46, 0x30, 0x31});
                result.append("status : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");
                break;
            case 14:
                result.append(">>> openPSAMCard <<<\n");
                result.append("param id : " + CardReaderConstrants.PSAM_CARD_2 + "\n");
                operationResult = smartPosApi.openPSAMCard(CardReaderConstrants.PSAM_CARD_2);
                result.append("status : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");
                break;
            case 15:
                result.append(">>> closePSAMCard <<<\n");
                result.append("param id : " + CardReaderConstrants.PSAM_CARD_2 + "\n");
                res = smartPosApi.closePSAMCard(CardReaderConstrants.PSAM_CARD_2);
                result.append("result : " + res + "\n");
                break;
            case 16:
                result.append(">>> transmitPSAM <<<\n");
                result.append("param id : " + CardReaderConstrants.PSAM_CARD_2 + "\n");
                operationResult = smartPosApi.transmitPSAM(CardReaderConstrants.PSAM_CARD_2, new byte[]{0x00, 0x00, 0x00});
                result.append("status : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");
                break;
            case 17:
                result.append(">>> verifyKeyA <<<\n");
                res = smartPosApi.verifyKeyA(0x02, new byte[]{
                        (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06
                });
                result.append("result : " + res + "\n");
                break;
            case 18:
                result.append(">>> verifyKeyB <<<\n");
                res = smartPosApi.verifyKeyB(0x02, new byte[]{
                        (byte) 0xf1, (byte) 0xf2, (byte) 0xf3, (byte) 0xf4, (byte) 0xf5, (byte) 0xf6
                });
                result.append("result : " + res + "\n");
                break;
            case 19:
                result.append(">>> readBlock <<<\n");
                operationResult = smartPosApi.readBlock(0x00, 0x01);
                result.append("status : " + operationResult.getStatusCode() + "\n");
                result.append("data : " + operationResult.getData() + "\n");
                break;
            case 20:
                result.append(">>> writeBlock <<<\n");
                res = smartPosApi.writeBlock(0x00, 0x01, new byte[]{(byte) 0x00, 0x00, 0x01, 0x23, 0x00, 0x00, 0x01, 0x23, 0x00, 0x00, 0x01, 0x23, 0x00, 0x00, 0x01, 0x23});
                result.append("result : " + res + "\n");
                break;
            case 21:
                result.append(">>> readValue <<<\n");
                byte[] money = smartPosApi.readValue(0x00, 0x01);
                result.append("money : " + GPMethods.bytesToHexString(money) + "\n");
                break;
            case 22:
                result.append(">>> writeValue <<<\n");
                money = new byte[]{0x00, 0x00, 0x01, 0x23};
                res = smartPosApi.writeValue(0x00, 0x01, money);
                result.append("result : " + res + "\n");
                break;
            case 23:
                result.append(">>> increaseValue <<<\n");
                res = smartPosApi.increaseValue(0x00, 0x01, 0xff);
                result.append("result : " + res + "\n");
                break;
            case 24:
                result.append(">>> decreaseValue <<<\n");
                res = smartPosApi.decreaseValue(0x00, 0x01, 0xff);
                result.append("result : " + res + "\n");
                break;
            case 25:
                result.append(">>> readMifareUltralightCard <<<\n");
                smartPosApi.readMifareUltralightCard(0);
                break;
            case 26:
                result.append(">>> writeMifareUltralightCard <<<\n");
                smartPosApi.writeMifareUltralightCard(0, null);
                break;
        }
        return result.toString();
    }
}
