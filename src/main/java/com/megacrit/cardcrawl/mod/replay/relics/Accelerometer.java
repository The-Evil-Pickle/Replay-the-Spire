package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.mod.hubris.relics.abstracts.*;
import com.evacipated.cardcrawl.mod.stslib.relics.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;

import basemod.ReflectionHacks;

import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;

public class Accelerometer extends AbstractRelic implements ClickableRelic
{
    public static final String ID = "Replay:Accelerometer";
    private boolean usedThisFight;
    
    public Accelerometer() {
        super("Replay:Accelerometer", "replay_accelerometer.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
    }
    
    public String getUpdatedDescription() {
        return this.CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0];
    }
    
    public void atBattleStart() {
        this.usedThisFight = false;
        this.beginLongPulse();
    }
    public void onVictory() {
    	this.usedThisFight = true;
        this.stopPulse();
    }
    public void atTurnStartPostDraw() {
    	if (!this.usedThisFight) {
    		this.beginLongPulse();
    	}
    }
    public void onPlayerEndTurn() {
    	this.stopPulse();
    }
    public void onRightClick() {
        if (!this.isObtained || this.usedThisFight || !this.pulse) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.stopPulse();
            this.flash();
            this.usedThisFight = true;
            for (AbstractPower p : AbstractDungeon.player.powers) {
            	if (p.type == AbstractPower.PowerType.DEBUFF && (boolean)ReflectionHacks.getPrivate(p, AbstractPower.class, "isTurnBased")) {
    				AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, p, 1));
    			}
            }
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            	if (m != null && !m.isDeadOrEscaped()) {
            		for (AbstractPower p : m.powers) {
            			if (p.type == AbstractPower.PowerType.DEBUFF && (boolean)ReflectionHacks.getPrivate(p, AbstractPower.class, "isTurnBased")) {
            				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, p, 1));
            			}
            		}
            	}
            }
        }
    }
    
    public AbstractRelic makeCopy() {
        return new Accelerometer();
    }
}
