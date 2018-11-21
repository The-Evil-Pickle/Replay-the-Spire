package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class StoryPower extends AbstractPower
{
    public static final String POWER_ID = "Replay:Storyteller";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public StoryPower(final AbstractCreature owner, final int amount) {
        this.name = StoryPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/knowledge.png");
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        //this.priority = 99;
    }
    
    @Override
    public void atEndOfRound() {
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }
    
    @Override
    public void updateDescription() {
        this.description = StoryPower.DESCRIPTIONS[0] + this.amount + StoryPower.DESCRIPTIONS[1];
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = StoryPower.powerStrings.NAME;
        DESCRIPTIONS = StoryPower.powerStrings.DESCRIPTIONS;
    }
}
