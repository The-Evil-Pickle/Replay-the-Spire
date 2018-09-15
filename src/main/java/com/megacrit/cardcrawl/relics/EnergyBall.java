package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.core.*;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.ui.panels.*;

import basemod.helpers.BaseModTags;
import basemod.helpers.CardTags;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class EnergyBall extends ReplayAbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:EnergyBall";
    private AbstractCard srcCard;
    private AbstractCard card;
	
    public static final ReplayIntSliderSetting SETTING_ENERGY = new ReplayIntSliderSetting("EnergyBall_Energy", "Energy Required", 3, 2, 6);
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
  		ArrayList<ReplayRelicSetting> r = new ArrayList<ReplayRelicSetting>();
  		r.add(SETTING_ENERGY);
  		return r;
  	}
    public EnergyBall() {
        super(ID, "SSBB_Smash_Ball.png", RelicTier.SHOP, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SETTING_ENERGY.value + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
    	this.counter = 0;
    }
    @Override
    public void onVictory() {
    	this.counter = -1;
    }
    
    @Override
    public void onEquip() {
    	AbstractPlayer player = AbstractDungeon.player;
    	if (player instanceof Ironclad) {
    		this.srcCard = new DemonForm();
    	} else if (player instanceof TheSilent) {
    		this.srcCard = new WraithForm();
    	} else if (player instanceof Defect) {
    		this.srcCard = new EchoForm();
    	} else {
    		this.srcCard = new DemonForm();
    		for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
    			if (CardTags.hasTag(c, BaseModTags.FORM)) {
    				this.srcCard = c.makeCopy();
    				return;
    			}
    			if (c.type == AbstractCard.CardType.POWER && c.cost == 3 && c.name.toLowerCase().contains("form")) {
    				this.srcCard = c.makeCopy();
    			}
    		}
    	}
    }
    
    @Override
    public void onPlayerEndTurn() {
    	
    	if (EnergyPanel.totalCount > 0 && this.counter < SETTING_ENERGY.value) {
    		this.flash();
    		this.counter += EnergyPanel.totalCount;
    		if (this.counter >= SETTING_ENERGY.value) {
    			if (this.srcCard == null) {
    				this.onEquip();
    			}
    			this.counter = SETTING_ENERGY.value;
    			this.card = this.srcCard.makeCopy();
    			this.card.freeToPlayOnce = true;
    			this.card.retain = true;
    			this.card.isEthereal = false;
    			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.card));
    		}
    	}
    	if (this.card != null) {
    		this.card.retain = true;
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new EnergyBall();
    }
}