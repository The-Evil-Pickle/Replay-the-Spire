package com.megacrit.cardcrawl.mod.replay.powers.replayxover;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.ReplayExplosivePower;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class TheDynamitePower extends TwoAmountPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "Replay:TheDynamite";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCard card;
    private static int bombIdOffset;
    
    public TheDynamitePower(final AbstractCreature owner, final int turns, final AbstractCard card) {
        this.name = TheDynamitePower.NAME;
        this.ID = "TheBomb" + TheDynamitePower.bombIdOffset;
        ++TheDynamitePower.bombIdOffset;
        this.owner = owner;
        this.amount = turns;
        this.card = card.makeStatEquivalentCopy();
        this.updateDescription();
        this.loadRegion("the_bomb");
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.amount == 1) {
            	this.card.calculateCardDamage(null);
                this.addToBot(new DamageAllEnemiesAction(this.owner, this.card.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
    
    @Override
    public void updateDescription() {
    	this.card.calculateCardDamage(null);
    	this.amount2 = this.card.multiDamage[0];
        if (this.amount == 1) {
            this.description = String.format(TheDynamitePower.DESCRIPTIONS[1], this.amount2);
        }
        else {
        	this.card.calculateCardDamage(null);
            this.description = String.format(TheDynamitePower.DESCRIPTIONS[0], this.amount, this.amount2);
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("TheBomb");
        NAME = TheDynamitePower.powerStrings.NAME;
        DESCRIPTIONS = TheDynamitePower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new TheDynamitePower(this.owner, this.amount, this.card);
	}
}
