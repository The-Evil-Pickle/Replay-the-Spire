package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import madsciencemod.actions.common.*;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.BattleTrance;
import com.megacrit.cardcrawl.cards.red.BloodForBlood;
import com.megacrit.cardcrawl.cards.red.Bloodletting;
import com.megacrit.cardcrawl.cards.red.Brutality;
import com.megacrit.cardcrawl.cards.red.Combust;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.cards.red.Immolate;
import com.megacrit.cardcrawl.cards.red.Offering;
import com.megacrit.cardcrawl.cards.red.Reaper;
import com.megacrit.cardcrawl.cards.red.Rupture;
import com.megacrit.cardcrawl.core.*;

import java.util.ArrayList;

import com.megacrit.cardcrawl.characters.*;

public class ChemicalBlood extends AbstractRelic
{
    public static final String ID = "m_ChemicalBlood";
    private static final int MAX_HEALTH_AMT = 8;
    boolean isActive = false;
    boolean isChecking = false;
    
    public ChemicalBlood() {
        super("m_ChemicalBlood", "sizzlingBlood.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ChemicalBlood.MAX_HEALTH_AMT + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        final AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0 && p.hasPower("Fuel")) {
            p.heal(Math.min(p.getPower("Fuel").amount, ChemicalBlood.MAX_HEALTH_AMT));
        }
    }
    
    @Override
    public void onPlayerEndTurn() {
	  	isChecking = true;
	  	this.pulse = false;
    }
    
    @Override
    public void onLoseHp(int damageAmount) {
	  	if (isActive) {
	  		if(AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
	  			isActive = false;
	  			this.pulse = false;
	  			return;
	  		}
	  		if (isChecking && AbstractDungeon.actionManager.turnHasEnded) {
	  			isActive = false;
	  			this.pulse = false;
	  		} else {
	  			flash();
	  			if (ReplayTheSpireMod.foundmod_science) {
	  				AbstractDungeon.actionManager.addToTop((AbstractGameAction)new GainFuelAction(1));
	  			}
	  			//AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, damageAmount * 2));
	  			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	  		}
	  	}
    }

    public void atTurnStart()
    {
	    isActive = true;
	  	isChecking = false;
	  	this.pulse = true;
	    beginPulse();
    }
    

	@Override
    public void onEquip() {
		//AbstractDungeon.rareRelicPool.add("Magic Flower");
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
		tmpPool.add(new BattleTrance());
		tmpPool.add(new BloodForBlood());
		tmpPool.add(new Bloodletting());
		tmpPool.add(new Brutality());
		tmpPool.add(new Combust());
		tmpPool.add(new Hemokinesis());
		tmpPool.add(new Immolate());
		tmpPool.add(new Offering());
		tmpPool.add(new Reaper());
		tmpPool.add(new Rupture());
		tmpPool.add(new Hemogenesis());
        for (final AbstractCard c : tmpPool) {
			switch (c.rarity) {
				case COMMON: {
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
				case UNCOMMON: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
				case RARE: {
					AbstractDungeon.rareCardPool.addToTop(c);
					AbstractDungeon.srcRareCardPool.addToBottom(c);
					continue;
				}
				default: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
			}
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ChemicalBlood();
    }
}
