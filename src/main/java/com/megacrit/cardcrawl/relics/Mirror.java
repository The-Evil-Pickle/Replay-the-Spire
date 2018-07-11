package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import java.util.Iterator;

public class Mirror extends AbstractRelic {
    public static final String ID = "Mirror";

    public Mirror()
	{
        super("Mirror", "replay_mirror.png", RelicTier.RARE, LandingSound.CLINK);
    }
/*
  public void atBattleStart()
  {
    flash();
    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    AbstractDungeon.player.addPower(new ReflectionPower(AbstractDungeon.player));
  }
*/
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0];
  }


  public AbstractRelic makeCopy()
  {
    return new Mirror();
  }
}
