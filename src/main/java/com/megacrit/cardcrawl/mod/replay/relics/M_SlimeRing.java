package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.BoundBlade;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WristBlade;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import slimebound.orbs.SpawnedSlime;

import com.megacrit.cardcrawl.relics.NinjaScroll;

public class M_SlimeRing extends M_MistRelic
{
    public static final String ID = "m_SlimeRing";
    
    public M_SlimeRing() {
        super(ID, "snakeRing.png", LandingSound.MAGICAL, slimebound.patches.AbstractCardEnum.SLIMEBOUND, CardColor.GREEN);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2, false));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_SlimeRing();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Bane());
        tmpPool.add(new BouncingFlask());
        tmpPool.add(new CripplingPoison());
        tmpPool.add(new DeadlyPoison());
        tmpPool.add(new Envenom());
        tmpPool.add(new NoxiousFumes());
        tmpPool.add(new PoisonedStab());
        tmpPool.add(new PoisonSmokescreen());
        tmpPool.add(new Backstab());
		return tmpPool;
	}
}
