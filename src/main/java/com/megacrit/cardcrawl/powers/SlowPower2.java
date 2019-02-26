package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
//import com.megacrit.cardcrawl.powers.PowerType;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class SlowPower2 extends AbstractPower
{
    public static final String POWER_ID = "Slow";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
	private int baseAmount;
    
    public SlowPower2(final AbstractCreature owner, final int amount) {
        this(owner, amount, 0);
    }
	
    public SlowPower2(final AbstractCreature owner, final int amount, final int baseAmount) {
        this.name = SlowPower.NAME;
        this.ID = "Slow";
		this.baseAmount = baseAmount;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("slow");
        //this.img = ImageMaster.loadImage("images/powers/32/slow.png");
        this.type = PowerType.DEBUFF;
        this.canGoNegative = true;
    }
    
    @Override
    public void atEndOfRound() {
        this.amount = this.baseAmount;
        this.updateDescription();
    }
    
    @Override
    public void updateDescription() {
		this.description = SlowPower.DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + SlowPower.DESCRIPTIONS[1];
		if (this.amount < this.baseAmount) {
			this.type = PowerType.BUFF;
			this.description = this.description + SlowPower.DESCRIPTIONS[2] + this.amount * 10 + SlowPower.DESCRIPTIONS[3];
		} else {
			this.type = PowerType.DEBUFF;
			if (this.amount != 0) {
				this.description = this.description + SlowPower.DESCRIPTIONS[2] + this.amount * 10 + SlowPower.DESCRIPTIONS[3];
			}
		}
    }
    
    @Override
    public void onAfterUseCard(final AbstractCard card, final UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SlowPower(this.owner, 1), 1));
    }
    
    @Override
    public float atDamageReceive(final float damage, final DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1.0f + this.amount * 0.1f);
        }
        return damage;
    }
	
	@Override
	public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Slow");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
