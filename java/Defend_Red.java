package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Red
  extends AbstractCard
{
  public static final String ID = "Defend_R";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_R");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = 1;
  private static final int BLOCK_AMT = 5;
  private static final int POOL = 0;
  
  public Defend_Red()
  {
    super("Defend_R", NAME, "red/skill/defend", "red/skill/defend", 1, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, 0);
    
    this.baseBlock = 5;
	this.timesUpgraded = 0;
  }
  public Defend_Red(int upgrades)
  {
    super("Defend_R", NAME, "red/skill/defend", "red/skill/defend", 1, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, 0);
    
    this.baseBlock = 5;
	this.timesUpgraded = upgrades;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    if (Settings.isDebug) {
      AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 50));
    } else {
      AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }
  }
  
  public AbstractCard makeCopy()
  {
    return new Defend_Red(this.timesUpgraded);
  }
  
  public void upgrade()
  {
	if (AbstractDungeon.player.hasRelic("Simple Rune"))
	{
		upgradeBlock(3 + this.timesUpgraded);
		this.timesUpgraded += 1;
		this.upgraded = true;
		this.name = (NAME + "+" + this.timesUpgraded);
		initializeTitle();
	} else {
		if (!this.upgraded)
		{
		  upgradeName();
		  upgradeBlock(3);
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
