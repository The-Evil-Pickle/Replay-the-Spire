package com.megacrit.cardcrawl.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.dungeons.*;
import replayTheSpire.*;

public class DivineProtection_stslib
  extends DivineProtection
{
  
  public DivineProtection_stslib()
  {
    super();
  }
  
  @Override
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0] + DivineProtection.HEALTH_AMT + this.DESCRIPTIONS[2];
  }
  
  @Override
  public void atBattleStart()
  {
	AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, DivineProtection.HEALTH_AMT));
  }
  
  @Override
  public AbstractRelic makeCopy()
  {
    return new DivineProtection_stslib();
  }
}
