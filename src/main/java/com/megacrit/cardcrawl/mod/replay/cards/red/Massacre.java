package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class Massacre
  extends CustomCard
{
  public static final String ID = "Massacre";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Massacre");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = 1;
  private static final int ATTACK_DMG = 1;
  private static final int POOL = 1;
  
  public Massacre()
  {
    super("Massacre", NAME, "cards/replay/massacre.png", 1, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
    
    this.baseDamage = 1;
    this.isMultiDamage = true;
	this.baseMagicNumber = 5;
    this.magicNumber = this.baseMagicNumber;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
    AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Strength"));
	if (p.hasPower("Flex"))
	{
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Flex"));
	}
  }
  
  public AbstractCard makeCopy()
  {
    return new Massacre();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      upgradeDamage(2);
      upgradeMagicNumber(3);
    }
  }
  
  public void applyPowers()
  {
    AbstractPlayer player = AbstractDungeon.player;
    this.isDamageModified = false;
	ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
	float[] tmp = new float[m.size()];
	for (int i = 0; i < tmp.length; i++) {
	tmp[i] = this.baseDamage;
	}
	for (int i = 0; i < tmp.length; i++) {
	for (AbstractPower p : player.powers)
	{
		if (p instanceof StrengthPower)
		{
			for (int _i = 1; _i < this.magicNumber; _i++){
				tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
			}
		}
	  tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
	  if (this.baseDamage != (int)tmp[i]) {
		this.isDamageModified = true;
	  }
	}
	}
	for (int i = 0; i < tmp.length; i++) {
	for (AbstractPower p : player.powers)
	{
	  tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
	  if (this.baseDamage != (int)tmp[i]) {
		this.isDamageModified = true;
	  }
	}
	}
	for (int i = 0; i < tmp.length; i++) {
	if (tmp[i] < 0.0F) {
	  tmp[i] = 0.0F;
	}
	}
	this.multiDamage = new int[tmp.length];
	for (int i = 0; i < tmp.length; i++) {
	this.multiDamage[i] = MathUtils.floor(tmp[i]);
	}
	this.damage = this.multiDamage[0];
  }
  
  
  public void calculateCardDamage(AbstractMonster mo)
  {
    AbstractPlayer player = AbstractDungeon.player;
    this.isDamageModified = false;
	ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
	float[] tmp = new float[m.size()];
	for (int i = 0; i < tmp.length; i++) {
	tmp[i] = this.baseDamage;
	}
	for (int i = 0; i < tmp.length; i++) {
	for (AbstractPower p : player.powers)
	{
		if (p instanceof StrengthPower)
		{
			for (int _i = 1; _i < this.magicNumber; _i++){
				tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
			}
		}
	  tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
	  if (this.baseDamage != (int)tmp[i]) {
		this.isDamageModified = true;
	  }
	}
	}
	for (int i = 0; i < tmp.length; i++) {
	for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
	  if ((!((AbstractMonster)m.get(i)).isDying) && (!((AbstractMonster)m.get(i)).isEscaping)) {
		tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn);
	  }
	}
	}
	for (int i = 0; i < tmp.length; i++) {
	for (AbstractPower p : player.powers)
	{
	  tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
	  if (this.baseDamage != (int)tmp[i]) {
		this.isDamageModified = true;
	  }
	}
	}
	for (int i = 0; i < tmp.length; i++) {
	for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
	  if ((!((AbstractMonster)m.get(i)).isDying) && (!((AbstractMonster)m.get(i)).isEscaping)) {
		tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn);
	  }
	}
	}
	for (int i = 0; i < tmp.length; i++) {
	if (tmp[i] < 0.0F) {
	  tmp[i] = 0.0F;
	}
	}
	this.multiDamage = new int[tmp.length];
	for (int i = 0; i < tmp.length; i++) {
	this.multiDamage[i] = MathUtils.floor(tmp[i]);
	}
	this.damage = this.multiDamage[0];
  }
}
