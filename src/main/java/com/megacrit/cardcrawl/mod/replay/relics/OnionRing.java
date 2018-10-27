package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.*;

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
