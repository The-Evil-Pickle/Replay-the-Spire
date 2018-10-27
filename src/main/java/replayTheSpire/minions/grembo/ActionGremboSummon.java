package replayTheSpire.minions.grembo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GrembosGang;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.helpers.BasePlayerMinionHelper;

public class ActionGremboSummon
  extends AbstractGameAction
{
  private boolean freeToPlayOnce = false;
  private int magic;
  private AbstractPlayer p;
  private AbstractMonster m;
  private int energyOnUse = -1;
  
  public ActionGremboSummon(AbstractPlayer p, int magic, boolean freeToPlayOnce, int energyOnUse)
  {
    this.p = p;
    this.magic = magic;
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
    	for (int i=0; i < this.magic; i++) {
    		if(this.p instanceof AbstractPlayerWithMinions) {
                AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) this.p;
                player.addMinion(GrembosGang.GetRandomGremboi(effect));
            } else {
            	BasePlayerMinionHelper.addMinion(this.p, GrembosGang.GetRandomGremboi(effect));
            }
    	}
    }
    this.isDone = true;
  }
}