package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;

public class Geode extends AbstractRelic
{
    public static final String ID = "Geode";
    private static boolean usedThisCombat;
    
    public Geode() {
        super("Geode", "geode.png", RelicTier.COMMON, LandingSound.HEAVY);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void atPreBattle() {
        Geode.usedThisCombat = false;
        this.pulse = true;
        this.beginPulse();
    }
    
	private boolean hasEmptySlot(){
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
			if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
				return true;
			}
		}
		return false;
	}
	
    @Override
    public void onLoseHp(final int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !Geode.usedThisCombat && damageAmount > 0) {
            this.flash();
            this.pulse = false;
            Geode.usedThisCombat = true;
			if (this.hasEmptySlot()) {
				AbstractDungeon.actionManager.addToTop(new ImpulseAction());
				AbstractDungeon.actionManager.addToTop(new ChannelAction(new CrystalOrb()));
			} else {
				AbstractDungeon.actionManager.addToTop(new ChannelAction(new CrystalOrb()));
				AbstractDungeon.actionManager.addToTop(new ImpulseAction());
			}
			/*if (!this.orbs.isEmpty() && !(this.orbs.get(this.orbs.size() - 1) instanceof EmptyOrbSlot)) {
				AbstractDungeon.addToTop(new TriggerPassiveAction());
			}*/
        }
    }
    
    @Override
    public void onVictory() {
        this.pulse = false;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Geode();
    }
    
    static {
        Geode.usedThisCombat = false;
    }
}
