package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.defect.EvokeSpecificOrbAction;
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
    	AbstractOrb evokeMe = null;
    	int lowestHP = 999999999;
    	int lowestHPBlock = 0;
    	for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
    		if (m != null && !m.isDeadOrEscaped() && m.currentHealth < lowestHP) {
    			lowestHP = m.currentHealth;
    			lowestHPBlock = m.currentBlock;
    		}
    		if (m.hasPower(LockOnPower.POWER_ID)) {
    			lowestHP = m.currentHealth;
    			lowestHPBlock = m.currentBlock;
    			break;
    		}
    	}
    	for (AbstractOrb orb : AbstractDungeon.player.orbs) {
    		if (orb instanceof Dark && orb.evokeAmount > lowestHP + lowestHPBlock) {
    			AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(orb));
    			return;
    		}
    	}
    }
}
