package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.core.*;

public class FriendlyPower extends AbstractPower
{
    public static final String POWER_ID = "Friendly";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public FriendlyPower(final AbstractCreature owner) {
        this.name = FriendlyPower.NAME;
        this.ID = "Friendly";
        this.owner = owner;
        this.amount = -1;
        this.updateDescription();
        this.loadRegion("unawakened");
    }
    
    @Override
    public void updateDescription() {
        this.description = FriendlyPower.DESCRIPTIONS[0];
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Friendly");
        NAME = FriendlyPower.powerStrings.NAME;
        DESCRIPTIONS = FriendlyPower.powerStrings.DESCRIPTIONS;
    }
}
