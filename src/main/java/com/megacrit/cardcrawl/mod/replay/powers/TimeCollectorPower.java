package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class TimeCollectorPower extends AbstractPower
{
    public static final String POWER_ID = "TimeCollector";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;
    private int maxAmount;
    
    public TimeCollectorPower(final AbstractCreature owner, final int maxAmount) {
        this.name = TimeCollectorPower.NAME;
        this.ID = TimeCollectorPower.POWER_ID;
        this.owner = owner;
        this.amount = maxAmount;
        this.maxAmount = maxAmount;
        this.updateDescription();
        this.loadRegion("time");
        this.type = PowerType.BUFF;
    }
    
    @Override
    public void updateDescription() {
        this.description = TimeCollectorPower.DESC[0] + this.maxAmount + TimeCollectorPower.DESC[1];
    }
    
    @Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
        this.flashWithoutSound();
        --this.amount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.cardQueue.clear();
            for (final AbstractCard c : AbstractDungeon.player.limbo.group) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
            }
            AbstractDungeon.player.limbo.group.clear();
            AbstractDungeon.player.releaseCard();
            AbstractDungeon.overlayMenu.endTurnButton.disable(true);
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        this.updateDescription();
    }
    
    @Override
    public void atStartOfTurn() {
        this.amount = this.maxAmount;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = TimeCollectorPower.powerStrings.NAME;
        DESC = TimeCollectorPower.powerStrings.DESCRIPTIONS;
    }
}
