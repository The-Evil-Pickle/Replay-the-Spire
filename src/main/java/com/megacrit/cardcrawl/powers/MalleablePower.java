package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;

public class MalleablePower extends AbstractPower
{
    public static final String POWER_ID = "Malleable";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final int STARTING_BLOCK = 3;
	private int basePower;
    
    public MalleablePower(final AbstractCreature owner) {
        this(owner, STARTING_BLOCK);
    }
	
	public MalleablePower(final AbstractCreature owner, int amt) {
		this.name = MalleablePower.NAME;
        this.ID = "Malleable";
        this.owner = owner;
        this.amount = amt;
		this.basePower = amt;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/malleable.png");
	}
    
    @Override
    public void updateDescription() {
        this.description = MalleablePower.DESCRIPTIONS[0] + this.amount + MalleablePower.DESCRIPTIONS[1] + MalleablePower.NAME + MalleablePower.DESCRIPTIONS[2] + this.basePower + MalleablePower.DESCRIPTIONS[3];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
		if (this.owner.isPlayer)
			return;
        this.amount = this.basePower;
        this.updateDescription();
    }
	
    @Override
    public void atEndOfRound() {
		if (!this.owner.isPlayer)
			return;
        this.amount = this.basePower;
        this.updateDescription();
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.type != DamageInfo.DamageType.HP_LOSS) {
            this.flash();
			if (this.owner.isPlayer) {
				AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, this.amount));
			} else {
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
			}
            ++this.amount;
            this.updateDescription();
        }
        return damageAmount;
    }
    
	@Override
    public void stackPower(final int stackAmount) {
        this.amount += stackAmount;
		this.basePower += stackAmount;
    }
	
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Malleable");
        NAME = MalleablePower.powerStrings.NAME;
        DESCRIPTIONS = MalleablePower.powerStrings.DESCRIPTIONS;
    }
}
