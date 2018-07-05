package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;

public class RingOfSearing extends AbstractRelic
{
    public static final String ID = "Ring of Searing";
    public static final int HP_FLOOR_ENEMY = 50;
	public static final int HP_LOSS_ENEMY = 10;
	public static final int HP_FLOOR_PLAYER = 75;
	public static final int HP_LOSS_PLAYER = 5;
    
	private boolean isFirstTurn;
	
    public RingOfSearing() {
        super("Ring of Searing", "cring_searing.png", RelicTier.SPECIAL, LandingSound.FLAT);
		this.isFirstTurn = true;
    }
    
    @Override
    public void atPreBattle() {
		this.isFirstTurn = true;
	}
	
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RingOfSearing.HP_FLOOR_ENEMY + this.DESCRIPTIONS[1] + RingOfSearing.HP_LOSS_ENEMY + this.DESCRIPTIONS[2] + RingOfSearing.HP_FLOOR_PLAYER + this.DESCRIPTIONS[3] + RingOfSearing.HP_LOSS_PLAYER + this.DESCRIPTIONS[4];
    }
    
    @Override
    public void atTurnStart() {
		this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new SearingRingAction(AbstractDungeon.player, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.FIRE, this.isFirstTurn));
		this.isFirstTurn = false;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RingOfSearing();
    }
}
