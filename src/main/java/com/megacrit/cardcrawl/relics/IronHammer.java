package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IronHammer
  extends AbstractRelic
{
  private boolean firstTurn = false;
  public static final String ID = "Iron Hammer";
  
  public IronHammer()
  {
    super("Iron Hammer", "ironHammer.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0];
  }
  
  public void atPreBattle()
  {
    this.firstTurn = true;
    if (!this.pulse)
    {
      beginPulse();
      this.pulse = true;
    }
  }
  
  public void onPlayerEndTurn()
  {
    if (this.pulse)
    {
      this.pulse = false;
      this.firstTurn = false;
    }
  }
  public void onCardDraw(AbstractCard drawnCard) 
  {
	if (this.firstTurn)
	{
		if (drawnCard.canUpgrade())
		  {
			drawnCard.upgrade();
			drawnCard.superFlash();
			flash();
		  }
	}
  }
  public AbstractRelic makeCopy()
  {
    return new IronHammer();
  }
}
