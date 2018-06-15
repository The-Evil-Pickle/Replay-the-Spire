package com.megacrit.cardcrawl.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class RP_SmoothStonePower extends AbstractPower
{
    public static final String POWER_ID = "RP_SmoothStonePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_SmoothStonePower(final AbstractCreature owner) {
        this.name = RP_SmoothStonePower.NAME;
        this.ID = "RP_SmoothStonePower";
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
        this.loadRegion("unawakened");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_SmoothStonePower.DESCRIPTIONS[0];
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 1), 1));
			this.amount--;
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_SmoothStonePower");
        NAME = RP_SmoothStonePower.powerStrings.NAME;
        DESCRIPTIONS = RP_SmoothStonePower.powerStrings.DESCRIPTIONS;
    }
}
