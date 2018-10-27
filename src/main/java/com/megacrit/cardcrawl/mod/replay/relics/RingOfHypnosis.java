package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;

import java.util.*;
import tobyspowerhouse.powers.*;

public class RingOfHypnosis extends AbstractRelic
{
    public static final String ID = "Ring of Hypnosis";
    private static final int CONF = 5;
    
    public RingOfHypnosis() {
        super("Ring of Hypnosis", "cring_hypnosis.png", RelicTier.SPECIAL, LandingSound.FLAT);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Confusion", "When applied to enemies, #yConfusion gives a random attack damage modifier each round, ranging from #b-3 to #b+2."));
        this.initializeTips();
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfHypnosis.CONF + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            //AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(mo, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new TPH_ConfusionPower(mo, RingOfHypnosis.CONF, false), RingOfHypnosis.CONF, true));
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfHypnosis();
    }
}
