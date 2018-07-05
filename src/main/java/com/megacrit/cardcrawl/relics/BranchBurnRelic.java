package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;

public class BranchBurnRelic extends AbstractRelic
{
    public static final String ID = "BranchBurnRelic";
    private static final int REGEN_AMT = 4;
    private boolean firstTurn;
    
    public BranchBurnRelic() {
        super("BranchBurnRelic", "kindling.png", RelicTier.SPECIAL, LandingSound.FLAT);
        this.firstTurn = true;
        this.counter = -2;
        this.pulse = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + BranchBurnRelic.REGEN_AMT + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void atTurnStart() {
        if (this.firstTurn) {
            if (this.counter == -2) {
                this.pulse = false;
                this.counter = -1;
                this.flash();
                //AbstractDungeon.actionManager.addToTop(new GainEnergyAction(2));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, BranchBurnRelic.REGEN_AMT), BranchBurnRelic.REGEN_AMT));
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
            this.firstTurn = false;
        }
    }
    
    @Override
    public void atPreBattle() {
        this.firstTurn = true;
    }
    
    @Override
    public void setCounter(final int counter) {
        super.setCounter(counter);
        if (counter == -2) {
            this.pulse = true;
        }
    }
    
    @Override
    public void onEnterRestRoom() {
        this.flash();
        this.counter = -2;
        this.pulse = true;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BranchBurnRelic();
    }
}
