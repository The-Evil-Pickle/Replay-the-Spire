package com.megacrit.cardcrawl.mod.replay.powers;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class WhispersOfEvilPower extends AbstractPower
{
	public static final String POWER_ID = "Replay:Whispers Of Evil";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public WhispersOfEvilPower(AbstractCreature owner, int amnt)
	{
	  this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amnt;
		this.type = AbstractPower.PowerType.DEBUFF;
		updateDescription();
		loadRegion("corruption");
	}

    @Override
    public void onInitialApplication() {
        AbstractDungeon.player.gameHandSize += this.amount;
    }
    @Override
    public void onRemove() {
        AbstractDungeon.player.gameHandSize -= this.amount;
    }
    
    @Override
    public void reducePower(final int reduceAmount) {
        this.fontScale = 8.0f;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }
	@Override
	public void updateDescription()
	{
		if (this.amount > 1) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
		} else {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
		}
	}
	@Override
	public void atEndOfTurn(boolean isPlayer)
	{
		this.flash();
		for (int i = 0; i < this.amount; i++) {
			final AbstractCard card = AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy();
	        card.name = "Echo: " + card.name;
	        card.exhaust = true;
	        card.isEthereal = true;
	        AlwaysRetainField.alwaysRetain.set(card, false);
	        SoulboundField.soulbound.set(card, false);
	        card.retain = false;
	        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));
		}
	}
}
