package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import theAct.powers.SlothfulPower;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;

import java.util.*;
import tobyspowerhouse.powers.*;

public class RingOfSloth extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Ring of Sloth";
    public static final int START_SLOTH = 1;
    public static final int POWER_REFUND = 1;
    public RingOfSloth() {
        super(ID, "cring_sloth.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfSloth.START_SLOTH + this.DESCRIPTIONS[1] + POWER_REFUND + ".";
    }
    @Override
    public void atBattleStartPreDraw() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlothfulPower(AbstractDungeon.player, START_SLOTH), START_SLOTH, true));
    	for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
    		if (c.type == CardType.POWER) {
    			RefundVariable.upgrade(c, POWER_REFUND);
    			if (RefundFields.refund.get(c) <= POWER_REFUND) {
    				c.rawDescription = c.rawDescription + " NL Refund !stslib:refund!.";
    				c.initializeDescription();
    			}
    		}
    	}
    }
    @Override
    public void onShuffle() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlothfulPower(AbstractDungeon.player, 1), 1));
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    }
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfSloth();
    }
}
