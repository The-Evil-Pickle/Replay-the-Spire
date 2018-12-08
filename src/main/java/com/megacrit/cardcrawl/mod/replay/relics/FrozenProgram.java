package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class FrozenProgram extends AbstractRelic
{
    public static final String ID = "Replay:Frozen Program";
    
    public FrozenProgram() {
        super(ID, "frozenProgram.png", RelicTier.SHOP, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new FrozenProgram();
    }
    
    @Override
    public void onEvokeOrb(final AbstractOrb orb) {
    	if (orb != null && orb instanceof Frost) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReflectionPower(AbstractDungeon.player, 1), 1));
    	}
    }
}
