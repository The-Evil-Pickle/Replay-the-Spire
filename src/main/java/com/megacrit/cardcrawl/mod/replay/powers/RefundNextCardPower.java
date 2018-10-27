package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;

public class RefundNextCardPower extends AbstractPower
{
    public static final String POWER_ID = "ReplayTheSpireMod:RefundNext";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RefundNextCardPower(final AbstractCreature owner, final int newAmount) {
        this.name = RefundNextCardPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/energyNext.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = RefundNextCardPower.DESCRIPTIONS[0] + this.amount + RefundNextCardPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        AbstractDungeon.actionManager.addToTop(new ReplayRefundAction(card, this.amount));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = RefundNextCardPower.powerStrings.NAME;
        DESCRIPTIONS = RefundNextCardPower.powerStrings.DESCRIPTIONS;
    }
}
