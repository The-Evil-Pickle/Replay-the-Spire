package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Collections;

public class SimpleRune
  extends AbstractRelic
{
  public static final String ID = "Simple Rune";
  private static final int CARD_AMT = 2;
  
  public SimpleRune()
  {
    super("Simple Rune", "warPaint.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
  }
  
  public void onEquip()
  {
    ArrayList<AbstractCard> upgradableCards = new ArrayList();
    for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
      if (c.cardID == "Strike_R" || c.cardID == "Strike_G" || c.cardID == "Strike_B" || c.cardID == "Defend_R" || c.cardID == "Defend_G" || c.cardID == "Defend_B") {
        upgradableCards.add(c);
      }
    }
    Collections.shuffle(upgradableCards, new java.util.Random(AbstractDungeon.miscRng.randomLong()));
    if (!upgradableCards.isEmpty()) {
      if (upgradableCards.size() == 1)
      {
        ((AbstractCard)upgradableCards.get(0)).upgrade();
        AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
        AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(
          ((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
      }
      else
      {
        ((AbstractCard)upgradableCards.get(0)).upgrade();
        ((AbstractCard)upgradableCards.get(1)).upgrade();
        AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
        AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(1));
        AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(
        
          ((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
        
        AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(
        
          ((AbstractCard)upgradableCards.get(1)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
        
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
      }
    }
  }
  
  public AbstractRelic makeCopy()
  {
    return new SimpleRune();
  }
}