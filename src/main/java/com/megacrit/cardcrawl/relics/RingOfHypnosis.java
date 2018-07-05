package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;
import tobyspowerhouse.powers.*;

public class RingOfHypnosis extends AbstractRelic
{
    public static final String ID = "Ring of Hypnosis";
    private static final int CONF = 5;
    
    public RingOfHypnosis() {
        super("Ring of Hypnosis", "cring_hypnosis.png", RelicTier.SPECIAL, LandingSound.FLAT);
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
