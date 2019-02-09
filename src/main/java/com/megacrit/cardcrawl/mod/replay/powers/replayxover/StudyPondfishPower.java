package com.megacrit.cardcrawl.mod.replay.powers.replayxover;

import com.megacrit.cardcrawl.powers.*;
import slimebound.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.RandomPondfishCardAction;

import slimebound.actions.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import org.apache.logging.log4j.*;

public class StudyPondfishPower extends AbstractPower
{
	public static final String POWER_ID = "replay:ss_fish";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    public StudyPondfishPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture("images/powers/32/ss_fish.png");
        this.amount = amount;
        this.updateDescription();
    }
    
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = StudyPondfishPower.DESCRIPTIONS[0];
        }
        else {
            this.description = StudyPondfishPower.DESCRIPTIONS[1] + this.amount + StudyPondfishPower.DESCRIPTIONS[2];
        }
    }
    
    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RandomPondfishCardAction(false));
        if (this.amount <= 1) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
}
