package com.megacrit.cardcrawl.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class RP_HourglassPower extends AbstractPower
{
    public static final String POWER_ID = "RP_HourglassPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_HourglassPower(final AbstractCreature owner) {
		this(owner, 3);
	}
    public RP_HourglassPower(final AbstractCreature owner, final int newAmount) {
        this.name = RP_HourglassPower.NAME;
        this.ID = "RP_HourglassPower";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("time");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_HourglassPower.DESCRIPTIONS[0] + this.amount + RP_HourglassPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_HourglassPower");
        NAME = RP_HourglassPower.powerStrings.NAME;
        DESCRIPTIONS = RP_HourglassPower.powerStrings.DESCRIPTIONS;
    }
}
