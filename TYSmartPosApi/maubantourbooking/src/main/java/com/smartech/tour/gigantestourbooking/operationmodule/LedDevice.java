package com.smartech.tour.gigantestourbooking.operationmodule;

import com.whty.smartpos.tysmartposapi.led.LedLightConstrants;

/**
 * Created by zengyahui on 2017-10-26.
 */

public class LedDevice extends OperationInter {

    @Override
    public String execute(int index) {
        result.delete(0, result.length());
        switch (index) {
            case 0:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.RED + "\n");
                result.append("param isOn : " + true + "\n");
                boolean res = smartPosApi.setLed(LedLightConstrants.RED, true);
                result.append("result : " + res + "\n");
                break;
            case 1:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.RED + "\n");
                result.append("param isOn : " + false + "\n");
                res = smartPosApi.setLed(LedLightConstrants.RED, false);
                result.append("result : " + res + "\n");
                break;
            case 2:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.GREEN + "\n");
                result.append("param isOn : " + true + "\n");
                res = smartPosApi.setLed(LedLightConstrants.GREEN, true);
                result.append("result : " + res + "\n");
                break;
            case 3:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.GREEN + "\n");
                result.append("param isOn : " + false + "\n");
                res = smartPosApi.setLed(LedLightConstrants.GREEN, false);
                result.append("result : " + res + "\n");
                break;
            case 4:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.YELLOW + "\n");
                result.append("param isOn : " + true + "\n");
                res = smartPosApi.setLed(LedLightConstrants.YELLOW, true);
                result.append("result : " + res + "\n");
                break;
            case 5:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.YELLOW + "\n");
                result.append("param isOn : " + false + "\n");
                res = smartPosApi.setLed(LedLightConstrants.YELLOW, false);
                result.append("result : " + res + "\n");
                break;
            case 6:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.BLUE + "\n");
                result.append("param isOn : " + true + "\n");
                res = smartPosApi.setLed(LedLightConstrants.BLUE, true);
                result.append("result : " + res + "\n");
                break;
            case 7:
                result.append(">>> setLed <<<\n");
                result.append("param light : " + LedLightConstrants.BLUE + "\n");
                result.append("param isOn : " + false + "\n");
                res = smartPosApi.setLed(LedLightConstrants.BLUE, false);
                result.append("result : " + res + "\n");
                break;
            case 8:
                result.append(">>> setLed <<<\n");
                result.append("param light : all" + "\n");
                result.append("param isOn : " + true + "\n");
                res = smartPosApi.setLed(LedLightConstrants.RED, true);
                res = smartPosApi.setLed(LedLightConstrants.GREEN, true);
                res = smartPosApi.setLed(LedLightConstrants.YELLOW, true);
                res = smartPosApi.setLed(LedLightConstrants.BLUE, true);
                result.append("result : " + res + "\n");
                break;
            case 9:
                result.append(">>> setLed <<<\n");
                result.append("param light : all" + "\n");
                result.append("param isOn : " + false + "\n");
                res = smartPosApi.setLed(LedLightConstrants.RED, false);
                res = smartPosApi.setLed(LedLightConstrants.GREEN, false);
                res = smartPosApi.setLed(LedLightConstrants.YELLOW, false);
                res = smartPosApi.setLed(LedLightConstrants.BLUE, false);
                result.append("result : " + res + "\n");
                break;
        }
        return result.toString();
    }
}
