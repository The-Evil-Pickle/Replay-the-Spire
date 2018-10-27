package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.*;

import java.util.*;

public class RingOfPeace extends AbstractRelic
{
    public static final String ID = "Ring of Peace";
    private static final int WEAK = 3;
    private static final int BUFF = 1;
    
    public RingOfPeace() {
        super("Ring of Peace", "cring_peace.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfPeace.WEAK + this.DESCRIPTIONS[1] + RingOfPeace.BUFF + this.DESCRIPTIONS[2];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, RingOfPeace.WEAK, false), RingOfPeace.WEAK, true));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, RingOfPeace.BUFF), RingOfPeace.BUFF, true));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            //AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(mo, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, RingOfPeace.WEAK, false), RingOfPeace.WEAK, true));
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfPeace();
    }
}
