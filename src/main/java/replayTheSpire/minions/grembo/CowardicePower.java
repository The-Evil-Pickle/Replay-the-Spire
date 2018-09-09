package replayTheSpire.minions.grembo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;

public class CowardicePower
  extends AbstractPower
{
  public static final String POWER_ID = "GremboCoward";
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  public CowardicePower(AbstractCreature owner, int amnt)
  {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
    this.amount = amnt;
    this.type = AbstractPower.PowerType.DEBUFF;
    updateDescription();
    loadRegion("corruption");
  }
  
  public void updateDescription()
  {
      this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
  }
  
  public void stackPower(int stackAmount)
  {
	this.fontScale = 8.0F;
	if (stackAmount < amount){
		amount = stackAmount;
	} else {
		if (amount > 1){
			amount = amount - 1;
		}
	}
  }
  
  public void atStartOfTurn()
  {
    amount = amount - 1;
	if (amount <= 0)
	{
	  flash();
	  ((AbstractMonster)this.owner).escapeNext = true;
	}
  }
}
