package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.*;

public class Splinters extends CustomCard implements StartupCard
{
	public static final String ID = "ReplayTheSpireMod:Splinters";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	
	public Splinters()
	{
		super(ID, NAME, "cards/replay/betaCurse.png", COST, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
		ExhaustiveVariable.setBaseValue(this, 2);
		this.baseMagicNumber = 2;
		this.magicNumber = this.baseMagicNumber;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.THORNS)));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
	}
	
	public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		return this.cardPlayable(m) && this.hasEnoughEnergy();
	}
	public AbstractCard makeCopy()
	{
		return new Splinters();
	}
	
	public void upgrade() {}

	@Override
	public boolean atBattleStartPreDraw() {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.makeCopy(), 1, true, true));
		return true;
	}
}
