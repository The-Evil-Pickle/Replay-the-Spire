package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
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
