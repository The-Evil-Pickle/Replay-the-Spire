package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.mod.hubris.relics.abstracts.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.mod.replay.actions.unique.FlurryBottleAction;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.relics.*;
import basemod.*;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.panelUI.ReplayIntSliderSetting;

public class ToweringPillarOfHats extends AbstractRelic
{
    public static final String ID = "Replay:Towering Pillar of Hats";
    public static final ReplayIntSliderSetting SETTING_MAX_SIZE = new ReplayIntSliderSetting("Pillar_Max", "Hand Size", 2, 0, 5);
    public static final ReplayIntSliderSetting SETTING_RETAIN = new ReplayIntSliderSetting("Pillar_Retain", "Retain 1 per __", 3, 2, 6);
    public ToweringPillarOfHats() {
        super(ID, "pillarOfHats.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.FLAT);
        this.counter = -1;
    	this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        if (ReplayTheSpireMod.foundmod_hubris) {
        	this.tips.add(new PowerTip("Synergy", this.DESCRIPTIONS[4]));
        }
        if (ReplayTheSpireMod.foundmod_conspire) {
        	this.tips.add(new PowerTip("Synergy", this.DESCRIPTIONS[5]));
        }
    }

    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0] + SETTING_MAX_SIZE.value + this.DESCRIPTIONS[1] + SETTING_RETAIN.value + this.DESCRIPTIONS[2];
    	if (ReplayTheSpireMod.foundmod_hubris) {
    		desc += this.DESCRIPTIONS[3] + FontHelper.colorString("Slimy Hat", "y");
    	}
    	if (ReplayTheSpireMod.foundmod_conspire) {
    		desc += this.DESCRIPTIONS[3] + FontHelper.colorString("Top Hat", "y");
    	}
        return desc;
    }
    
    @Override
    public void onPlayerEndTurn() {
    	int numToRetain = AbstractDungeon.player.hand.size() / 3;
    	for (int i=1; i <= numToRetain; i++) {
    		AbstractDungeon.player.hand.group.get(AbstractDungeon.player.hand.size() - i).retain = true;
    	}
    	if (AbstractDungeon.player.hasRelic("conspire:TopHat") && AbstractDungeon.player.hand.size() >= 10) {
    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
    	}
    }

    @Override
    public void atPreBattle() {
    	if (this.counter == -1 && AbstractDungeon.player.hasRelic("hubris:SlimyHat")) {
    		BaseMod.MAX_HAND_SIZE += 1;
        	this.counter = -2;
    	}
    }
    
    @Override
    public void onEquip() {
        BaseMod.MAX_HAND_SIZE += 2;
        if (AbstractDungeon.player.hasRelic("hubris:SlimyHat")) {
        	BaseMod.MAX_HAND_SIZE += 1;
        	this.counter = -2;
        }
    }

    @Override
    public void onUnequip() {
        BaseMod.MAX_HAND_SIZE -= 2;
        if (this.counter == -2) {
        	BaseMod.MAX_HAND_SIZE -= 1;
        	this.counter = -1;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ToweringPillarOfHats();
    }
    
    @Override
    public int getPrice() {
    	return 300;
    }
}
