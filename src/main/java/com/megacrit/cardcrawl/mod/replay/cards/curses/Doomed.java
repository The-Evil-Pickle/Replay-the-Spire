package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import basemod.abstracts.*;

public class Doomed extends CustomCard
{
	public static final String ID = "ReplayTheSpireMod:Doomed";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	
	public Doomed()
	{
			super(ID, NAME, "cards/replay/betaCurse.png", COST, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF);
			this.isEthereal = true;
			//SoulboundField.soulbound.set(this, true);
			FleetingField.fleeting.set(this, true);
			GraveField.grave.set(this, true);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m)
	{
		AbstractDungeon.actionManager.addToBottom(new RemoveAllTemporaryHPAction(p, p));
		AbstractDungeon.actionManager.addToBottom(new ReplayLoseAllShieldingAction(p, p));
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, BufferPower.POWER_ID));
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, IntangiblePlayerPower.POWER_ID));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, p.maxHealth * 2, DamageInfo.DamageType.HP_LOSS)));
	}
	
	public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		return this.cardPlayable(m) && this.hasEnoughEnergy();
	}
	public AbstractCard makeCopy()
	{
		return new Doomed();
	}
	
	public void upgrade() {}
}
