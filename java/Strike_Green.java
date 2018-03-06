package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;

public class Strike_Green
  extends AbstractCard
{
  public static final String ID = "Strike_G";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Strike_G");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = 1;
  private static final int ATTACK_DMG = 6;
  private static final int POOL = 0;
  
  public Strike_Green()
  {
    super("Strike_G", NAME, null, "green/attack/strike", 1, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY, 0);
    
    this.baseDamage = 6;
	this.timesUpgraded = 0;
  }
  public Strike_Green(int upgrades)
  {
    super("Strike_G", NAME, null, "green/attack/strike", 1, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY, 0);
    
    this.baseDamage = 6;
	this.timesUpgraded = upgrades;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    if (Settings.isDebug)
    {
      if (Settings.isInfo)
      {
        this.multiDamage = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
        for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
          this.multiDamage[i] = 150;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
      }
      else
      {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, 150, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
      }
    }
    else {
      AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
  }
  
  public AbstractCard makeCopy()
  {
    return new Strike_Green(this.timesUpgraded);
  }
  
  public void upgrade()
  {
	  if (AbstractDungeon.player.hasRelic("Simple Rune"))
	  {
		upgradeDamage(3 + this.timesUpgraded);
		this.timesUpgraded += 1;
		this.upgraded = true;
		this.name = (NAME + "+" + this.timesUpgraded);
		initializeTitle();
	  } else {
		if (!this.upgraded)
		{
		  upgradeName();
		  upgradeDamage(3);
		}
	  }
    
  }
  
  public boolean canUpgrade()
  {
	if (AbstractDungeon.player.hasRelic("Simple Rune"))
	{
		return true;
	}
	if (this.upgraded)
	{
		return false;
	}
    return true;
  }
}
