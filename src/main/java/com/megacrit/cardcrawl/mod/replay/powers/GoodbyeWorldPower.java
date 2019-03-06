package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

public class GoodbyeWorldPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "GoodbyeWorld";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public GoodbyeWorldPower(final AbstractCreature owner, final int amount) {
        this.name = GoodbyeWorldPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("corruption");
    }
	
    @Override
    public void updateDescription() {
        this.description = GoodbyeWorldPower.DESCRIPTIONS[0] + this.amount + GoodbyeWorldPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
    	for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.rarity == AbstractCard.CardRarity.COMMON && c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
            }
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = GoodbyeWorldPower.powerStrings.NAME;
        DESCRIPTIONS = GoodbyeWorldPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new GoodbyeWorldPower(owner, amount);
	}
}
