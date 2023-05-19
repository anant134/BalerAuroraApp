package com.smartech.tour.baleraurora.module;

public class cardState {


    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    String cardState;
    guestInfo _guestInfo;

    public guestInfo getGuestInfo() {
        return _guestInfo;
    }
    public void setGuestInfo(guestInfo guestInfo) {
        this._guestInfo = guestInfo;
    }

    public  cardState(){

    }
}
