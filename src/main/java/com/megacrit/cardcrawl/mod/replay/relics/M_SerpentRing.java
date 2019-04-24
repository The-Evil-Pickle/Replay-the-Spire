package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.BoundBlade;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WristBlade;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.relics.NinjaScroll;

public class M_SerpentRing extends M_MistRelic
{
    public static final String ID = "m_SerpentRing";
    
    public M_SerpentRing() {
        super(ID, "snakeRing.png", LandingSound.FLAT, blackbeard.enums.AbstractCardEnum.BLACKBEARD_BLACK, CardColor.GREEN);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), 2, false));
    }
    
	@Override
    public void onEquip() {
		AbstractDungeon.bossRelicPool.add(WristBlade.ID);
		AbstractDungeon.uncommonRelicPool.add(NinjaScroll.ID);
		super.onEquip();
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_SerpentRing();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new AThousandCuts());
        tmpPool.add(new Adrenaline());
        tmpPool.add(new Alchemize());
        tmpPool.add(new AfterImage());
        tmpPool.add(new Accuracy());
        tmpPool.add(new BladeDance());
        tmpPool.add(new Choke());
        tmpPool.add(new EndlessAgony());
        tmpPool.add(new Finisher());
        tmpPool.add(new InfiniteBlades());
        tmpPool.add(new HiddenBlade());
        tmpPool.add(new CloakAndDagger());
        tmpPool.add(new StormOfSteel());
		return tmpPool;
	}
}
