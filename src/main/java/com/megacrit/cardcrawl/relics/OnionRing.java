package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;

public class OnionRing extends AbstractRelic
{
    public static final String ID = "Onion Ring";
    public int TurnsLeft;
    public boolean isFirstTurn;
    
    public OnionRing() {
        super(ID, "onionRing.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
        this.TurnsLeft = 3;
        this.isFirstTurn = true;
    }
    
    public void atBattleStart() {
        this.TurnsLeft = 3;
        this.isFirstTurn = true;
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, 3));
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public void atTurnStart() {
    	if (this.isFirstTurn) {
    		this.isFirstTurn = false;
    	} else if (this.TurnsLeft > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, -1));
            --this.TurnsLeft;
        }
    }
    
    public AbstractRelic makeCopy() {
        return new OnionRing();
    }
}
