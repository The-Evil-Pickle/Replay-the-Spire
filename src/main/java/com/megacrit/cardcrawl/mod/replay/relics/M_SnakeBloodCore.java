package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.BoundBlade;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WristBlade;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import slimebound.orbs.SpawnedSlime;

import com.megacrit.cardcrawl.relics.NinjaScroll;

public class M_SnakeBloodCore extends AbstractRelic
{
    public static final String ID = "m_SnakeBloodCore";
    private static final int NUM_SLOTS = 2;
    private static final int HEAL_AMT = 3;
    
    public M_SnakeBloodCore() {
        super(ID, "snakeRing.png", RelicTier.STARTER, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + NUM_SLOTS + this.DESCRIPTIONS[1] + HEAL_AMT + this.DESCRIPTIONS[2];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1, false));
        AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(NUM_SLOTS));
        
    }

    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (AbstractDungeon.player.currentHealth > 0) {
        	AbstractDungeon.player.heal(HEAL_AMT);
        }
    }
	@Override
    public void onEquip() {
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new DualWield());
        tmpPool.add(new InfernalBlade());
        tmpPool.add(new Havoc());
        tmpPool.add(new BurningPact());
        tmpPool.add(new Offering());
        tmpPool.add(new Eclipse());
        tmpPool.add(new LeadingStrike());
        tmpPool.add(new PerfectedStrike());
        tmpPool.add(new Adrenaline());
        tmpPool.add(new BulletTime());
        tmpPool.add(new Distraction());
        tmpPool.add(new Nightmare());
        tmpPool.add(new Burst());
        tmpPool.add(new Amplify());
        tmpPool.add(new Reflex());
        tmpPool.add(new DaggerThrow());
        tmpPool.add(new HelloWorld());
        tmpPool.add(new ReplayGoodbyeWorld());
        tmpPool.add(new Reboot());
        tmpPool.add(new Recycle());
        tmpPool.add(new Reprogram());
        tmpPool.add(new Seek());
        tmpPool.add(new WhiteNoise());
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
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
			}
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_SnakeBloodCore();
    }
}
