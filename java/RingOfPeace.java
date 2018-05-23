package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
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
