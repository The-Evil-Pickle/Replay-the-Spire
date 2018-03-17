package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.GhostSwipe;
import com.megacrit.cardcrawl.cards.colorless.GhostDefend;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

public class PetGhost
  extends AbstractRelic
{
  public static final String ID = "Pet Ghost";
  public static final float RARE_CHANCE = 0.3F;
  
  public PetGhost()
  {
    super("Pet Ghost", "lives.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0];
  }
  
  public void atTurnStart() 
  {
	AbstractCard c;
	if (AbstractDungeon.cardRandomRng.randomBoolean()){
		c = new GhostSwipe();
	} else {
		c = new GhostDefend();
	}
	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, false));
    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
  }
  
  public AbstractRelic makeCopy()
  {
    return new PetGhost();
  }
}
