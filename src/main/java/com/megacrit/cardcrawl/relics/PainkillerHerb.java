package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.rooms.*;

import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;

public class PainkillerHerb extends AbstractRelic
{
    public static final String ID = "Painkiller Herb";
    private static final int HEAL_AMT = 6;
    
    public PainkillerHerb() {
        super("Painkiller Herb", "cherryBlossom.png", RelicTier.BOSS, LandingSound.FLAT);
        if (ReplayTheSpireMod.foundmod_hubris) {
        	this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.tips.add(new PowerTip("Synergy", this.DESCRIPTIONS[2] + FontHelper.colorString("Virtuous Blindfold", "y") + this.DESCRIPTIONS[3] + HEAL_AMT + this.DESCRIPTIONS[4]));
            this.initializeTips();
        }
    }
	
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
    	if (ReplayTheSpireMod.foundmod_hubris) {
    		desc += this.DESCRIPTIONS[2] + "Virtuous Blindfold";
    	}
    	
    	return desc;
    }
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
	
    @Override
    public void onEnterRoom(final AbstractRoom room) {
        if (AbstractDungeon.player.hasRelic("hubris:VirtuousBlindfold")) {
            this.flash();
            AbstractDungeon.player.heal(PainkillerHerb.HEAL_AMT);
        }
    }
    @Override
    public AbstractRelic makeCopy() {
        return new PainkillerHerb();
    }
}
