package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.WingHatAction;
import com.megacrit.cardcrawl.rooms.*;

public class WingHat extends AbstractRelic implements ClickableRelic
{
    public static final String ID = "Replay:Winged Headress";
    private boolean usedThisTurn;
    private boolean deckCycled;
    
    public WingHat() {
        super(ID, "wingHat.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.usedThisTurn = false;
        this.deckCycled = false;
    }
    @Override
    public void atTurnStartPostDraw() {
    	if (!this.deckCycled) {
    		this.usedThisTurn = false;
    		this.beginLongPulse();
    	}
    }
    @Override
    public void onPlayerEndTurn() {
    	this.usedThisTurn = true;
    	this.pulse = false;
    }
    @Override
    public void onShuffle() {
    	this.deckCycled = true;
    }
    @Override
    public void onRightClick() {
        if (!this.isObtained || this.usedThisTurn || this.deckCycled) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.stopPulse();
            this.flash();
            this.usedThisTurn = true;
            this.pulse  = false;
            AbstractDungeon.actionManager.addToBottom(new WingHatAction());
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WingHat();
    }
}
