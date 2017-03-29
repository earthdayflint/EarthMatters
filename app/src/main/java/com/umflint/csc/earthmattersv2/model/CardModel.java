package com.umflint.csc.earthmattersv2.model;

/**
 * Created by Benjamin on 3/29/2017.
 */

public class CardModel {

    private String cardViewText;

    public CardModel(String cardViewText) {
        this.cardViewText = cardViewText;
    }

    public CardModel() {
    }

    public String getCardViewText() {
        return cardViewText;
    }

    public void setCardViewText(String cardViewText) {
        this.cardViewText = cardViewText;
    }
}
