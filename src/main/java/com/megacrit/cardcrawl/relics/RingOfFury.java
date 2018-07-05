package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class RingOfFury
  extends AbstractRelic
{
  public static final String ID = "Ring of Fury";
  private static final int STR = 2;
  private static final int DEX = 1;
  
  public RingOfFury()
  {
    super("Ring of Fury", "redCirclet.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0] + RingOfFury.STR + this.DESCRIPTIONS[1] + RingOfFury.DEX + this.DESCRIPTIONS[2];
  }
  
  public void atBattleStart()
  {
    flash();
    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, RingOfFury.STR), RingOfFury.STR));
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -RingOfFury.DEX), -RingOfFury.DEX));
    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
  }
  
  public AbstractRelic makeCopy()
  {
    return new RingOfFury();
  }
}
