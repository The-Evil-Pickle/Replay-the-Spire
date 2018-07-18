package com.megacrit.cardcrawl.powers.relicPowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
    public void updateDescription() {
        this.description = RP_Mango.DESCRIPTIONS[0] + this.amount + RP_Mango.DESCRIPTIONS[1];
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_Mango");
        NAME = RP_Mango.powerStrings.NAME;
        DESCRIPTIONS = RP_Mango.powerStrings.DESCRIPTIONS;
    }
}