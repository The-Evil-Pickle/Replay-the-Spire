package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import java.util.ArrayList;

public class KingOfHearts
  extends AbstractRelic
{
  public static final String ID = "King of Hearts";
  
  boolean isActive = false;
  boolean isChecking = false;
  
  public KingOfHearts()
  {
    super("King of Hearts", "kingOfHearts.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0];
  }
  
  public void atTurnStart()
  {
    isActive = true;
	isChecking = false;
	this.pulse = true;
    beginPulse();
  }
  
  public void onPlayerEndTurn() {
	isChecking = true;
	this.pulse = false;
  }
  
  public void onLoseHp(int damageAmount) {
	if (isActive) {
		if (isChecking && AbstractDungeon.actionManager.turnHasEnded) {
			isActive = false;
			this.pulse = false;
		} else {
			flash();
			AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, damageAmount * 2));
			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
	}
  }
  
  public void onVictory()
  {
    this.pulse = false;
  }
  
  public AbstractRelic makeCopy()
  {
    return new KingOfHearts();
  }
}
