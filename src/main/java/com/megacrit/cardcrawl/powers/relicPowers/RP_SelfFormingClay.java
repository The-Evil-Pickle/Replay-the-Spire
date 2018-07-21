package com.megacrit.cardcrawl.powers.relicPowers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

public class RP_SelfFormingClay extends AbstractPower
{
    public static final String POWER_ID = "Malleable";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_SelfFormingClay(final AbstractCreature owner) {
    	this(owner, 3);
    }
    
    public RP_SelfFormingClay(final AbstractCreature owner, final int amount) {
        this.name = RP_SelfFormingClay.NAME;
        this.ID = "Malleable";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/malleable.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_SelfFormingClay.DESCRIPTIONS[0] + this.amount + RP_SelfFormingClay.DESCRIPTIONS[1];
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, 3, this.name), 3));
            this.updateDescription();
        }
        return damageAmount;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_SelfFormingClay");
        NAME = RP_SelfFormingClay.powerStrings.NAME;
        DESCRIPTIONS = RP_SelfFormingClay.powerStrings.DESCRIPTIONS;
    }
}