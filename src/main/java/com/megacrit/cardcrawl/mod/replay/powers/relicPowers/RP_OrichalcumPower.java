package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.*;

public class RP_OrichalcumPower extends AbstractPower
{
    public static final String POWER_ID = "RP_OrichalcumPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_OrichalcumPower(final AbstractCreature owner) {
		this(owner, 6);
	}
    public RP_OrichalcumPower(final AbstractCreature owner, final int newAmount) {
        this.name = RP_OrichalcumPower.NAME;
        this.ID = "RP_OrichalcumPower";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/malleable.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_OrichalcumPower.DESCRIPTIONS[0] + this.amount + RP_OrichalcumPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (this.owner.currentBlock <= 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_OrichalcumPower");
        NAME = RP_OrichalcumPower.powerStrings.NAME;
        DESCRIPTIONS = RP_OrichalcumPower.powerStrings.DESCRIPTIONS;
    }
}
