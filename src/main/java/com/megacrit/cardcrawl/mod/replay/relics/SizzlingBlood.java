package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.*;

public class SizzlingBlood extends AbstractRelic
{
    public static final String ID = "Sizzling Blood";
    private static final int HEALTH_AMT = 4;
    private static final int MAX_HEALTH_AMT = 4;
    
    public SizzlingBlood() {
        super("Sizzling Blood", "sizzlingBlood.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SizzlingBlood.HEALTH_AMT + this.DESCRIPTIONS[1] + SizzlingBlood.MAX_HEALTH_AMT + this.DESCRIPTIONS[2];
    }
    
    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        final AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal(SizzlingBlood.HEALTH_AMT);
        }
    }
    
    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(SizzlingBlood.MAX_HEALTH_AMT, false);
    }
	
    @Override
    public AbstractRelic makeCopy() {
        return new SizzlingBlood();
    }
}
