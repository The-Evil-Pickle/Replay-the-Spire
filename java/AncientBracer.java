package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class AncientBracer
  extends AbstractRelic
{
  public static final String ID = "Ancient Bracer";
  private static final int ARTIF = 2;
  
  public AncientBracer()
  {
    super("Ancient Bracer", "betaRelic.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0] + AncientBracer.ARTIF + this.DESCRIPTIONS[1];
  }
  
  public void atBattleStart()
  {
    flash();
    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, AncientBracer.ARTIF), AncientBracer.ARTIF));
  }
  
  public AbstractRelic makeCopy()
  {
    return new AncientBracer();
  }
}
