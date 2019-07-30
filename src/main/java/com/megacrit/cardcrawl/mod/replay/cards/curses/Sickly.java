package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import basemod.*;
import basemod.abstracts.*;

public class Sickly
  extends CustomCard
{
  public static final String ID = "Sickly";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Sickly");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = -2;
  
  public Sickly()
  {
    super("Sickly", NAME, "cards/replay/betaCurse.png", -2, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
	
	this.baseMagicNumber = 3;
    this.magicNumber = this.baseMagicNumber;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    if (this.dontTriggerOnUseCard)
    {
      AbstractDungeon.actionManager.addToTop(new LoseBlockAction(p, p, this.magicNumber));
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
    return new Sickly();
  }
  
  public void upgrade() {}
}
