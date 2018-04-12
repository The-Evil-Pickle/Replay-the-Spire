package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class PoisonDartsAction
  extends AbstractGameAction
{
  private boolean freeToPlayOnce = false;
  private int damage;
  private AbstractPlayer p;
  private AbstractMonster m;
  private int energyOnUse = -1;
  
  public PoisonDartsAction(AbstractPlayer p, AbstractMonster m, int damage, boolean freeToPlayOnce, int energyOnUse)
  {
    this.p = p;
    this.m = m;
    this.damage = damage;
    this.freeToPlayOnce = freeToPlayOnce;
    this.duration = Settings.ACTION_DUR_XFAST;
    this.actionType = AbstractGameAction.ActionType.SPECIAL;
    this.energyOnUse = energyOnUse;
  }
  
  public void update()
  {
    int effect = EnergyPanel.totalCount;
    if (this.energyOnUse != -1) {
      effect = this.energyOnUse;
    }
    if (this.p.hasRelic("Chemical X"))
    {
      effect += 2;
      this.p.getRelic("Chemical X").flash();
    }
    if (effect > 0)
    {
		//AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.p, new NecroticPoisonPower(this.m, this.p, effect), effect, AbstractGameAction.AttackEffect.POISON));
      for (int i = 0; i < (effect); i++) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.p, new NecroticPoisonPower(this.m, this.p, this.damage), this.damage, AbstractGameAction.AttackEffect.POISON));
		/*
		for (AbstractMonster mm : AbstractDungeon.getMonsters().monsters) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mm, this.p, new NecroticPoisonPower(mm, this.p, this.damage), this.damage, AbstractGameAction.AttackEffect.POISON));
		}
		*/
      }
      if (!this.freeToPlayOnce) {
        this.p.energy.use(EnergyPanel.totalCount);
      }
    }
    this.isDone = true;
  }
}
