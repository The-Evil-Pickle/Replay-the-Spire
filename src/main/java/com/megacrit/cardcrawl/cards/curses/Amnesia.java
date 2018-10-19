package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import basemod.*;
import basemod.abstracts.*;

public class Amnesia
  extends CustomCard
{
  public static final String ID = "Amnesia";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Amnesia");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = -2;
  
  public Amnesia()
  {
    super("Amnesia", NAME, "cards/replay/betaCurse.png", -2, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
	this.exhaust = true;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    if ((!this.dontTriggerOnUseCard) && (p.hasRelic("Blue Candle")))
    {
      useBlueCandle(p);
    }
    else
    {
	  
		final int count = AbstractDungeon.player.hand.size();
		for (int i = 0; i < count; ++i) {
			AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, true, true));
		}
		//AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.limbo));
		//AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
		//AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
      //AbstractDungeon.actionManager.addToTop(new LoseBlockAction(p, p, this.magicNumber));
      
      //AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
  }
	
    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
    
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
  
  public AbstractCard makeCopy()
  {
    return new Amnesia();
  }
  
  public void upgrade() {}
}
