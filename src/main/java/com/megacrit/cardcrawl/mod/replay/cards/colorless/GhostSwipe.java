package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;

public class GhostSwipe
  extends AbstractCard
{
  public static final String ID = "Ghost Swipe";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghost Swipe");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  private static final int ATTACK_DMG = 6;
  private static final int UPG_DMG = 3;
  private static final int POOL = 0;
  
  public GhostSwipe()
  {
    super("Ghost Swipe", NAME, "colorless/attack/swiftStrike", "colorless/attack/swiftStrike", 1, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);
    
    this.baseDamage = 6;
    this.exhaust = true;
    this.isEthereal = true;
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
  }
  
  public void triggerOnEndOfPlayerTurn()
  {
    AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
  }
  
  public AbstractCard makeCopy()
  {
    return new GhostSwipe();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      upgradeDamage(3);
    }
  }
}