package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;

public class Shallot extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Shallot";
    public int TurnsLeft;
    public boolean isFirstTurn;
    
    public Shallot() {
        super(ID, "shallot.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
        this.TurnsLeft = 3;
        this.isFirstTurn = true;
    }
    
    public void atBattleStart() {
        this.TurnsLeft = 3;
        this.isFirstTurn = true;
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.addPower(new DexterityPower(AbstractDungeon.player, 3));
        AbstractDungeon.player.addPower(new MetallicizePower(AbstractDungeon.player, 3));
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public void atTurnStart() {
    	if (this.isFirstTurn) {
    		this.isFirstTurn = false;
    	} else if (this.TurnsLeft > 0) {
            this.flash();
            AbstractDungeon.player.addPower(new DexterityPower(AbstractDungeon.player, -1));
            AbstractDungeon.player.addPower(new MetallicizePower(AbstractDungeon.player, -1));
            if (AbstractDungeon.player.hasPower(MetallicizePower.POWER_ID) && AbstractDungeon.player.getPower(MetallicizePower.POWER_ID).amount <= 0) {
            	AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, MetallicizePower.POWER_ID));
            }
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            --this.TurnsLeft;
        }
    }
    
    public AbstractRelic makeCopy() {
        return new Shallot();
    }
}
