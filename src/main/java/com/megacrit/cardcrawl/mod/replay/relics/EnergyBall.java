package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.*;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.ui.panels.*;

import basemod.helpers.BaseModCardTags;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class EnergyBall extends ReplayAbstractRelic implements ClickableRelic
{
    public static final String ID = "ReplayTheSpireMod:EnergyBall";
    private AbstractCard srcCard;
    private AbstractCard card;
    private int energyCost;
    
	private boolean usedThisBattle;
    public static final ReplayIntSliderSetting SETTING_ENERGY = new ReplayIntSliderSetting("EnergyBall_Energy", "Energy Required", 3, 2, 6);
    public static final ReplayIntSliderSetting SETTING_MAX = new ReplayIntSliderSetting("EnergyBall_Max", "Max Storage", 5, 2, 12);
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
  		ArrayList<ReplayRelicSetting> r = new ArrayList<ReplayRelicSetting>();
  		r.add(SETTING_ENERGY);
  		return r;
  	}
    public EnergyBall() {
        super(ID, "SSBB_Smash_Ball.png", RelicTier.SHOP, LandingSound.MAGICAL);
        this.usedThisBattle = true;
        this.counter = 0;
        this.energyCost = SETTING_ENERGY.value;
    }
    
    @Override
    public String getUpdatedDescription() {
    	if (this.srcCard != null) {
    		return this.CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0] + SETTING_MAX.value + this.DESCRIPTIONS[1] + this.energyCost + this.DESCRIPTIONS[2];
    	}
        return this.CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0] + SETTING_MAX.value + this.DESCRIPTIONS[1] + SETTING_ENERGY.value + this.DESCRIPTIONS[2];
    }

    @Override
    public void atBattleStart() {
    	this.usedThisBattle = false;
    }
    @Override
    public void onVictory() {
    	this.usedThisBattle = true;
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
    			if (c.hasTag(BaseModCardTags.FORM)) {
    				this.srcCard = c.makeCopy();
    				return;
    			}
    			if (c.type == AbstractCard.CardType.POWER && c.cost >= 3 && c.name.toLowerCase().contains("form")) {
    				this.srcCard = c.makeCopy();
    			}
    		}
    	}
    	if (this.srcCard != null) {
    		this.energyCost = Math.min(this.srcCard.cost - 3 + SETTING_ENERGY.value, SETTING_MAX.value);
    	}
    	this.description = this.getUpdatedDescription();
    	this.tips.clear();
    	this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    @Override
    public void onPlayerEndTurn() {
    	if (EnergyPanel.totalCount > 0 && this.counter < SETTING_MAX.value) {
    		this.flash();
    		int increaseby = Math.min(SETTING_MAX.value - this.counter, EnergyPanel.totalCount);
			this.counter += increaseby;
    		EnergyPanel.useEnergy(increaseby);
    	}
    	if (this.card != null) {
    		this.card.retain = true;
    	}
    }
    @Override
    public int getPrice() {
    	return 300;
    }
    @Override
    public AbstractRelic makeCopy() {
        return new EnergyBall();
    }
	@Override
	public void onRightClick() {
		if (this.isObtained && this.counter >= this.energyCost && !this.usedThisBattle) {
			this.counter -= this.energyCost;
			this.usedThisBattle = true;
			if (this.srcCard == null) {
				this.onEquip();
			}
			if (AbstractDungeon.player.hasRelic(QuantumEgg.ID) && !this.srcCard.upgraded) {
				this.srcCard.upgrade();
			}
			AbstractCard c = this.srcCard.makeCopy();
			c.freeToPlayOnce = true;
			c.cost = 0;
			c.costForTurn = 0;
			AbstractDungeon.actionManager.addToBottom(new QueueCardAction(c, AbstractDungeon.getRandomMonster()));
		}
	}
}