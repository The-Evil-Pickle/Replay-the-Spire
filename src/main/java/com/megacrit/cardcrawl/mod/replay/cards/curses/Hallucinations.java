package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import basemod.*;
import basemod.abstracts.*;

public class Hallucinations
  extends CustomCard
{
  public static final String ID = "Hallucinations";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Hallucinations");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int EXHAUSTIVE = 3;
  
  public Hallucinations()
  {
    super("Hallucinations", NAME, "cards/replay/hallucinations.png", -2, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
	ExhaustiveVariable.setBaseValue(this, EXHAUSTIVE);
	this.baseMagicNumber = 1;
    this.magicNumber = this.baseMagicNumber;
  }
  
	@Override
	public void use(AbstractPlayer p, AbstractMonster m)
	{
		if ((!this.dontTriggerOnUseCard) && (p.hasRelic("Blue Candle")))
		{
		  useBlueCandle(p);
		}
		else
		{
			for (int i =0; i < this.magicNumber; i++) {
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
				  AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, true, false));
			}
		  //Exhaustive.increment(this);
		}
	}
  
    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
    
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        //AbstractDungeon.actionManager.addToBottom(new PlayWithoutDiscardingAction(this));
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    
  public AbstractCard makeCopy()
  {
    return new Hallucinations();
  }
  
  public void upgrade() {}
}
