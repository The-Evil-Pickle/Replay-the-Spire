package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
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
import com.megacrit.cardcrawl.powers.DarknessPower;
import java.util.ArrayList;
import basemod.*;
import basemod.abstracts.*;

public class Languid
  extends CustomCard
{
  public static final String ID = "Languid";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Languid");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int COST = -2;
  
  public Languid()
  {
    super("Languid", NAME, "cards/replay/betaCurse.png", -2, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    if ((!this.dontTriggerOnUseCard) && (p.hasRelic("Blue Candle")))
    {
      useBlueCandle(p);
    }
    else
    {
      AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DarknessPower(1), 1));
      
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
        //AbstractDungeon.actionManager.addToBottom(new PlayWithoutDiscardingAction(this));
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
  
  public AbstractCard makeCopy()
  {
    return new Languid();
  }
  
  public void upgrade() {}
}
