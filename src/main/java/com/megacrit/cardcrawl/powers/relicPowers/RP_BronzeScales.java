package com.megacrit.cardcrawl.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class RP_BronzeScales extends AbstractPower
{
    public static final String POWER_ID = "RP_BronzeScales";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private DamageInfo thornsInfo;

    public RP_BronzeScales(final AbstractCreature owner) {
    	this(owner, 3);
    }
    public RP_BronzeScales(final AbstractCreature owner, final int amount) {
        this.name = RP_BronzeScales.NAME;
        this.ID = "Sharp Hide";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("sharpHide");
        this.thornsInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_BronzeScales.DESCRIPTIONS[0] + this.amount + RP_BronzeScales.DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(final int stackAmount) {
        if (this.amount == -1) {
            return;
        }
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.thornsInfo = new DamageInfo(null, this.amount, DamageInfo.DamageType.THORNS);
        this.updateDescription();
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, this.thornsInfo, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
        return damageAmount;
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_BronzeScales");
        NAME = RP_BronzeScales.powerStrings.NAME;
        DESCRIPTIONS = RP_BronzeScales.powerStrings.DESCRIPTIONS;
    }
}
