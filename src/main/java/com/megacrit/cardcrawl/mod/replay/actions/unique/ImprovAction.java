package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;

public class ImprovAction
  extends AbstractGameAction
{
  public static int numPlaced;
  private boolean retrieveCard = false;
  
  private boolean isUpgraded = false;
  
  public ImprovAction(boolean isUp)
  {
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.duration = Settings.ACTION_DUR_FAST;
	this.isUpgraded = isUp;
  }
  
  public void update()
  {
    if (this.duration == Settings.ACTION_DUR_FAST)
    {
      AbstractDungeon.cardRewardScreen.codexOpen();
      tickDuration();
      return;
    }
    if (!this.retrieveCard)
    {
      if (AbstractDungeon.cardRewardScreen.codexCard != null)
      {
        AbstractCard codexCard = AbstractDungeon.cardRewardScreen.codexCard.makeStatEquivalentCopy();
		if (this.isUpgraded)
		{
		  if (codexCard.canUpgrade())
		  {
			codexCard.upgrade();
		  }
		}
		if (codexCard.costForTurn > 0)
		{
		codexCard.cost -= 1;
		codexCard.costForTurn -= 1;
		codexCard.isCostModified = true;
		}
        codexCard.current_x = (-1000.0F * Settings.scale);
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(codexCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        
        AbstractDungeon.cardRewardScreen.codexCard = null;
      }
      this.retrieveCard = true;
    }
    tickDuration();
  }
}
