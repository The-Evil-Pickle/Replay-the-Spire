package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.ChaosEvent;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.*;

public class RingOfFangs extends AbstractRelic
{
    public static final String ID = "Ring of Fangs";
    private static final int STR = 3;
    
    public RingOfFangs() {
        super("Ring of Fangs", "cring_fangs.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfFangs.STR + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -RingOfFangs.STR), -RingOfFangs.STR));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnvenomPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public int getPrice() {
    	return ChaosEvent.RING_SHOP_PRICE;
    }
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfFangs();
    }
}
