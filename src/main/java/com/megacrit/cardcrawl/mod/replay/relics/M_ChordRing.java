package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.bard.cards.*;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PaperCrane;

public class M_ChordRing extends M_MistRelic
{
    public static final String ID = "m_ChordRing";
    
    public M_ChordRing() {
        super(ID, "snakeRing.png", LandingSound.FLAT, Bard.Enums.COLOR, CardColor.GREEN);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2));
    }
    
	@Override
    public void onEquip() {
		AbstractDungeon.uncommonRelicPool.add(PaperCrane.ID);
		super.onEquip();
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_ChordRing();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new SuckerPunch());
        tmpPool.add(new ExploitWeakness());
        tmpPool.add(new GrandFinale());
        tmpPool.add(new FromAllSides());
        tmpPool.add(new Malaise());
        tmpPool.add(new LegSweep());
        tmpPool.add(new HeelHook());
        
        tmpPool.add(new HideousLaughter());
        tmpPool.add(new Thunderwave());
        tmpPool.add(new StudiedStrike());
        tmpPool.add(new ViciousMockery());
        tmpPool.add(new Demoralize());
        tmpPool.add(new ReverseGravity());
        tmpPool.add(new PhantasmalForce());
		return tmpPool;
	}
}
