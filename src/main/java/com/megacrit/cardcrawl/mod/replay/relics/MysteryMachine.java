package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.*;

public class MysteryMachine extends AbstractRelic
{
    public static final String ID = "Replay:Mystery Machine";
    private boolean triggeredLast;
	
    public MysteryMachine() {
        super(ID, "betaRelic.png", RelicTier.COMMON, LandingSound.SOLID);
		this.counter = 0;
		this.triggeredLast = false;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onEnterRoom(final AbstractRoom room) {
    	if (room.getMapSymbol() != null && room.getMapSymbol().equals("?")) {
    		this.counter++;
    		this.triggeredLast = true;
    	} else if (this.counter > 0 && !(room instanceof TreasureRoomBoss)) {
    		if (this.triggeredLast) {
    			this.triggeredLast = false;
    		} else {
    			this.counter--;
    		}
    	}
    }
    @Override
    public void atPreBattle() {
    	if (this.counter > 0) {
    		this.flash();
    		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    		if (AbstractDungeon.miscRng.randomBoolean()) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, (int)Math.ceil(this.counter / 2.0)), (int)Math.ceil(this.counter / 2.0)));
    			if (this.counter > 1)
    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, (int)Math.floor(this.counter / 2.0)), (int)Math.floor(this.counter / 2.0)));
    		} else {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, (int)Math.ceil(this.counter / 2.0)), (int)Math.ceil(this.counter / 2.0)));
    			if (this.counter > 1)
    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, (int)Math.floor(this.counter / 2.0)), (int)Math.floor(this.counter / 2.0)));
    		}
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new MysteryMachine();
    }
}
