package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import infinitespire.InfiniteSpire;
import replayTheSpire.ReplayTheSpireMod;

public class GhostHeart extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:GhostHeart";
    static final int INTANG = 2;
    
    public GhostHeart() {
        super(ID, "betaRelic.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + INTANG + this.DESCRIPTIONS[1];
    }

    @Override
    public void onTrigger() {
        doShielding(1);
        AbstractDungeon.player.heal(1, true);
        /*if (ReplayTheSpireMod.foundmod_infinite) {
        	try {
				triggerDieQuest();
			} catch (ClassNotFoundException | NoClassDefFoundError e) {
				// TODO Auto-generated catch block
			}
        }*/
    }
    
   /* private static void triggerDieQuest() throws ClassNotFoundException, NoClassDefFoundError {
		Class<InfiniteSpire> infiniteMod = InfiniteSpire.class;
		for (final infinitespire.abstracts.Quest q : infinitespire.InfiniteSpire.questLog) {
            if (q instanceof infinitespire.quests.DieQuest) {
                ((infinitespire.quests.DieQuest)q).onPlayerDie();
            }
        }
	}*/
    
    @Override
    public void atPreBattle() {
    	if (AbstractDungeon.player.currentHealth == 1) {
    		doShielding(0);
    	} else {
        	this.setCounter(-1);
        	this.beginPulse();
            this.pulse = true;
    	}
    }
    private void doShielding(int intanmod) {
        this.flash();
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, INTANG + intanmod), INTANG + intanmod));
    	AbstractDungeon.actionManager.addToTop(new ReplayGainShieldingAction(AbstractDungeon.player, AbstractDungeon.player, Math.max(AbstractDungeon.player.maxHealth / 10 - 1, 1)));
    	AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    	this.setCounter(-2);
    	this.pulse = false;
    }
    
    @Override
    public void onVictory() {
        this.pulse = false;
    }
    
    public AbstractRelic makeCopy() {
        return new GhostHeart();
    }
}
