package com.megacrit.cardcrawl.powers.relicPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.Mango;

public class RP_Mango extends AbstractPower
{
    public static final String POWER_ID = "RP_Mango";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_Mango(final AbstractCreature owner, int amt) {
        this.name = RP_Mango.NAME;
        this.ID = "RP_Mango";
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("unawakened");
    }

    @Override
    public void stackPower(final int stackAmount) {
        if (stackAmount == -1) {
        	this.fontScale = 8.0f;
            this.amount += 14;
            this.updateDescription();
            this.owner.maxHealth += 14;
    		AbstractDungeon.actionManager.addToTop(new HealAction(this.owner, this.owner, 14));
            return;
        }
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.updateDescription();
        this.owner.maxHealth += stackAmount;
		AbstractDungeon.actionManager.addToTop(new HealAction(this.owner, this.owner, stackAmount));
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_Mango.DESCRIPTIONS[0] + this.amount + RP_Mango.DESCRIPTIONS[1];
    }
    @Override
    public void onInitialApplication() {
		this.owner.maxHealth += this.amount;
		AbstractDungeon.actionManager.addToTop(new HealAction(this.owner, this.owner, this.amount));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_Mango");
        NAME = RP_Mango.powerStrings.NAME;
        DESCRIPTIONS = RP_Mango.powerStrings.DESCRIPTIONS;
    }
}