package com.megacrit.cardcrawl.cards.curses;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class Hallucinations
  extends AbstractCard
{
  public static final String ID = "Hallucinations";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Hallucinations");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = -2;
  private static final int POOL = 1;
  
  public Hallucinations()
  {
    super("Hallucinations", NAME, null, "curse/doubt", -2, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE, 1);
	
	this.exhaust = true;
	this.baseMagicNumber = 3;
    this.magicNumber = this.baseMagicNumber;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    if ((!this.dontTriggerOnUseCard) && (p.hasRelic("Blue Candle")))
    {
      useBlueCandle(p);
    }
    else
    {
      for (int counter = 0; counter < this.magicNumber; counter++){
		  AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
		  int r = MathUtils.random(20);
		  if (r == 20)
		  {
			  rarity = AbstractCard.CardRarity.RARE;
		  } else {
			  if (r >= 15) {
				  rarity = AbstractCard.CardRarity.UNCOMMON;
			  }
		  }
		  AbstractCard c = AbstractDungeon.getCard(rarity).makeCopy();
		  AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p, p, c, 1, true, false));
	  }
      AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
  }
  
  public void triggerOnEndOfTurnForPlayingCard()
  {
    this.dontTriggerOnUseCard = true;
    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, null));
  }
  
  public AbstractCard makeCopy()
  {
    return new Hallucinations();
  }
  
  public void upgrade() {}
}
