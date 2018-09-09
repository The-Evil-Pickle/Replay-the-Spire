package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.replay.FadingForestBoss;
import com.megacrit.cardcrawl.powers.*;
import java.util.ArrayList;
import basemod.*;
import basemod.abstracts.*;

public class CommonCold
  extends CustomCard
{
  public static final String ID = "CommonCold";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = 0;
  
  public CommonCold()
  {
    super(ID, NAME, "cards/replay/betaCurse.png", COST, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
	if (p.hasRelic("Medical Kit")) {
	    this.useMedicalKit(p);
	}
	else
	{
		AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "ah- @CHOO!@", 2.0f, 2.0f));
	}
  }

  public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
      return this.cardPlayable(m) && this.hasEnoughEnergy();
  }
  public AbstractCard makeCopy()
  {
    return new CommonCold();
  }
  
  public void upgrade() {}
}
