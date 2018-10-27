package com.megacrit.cardcrawl.mod.replay.actions.defect;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.characters.*;

import java.util.*;

public class RaiderMaskAction extends AbstractGameAction
{
    public RaiderMaskAction() {
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.WAIT;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
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
        		if (orb instanceof Dark && orb.evokeAmount + orb.passiveAmount > lowestHP + lowestHPBlock) {
        			orb.evokeAmount += orb.passiveAmount;
        			AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(orb));
                    this.isDone = true;
        			return;
        		}
        	}
            this.isDone = true;
        }
    }
}
