package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomCard;

public class Necrogeddon
extends CustomCard
{
	public static final String ID = "ReplayTheSpireMod:Necrogeddon";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	
	public Necrogeddon()
	{
		super(ID, NAME, "cards/replay/necrogeddon.png", 1, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		GraveField.grave.set(this, true);
		this.baseDamage = 0;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m)
	{
		if (this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
		} else {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
		}
		
	}
	
	public AbstractCard makeCopy()
	{
		return new Necrogeddon();
	}
	private void workOutDescription() {
	    if (this.upgraded) {
	    	this.rawDescription = UPGRADE_DESCRIPTION;
	    } else {
	    	this.rawDescription = DESCRIPTION;
	    }
	    if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
	    	this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[0];
	    }
	    this.initializeDescription();
	}
	@Override
	public void applyPowers() {
		this.baseDamage = AbstractDungeon.player.exhaustPile.size();
	    super.applyPowers();
		this.workOutDescription();
	}
	@Override
	public void calculateCardDamage(final AbstractMonster mo) {
		this.baseDamage = AbstractDungeon.player.exhaustPile.size();
		super.calculateCardDamage(mo);
	}
	public void upgrade()
	{
		if (!this.upgraded)
		{
			upgradeName();
		    this.isMultiDamage = true;
		    this.target = AbstractCard.CardTarget.ALL_ENEMY;
			this.workOutDescription();
		}
	}
}