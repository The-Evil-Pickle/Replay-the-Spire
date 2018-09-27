package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import replayTheSpire.*;

public class DivineProtection
  extends AbstractRelic
{
  public static final String ID = "Divine Protection";
  protected static final int HEALTH_AMT = 8;
  
  
  public DivineProtection()
  {
    super("Divine Protection", "divineProtection.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
	
  }
  @Override
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0] + DivineProtection.HEALTH_AMT + this.DESCRIPTIONS[1];
  }
  
  @Override
  public void atBattleStart()
  {
	ReplayTheSpireMod.addShielding(AbstractDungeon.player, DivineProtection.HEALTH_AMT);
  }
  
  @Override
  public AbstractRelic makeCopy()
  {
    return new DivineProtection();
  }
}
