package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.PowerTip;

public class DivineProtection
  extends AbstractRelic
{
  public static final String ID = "Divine Protection";
  private static final int HEALTH_AMT = 8;
  
  public DivineProtection()
  {
    super("Divine Protection", "betaRelic.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
	
	this.counter = 0;
  }
  
  public void setCounter(int c)
  {
    this.counter = c;
    if (this.counter <= 0) {
      this.description = (this.DESCRIPTIONS[0] + DivineProtection.HEALTH_AMT + this.DESCRIPTIONS[1]);
    } else {
      this.description = (this.DESCRIPTIONS[0] + DivineProtection.HEALTH_AMT + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2] + this.counter + this.DESCRIPTIONS[3]);
    }
    this.tips.clear();
    this.tips.add(new PowerTip(this.name, this.description));
    initializeTips();
  }
  
  @Override
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0] + DivineProtection.HEALTH_AMT + this.DESCRIPTIONS[1];
  }
  
  @Override
  public void onVictory()
  {
    flash();
    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	if (this.counter > 0) {
		//AbstractDungeon.actionManager.addToTop((AbstractGameAction)new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.counter));
		AbstractDungeon.player.damage(new DamageInfo(null, this.counter, DamageInfo.DamageType.HP_LOSS));
	}
    AbstractDungeon.player.decreaseMaxHealth(DivineProtection.HEALTH_AMT);
	setCounter(0);
  }
  
  @Override
  public void atBattleStart()
  {
	flash();
	AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	AbstractDungeon.player.increaseMaxHp(DivineProtection.HEALTH_AMT, true);
	setCounter(DivineProtection.HEALTH_AMT);
  }
  
  @Override
  public void onLoseHp(int damageAmount)
  {
	if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
		if (this.counter > 0){
			int newcount = this.counter - damageAmount;
			if (newcount < 0){
				newcount = 0;
			}
			setCounter(newcount);
		}
	}
  }
  
  @Override
  public AbstractRelic makeCopy()
  {
    return new DivineProtection();
  }
}
