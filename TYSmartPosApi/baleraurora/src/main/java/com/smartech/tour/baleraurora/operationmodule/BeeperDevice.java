package com.smartech.tour.baleraurora.operationmodule;

import com.whty.smartpos.tysmartposapi.beeper.BeepModeConstrants;

/**
 * Created by zengyahui on 2017-10-26.
 */

public class BeeperDevice extends OperationInter {

    @Override
    public String execute(int index) {
        result.delete(0, result.length());
        switch (index) {
            case 0:
                result.append(">>> beep <<<\n");
                result.append("param mode : " + BeepModeConstrants.NORMAL + "\n");
                smartPosApi.beep(BeepModeConstrants.NORMAL);
                break;
            case 1:
                result.append(">>> beep <<<\n");
                result.append("param mode : " + BeepModeConstrants.SUCCESS + "\n");
                smartPosApi.beep(BeepModeConstrants.SUCCESS);
                break;
            case 2:
                result.append(">>> beep <<<\n");
                result.append("param mode : " + BeepModeConstrants.FAIL + "\n");
                smartPosApi.beep(BeepModeConstrants.FAIL);
                break;
            case 3:
                result.append(">>> beep <<<\n");
                result.append("param mode : " + BeepModeConstrants.INTERVAL + "\n");
                smartPosApi.beep(BeepModeConstrants.INTERVAL);
                break;
            case 4:
                result.append(">>> beep <<<\n");
                result.append("param mode : " + BeepModeConstrants.ERROR + "\n");
                smartPosApi.beep(BeepModeConstrants.ERROR);
                break;
        }
        return result.append("success\n").toString();
    }
}
