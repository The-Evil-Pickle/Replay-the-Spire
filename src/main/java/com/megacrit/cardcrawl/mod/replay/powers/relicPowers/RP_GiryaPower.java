package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;

public class RP_GiryaPower extends AbstractPower
{
    public static final String POWER_ID = "RP_GiryaPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_GiryaPower(final AbstractCreature owner) {
        this.name = RP_GiryaPower.NAME;
        this.ID = "RP_GiryaPower";
        this.owner = owner;
        this.amount = 3;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/formOfBull.png");
        //this.loadRegion("unawakened");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_GiryaPower.DESCRIPTIONS[0] + this.amount + RP_GiryaPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
			this.amount--;
			this.updateDescription();
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_GiryaPower");
        NAME = RP_GiryaPower.powerStrings.NAME;
        DESCRIPTIONS = RP_GiryaPower.powerStrings.DESCRIPTIONS;
    }
}
