package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.SwiftStrike;
import com.megacrit.cardcrawl.cards.colorless.PoisonedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PerfectedStrike
  extends AbstractCard
{
  public static final String ID = "Perfected Strike";
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Perfected Strike");
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
  public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
  private static final int COST = 2;
  private static final int DAMAGE_AMT = 6;
  private static final int POOL = 1;
  public static final int BONUS = 2;
  public static final int UPG_BONUS = 3;
  
  public PerfectedStrike()
  {
    super("Perfected Strike", NAME, "red/attack/perfectedStrike", "red/attack/perfectedStrike", 2, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, 1);
    
    this.baseDamage = 6;
    this.baseMagicNumber = 2;
    this.magicNumber = this.baseMagicNumber;
  }
  
  public static int countCards()
  {
    int count = 0;
    for (AbstractCard c : AbstractDungeon.player.hand.group) {
      if (isStrike(c)) {
        count++;
      }
    }
    for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
      if (isStrike(c)) {
        count++;
      }
    }
    for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
      if (isStrike(c)) {
        count++;
      }
    }
    return count;
  }
  
  public static boolean isStrike(AbstractCard c)
  {
    return ((c instanceof Strike_Red)) || ((c instanceof SwiftStrike)) || ((c instanceof PerfectedStrike)) || ((c instanceof PommelStrike)) || ((c instanceof WildStrike)) || ((c instanceof TwinStrike)) || ((c instanceof PoisonedStrike));
  }
  
  public void use(AbstractPlayer p, AbstractMonster m)
  {
    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
  }
  
  public AbstractCard makeCopy()
  {
    return new PerfectedStrike();
  }
  
  public void upgrade()
  {
    if (!this.upgraded)
    {
      upgradeName();
      upgradeMagicNumber(1);
      this.rawDescription = UPGRADE_DESCRIPTION;
      initializeDescription();
    }
  }
}
