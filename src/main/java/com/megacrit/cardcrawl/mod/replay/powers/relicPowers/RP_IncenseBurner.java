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

public class RP_IncenseBurner extends AbstractPower
{
    public static final String POWER_ID = "RP_IncenseBurner";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_IncenseBurner(final AbstractCreature owner) {
        this.name = RP_IncenseBurner.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 6;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/ghost.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_IncenseBurner.DESCRIPTIONS[0];
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
			this.amount--;
			if (this.amount <= 0) {
				this.amount = 6;
	            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
			}
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = RP_IncenseBurner.powerStrings.NAME;
        DESCRIPTIONS = RP_IncenseBurner.powerStrings.DESCRIPTIONS;
    }
}
