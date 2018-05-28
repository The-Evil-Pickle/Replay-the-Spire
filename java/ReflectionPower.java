package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.graphics.*;

public class ReflectionPower
  extends AbstractPower
{
	public static final String POWER_ID = "Reflection";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Reflection");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied;
	
	
	public ReflectionPower(AbstractCreature owner) {
		this (owner, 1, false);
	}
	public ReflectionPower(AbstractCreature owner, final int amount) {
		this (owner, amount, false);
	}
	public ReflectionPower(AbstractCreature owner, final int amount, final boolean justApplied)
	{
		this.name = NAME;
		this.ID = "Reflection";
		this.owner = owner;
		this.amount = amount;
		this.justApplied = justApplied;
        this.isTurnBased = true;
		this.description = DESCRIPTIONS[0];
		//loadRegion("reflection");
		this.img = new Texture("img/powers/Reflection.png");
	}
  
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
  
    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Reflection"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Reflection", 1));
        }
    }
	
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.owner != null && info.owner != this.owner && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount <= 0 && info.output > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
        return damageAmount;
    }
}
