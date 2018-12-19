package com.megacrit.cardcrawl.mod.replay.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.BagOfTricksAction;
import com.megacrit.cardcrawl.mod.replay.actions.unique.PoisonDartsAction;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import basemod.abstracts.CustomCard;

public class BagOfTricks extends CustomCard
{
	  public static final String ID = "Replay:Bag of Tricks";
	  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	  public static final String NAME = cardStrings.NAME;
	  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	  public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	  public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	  private static final int COST = -1;
	  
	  public BagOfTricks()
	  {
	    super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
	    this.baseMagicNumber = 4;
	    this.baseBlock = 7;
	    this.block = this.baseBlock;
	    this.magicNumber = this.baseMagicNumber;
	  }
	  
	  public void use(AbstractPlayer p, AbstractMonster m)
	  {
		  if (!this.upgraded) {
			  AbstractDungeon.actionManager.addToBottom(new BagOfTricksAction(p, m, this, this.magicNumber + 1, this.magicNumber - 1, this.block, this.magicNumber - 2, 2, this.freeToPlayOnce, this.energyOnUse));
		  } else {
			  AbstractDungeon.actionManager.addToBottom(new BagOfTricksAction(p, m, this, this.magicNumber + 2, this.magicNumber - 1, this.block, this.magicNumber - 2, 3, this.freeToPlayOnce, this.energyOnUse));
		  }
	  }
	  
	  public AbstractCard makeCopy()
	  {
	    return new BagOfTricks();
	  }
	  
	  public void upgrade()
	  {
	    if (!this.upgraded)
	    {
	      upgradeName();
	      this.upgradeMagicNumber(1);
	      this.upgradeBlock(3);
	    }
	  }
	}