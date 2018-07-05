package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import java.lang.*;

public class RetainSomeBlockPower extends AbstractPower
{
    public static final String POWER_ID = "Retain Some Block";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RetainSomeBlockPower(final AbstractCreature owner, final int armorAmt, final String newName) {
        this.name = newName;
        this.ID = "Retain Some Block";
        this.owner = owner;
        this.amount = armorAmt;
        this.updateDescription();
        this.loadRegion("defenseNext");
    }
    
    public RetainSomeBlockPower(final AbstractCreature owner, final int armorAmt) {
        this(owner, armorAmt, RetainSomeBlockPower.NAME);
    }
    
    @Override
    public void updateDescription() {
        this.description = RetainSomeBlockPower.DESCRIPTIONS[0] + this.amount + RetainSomeBlockPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atStartOfTurn() {
		if (this.owner.currentBlock > 0 && !this.owner.hasPower("Blur") && !this.owner.hasPower("Barricade")) {
			this.flash();
			AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
			if (this.owner instanceof AbstractPlayer && ((AbstractPlayer)this.owner).hasRelic("Calipers") && this.owner.currentBlock > Calipers.BLOCK_LOSS) {
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, Math.min(Math.min(this.amount, Calipers.BLOCK_LOSS), this.owner.currentBlock - Calipers.BLOCK_LOSS)));
			} else {
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, Math.min(this.amount, this.owner.currentBlock)));
			}
		}
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Retain Some Block");
        NAME = RetainSomeBlockPower.powerStrings.NAME;
        DESCRIPTIONS = RetainSomeBlockPower.powerStrings.DESCRIPTIONS;
    }
}
