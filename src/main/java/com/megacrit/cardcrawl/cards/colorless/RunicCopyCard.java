package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class RunicCopyCard 
extends CustomCard
{
	private AbstractCard parentCard;

	public RunicCopyCard(AbstractCard c)
	{
	  super(c.cardID, c.name, "colorless/attack/swiftStrike", c.cost, c.rawDescription, c.type, c.color, c.rarity, c.target);
	  this.parentCard = c;
	  TakeParentStats();
	}
	
	public void TakeParentStats() {
		this.target = this.parentCard.target;
		this.upgraded = this.parentCard.upgraded;
		this.timesUpgraded = this.parentCard.timesUpgraded;
		this.baseDamage = this.parentCard.baseDamage;
		this.baseBlock = this.parentCard.baseBlock;
		this.baseMagicNumber = this.parentCard.baseMagicNumber;
		this.cost = this.parentCard.cost;
		this.inBottleLightning = this.parentCard.inBottleLightning;
		this.inBottleFlame = this.parentCard.inBottleFlame;
		this.inBottleTornado = this.parentCard.inBottleTornado;
		this.isSeen = this.parentCard.isSeen;
		this.isLocked = this.parentCard.isLocked;
		this.misc = this.parentCard.misc;
		this.exhaust = this.parentCard.exhaust;
		this.isEthereal = this.parentCard.isEthereal;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m)
	{
		this.parentCard.use(p, m);
	}
	
	public AbstractCard makeCopy()
	{
	  return new RunicCopyCard(this.parentCard);
	}
	
	public void upgrade()
	{
		this.parentCard.upgraded = false;
		this.parentCard.upgrade();
		this.TakeParentStats();
		this.name = this.parentCard.makeCopy().name + "+" + this.timesUpgraded;
	}
}