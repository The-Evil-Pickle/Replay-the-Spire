package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.defect.RaiderMaskAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class RaidersMask extends AbstractRelic
{
    public static final String ID = "Raider's Mask";
    
    public RaidersMask() {
        super("Raider's Mask", "raidmask.png", RelicTier.SHOP, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RaidersMask();
    }
    
    @Override
    public void onPlayerEndTurn() {
    	AbstractDungeon.actionManager.addToBottom(new RaiderMaskAction());
    }
}
