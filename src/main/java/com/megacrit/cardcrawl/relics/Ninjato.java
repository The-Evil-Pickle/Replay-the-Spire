package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class Ninjato extends ReplayAbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Ninjato";
    private static final int NUM_CARDS = 3;
    private boolean triggeredThisTurn;

    public static final ReplayIntSliderSetting SETTING_DRAW = new ReplayIntSliderSetting("Ninjato_Draw", "Cards Drawn", 2, 3);
    public static final ReplayIntSliderSetting SETTING_REFUND = new ReplayIntSliderSetting("Ninjato_Refund", "Refund (Makes relic Rare)", 0, 3);
    
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_DRAW);
    	settings.add(SETTING_REFUND);
		return settings;
	}
    
    public Ninjato() {
        super(ID, "ninjato.png", (SETTING_REFUND.value > 0 && SETTING_DRAW.value > 0) ? RelicTier.RARE : RelicTier.UNCOMMON, LandingSound.CLINK);
        this.triggeredThisTurn = false;
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = "";
    	if (SETTING_REFUND.value > 0) {
    		desc += this.DESCRIPTIONS[0] + SETTING_REFUND.value + ".";
    		if (SETTING_DRAW.value > 0) {
    			desc += " NL ";
    		}
    	}
    	if (SETTING_DRAW.value > 0) {
			desc += this.DESCRIPTIONS[1] + NUM_CARDS + this.DESCRIPTIONS[2] + SETTING_DRAW.value + this.DESCRIPTIONS[3];
		}
        return desc;
    }
    
    @Override
    public void atTurnStart() {
        this.triggeredThisTurn = false;
        this.counter = 0;
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            ++this.counter;
            if (this.counter % 3 == 0) {
                this.counter = 0;
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, SETTING_DRAW.value));
                if (!this.triggeredThisTurn && SETTING_REFUND.value > 0) {
                	AbstractDungeon.actionManager.addToTop(new ReplayRefundAction(card, SETTING_REFUND.value));
                    this.triggeredThisTurn = true;
                }
            }
        }
    }
    
    @Override
    public void onVictory() {
        this.counter = -1;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Ninjato();
    }
}
