/*
package com.megacrit.cardcrawl.mod.replay.powers.replayxover;

import com.megacrit.cardcrawl.powers.*;

import expansioncontent.actions.RandomCardWithTagAction;
import replayTheSpire.replayxover.downfallen;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.PowerStrings;

import com.megacrit.cardcrawl.actions.common.*;

public class StudyFableSpinnerPower extends AbstractPower
{
	public static final String POWER_ID = "replay:ss_forest";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean upgrade;
    
    public StudyFableSpinnerPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final boolean upgrade) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.upgrade = upgrade;
        this.img = new Texture("images/powers/32/knowledge.png");
        this.amount = amount;
        this.updateDescription();
    }
    
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = StudyFableSpinnerPower.DESCRIPTIONS[0];
        }
        else {
            this.description = StudyFableSpinnerPower.DESCRIPTIONS[1] + this.amount + StudyFableSpinnerPower.DESCRIPTIONS[2];
        }
    }
    
    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RandomCardWithTagAction(this.upgrade, downfallen.STUDY_FOREST));
        if (this.amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
}
*/
