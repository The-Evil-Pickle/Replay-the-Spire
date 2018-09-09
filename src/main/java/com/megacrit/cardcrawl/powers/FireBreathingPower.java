package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class FireBreathingPower extends AbstractPower
{
    public static final String POWER_ID = "Fire Breathing";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public FireBreathingPower(final AbstractCreature owner, final int newAmount) {
        this.name = FireBreathingPower.NAME;
        this.ID = "Fire Breathing";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("firebreathing");
    }
    
    @Override
    public void updateDescription() {
        this.description = FireBreathingPower.DESCRIPTIONS[0] + this.amount + FireBreathingPower.DESCRIPTIONS[1] + this.amount + FireBreathingPower.DESCRIPTIONS[2];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        int count = 0;
        for (final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                ++count;
            }
        }
        if (count > 0) {
            this.flash();
            for (int i = 0; i < count; ++i) {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount + (i * this.amount), true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
            }
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Fire Breathing");
        NAME = FireBreathingPower.powerStrings.NAME;
        DESCRIPTIONS = FireBreathingPower.powerStrings.DESCRIPTIONS;
    }
}
