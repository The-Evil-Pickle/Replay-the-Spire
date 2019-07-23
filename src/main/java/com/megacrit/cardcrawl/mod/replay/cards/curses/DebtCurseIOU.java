package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.*;

public class DebtCurseIOU extends CustomCard
{
	public static final String ID = "ReplayTheSpireMod:IOU";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	public static final int GOLD_COST = 100;
	
	public DebtCurseIOU()
	{
			super(ID, NAME, "cards/replay/betaCurse.png", COST, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
			this.baseMagicNumber = GOLD_COST;
			this.magicNumber = this.baseMagicNumber;
			SoulboundField.soulbound.set(this, true);
			FleetingField.fleeting.set(this, true);
			this.tags.add(CardTags.HEALING);//doesn't heal but we don't really want to generate it in combat
	}
	
	public void use(AbstractPlayer p, AbstractMonster m)
	{
		AbstractDungeon.player.loseGold(this.magicNumber);
	}
	
	public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		return this.cardPlayable(m) && p.gold >= this.magicNumber;
	}
	public AbstractCard makeCopy()
	{
		return new DebtCurseIOU();
	}
	
	public void upgrade() {}
}
