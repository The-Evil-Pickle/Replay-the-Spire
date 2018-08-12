package com.megacrit.cardcrawl.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class RP_VajraPower extends AbstractPower
{
    public static final String POWER_ID = "RP_VajraPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_VajraPower(final AbstractCreature owner) {
        this.name = RP_VajraPower.NAME;
        this.ID = "RP_VajraPower";
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
        this.loadRegion("unawakened");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_VajraPower.DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!this.owner.isPlayer) {
        	this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
			this.amount--;
        }
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.owner.isPlayer && this.amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
			this.amount--;
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_VajraPower");
        NAME = RP_VajraPower.powerStrings.NAME;
        DESCRIPTIONS = RP_VajraPower.powerStrings.DESCRIPTIONS;
    }
}
