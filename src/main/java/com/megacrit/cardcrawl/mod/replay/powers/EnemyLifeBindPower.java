package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import replayTheSpire.ReplayTheSpireMod;
import com.megacrit.cardcrawl.core.*;

public class EnemyLifeBindPower extends AbstractPower
{
    public static final String POWER_ID = "Replay:Enemy Life Bind";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public EnemyLifeBindPower(final AbstractCreature owner) {
        this.name = EnemyLifeBindPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("lifebind");
        this.type = PowerType.DEBUFF;
    }
    @Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
