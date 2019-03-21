package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.black.EchoChamber;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import gluttonmod.actions.MakeEchoAction;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;

public class EchoChamberPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Replay:Echo Chamber";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int cardsDoubledThisTurn;
    private boolean upgraded;
    
    public EchoChamberPower(final AbstractCreature owner, final int amount, final boolean upgraded) {
        this.cardsDoubledThisTurn = 0;
        this.name = EchoChamberPower.NAME;
        this.ID = POWER_ID;
        this.upgraded = upgraded;
        if (this.upgraded) {
        	this.name += "+";
        	this.ID += "+";
        }
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("echo");
    }
    
    @Override
    public void updateDescription() {
        if (this.upgraded) {
            this.description = EchoChamberPower.DESCRIPTIONS[0] + this.amount + EchoChamberPower.DESCRIPTIONS[2];
        }
        else {
            this.description = EchoChamberPower.DESCRIPTIONS[0] + this.amount + EchoChamberPower.DESCRIPTIONS[1];
        }
    }
    
    @Override
    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!card.purgeOnUse && !(card instanceof EchoChamber) && this.amount > 0 && this.cardsDoubledThisTurn < this.amount && (this.upgraded || (card.type != CardType.POWER && !card.isEthereal))) {
            ++this.cardsDoubledThisTurn;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new MakeEchoAction(card));
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = EchoChamberPower.powerStrings.NAME;
        DESCRIPTIONS = EchoChamberPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new EchoChamberPower(owner, amount, upgraded);
	}
}
