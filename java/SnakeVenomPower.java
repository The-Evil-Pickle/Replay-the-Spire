package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;

public class SnakeVenomPower extends AbstractPower
{
    public static final String POWER_ID = "Snake Venom";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public SnakeVenomPower(final AbstractCreature owner, final int newAmount) {
        this.name = SnakeVenomPower.NAME;
        this.ID = "Snake Venom";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/envenom.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = SnakeVenomPower.DESCRIPTIONS[0] + this.amount + SnakeVenomPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Snake Venom"));
        }
    }
	
    @Override
    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, this.owner, new NecroticPoisonPower(target, this.owner, this.amount), this.amount, true));
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Snake Venom");
        NAME = SnakeVenomPower.powerStrings.NAME;
        DESCRIPTIONS = SnakeVenomPower.powerStrings.DESCRIPTIONS;
    }
}
