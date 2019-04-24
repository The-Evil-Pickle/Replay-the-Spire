package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.green.Acrobatics;
import com.megacrit.cardcrawl.cards.green.AllOutAttack;
import com.megacrit.cardcrawl.cards.green.CalculatedGamble;
import com.megacrit.cardcrawl.cards.green.DaggerThrow;
import com.megacrit.cardcrawl.cards.green.MasterfulStab;
import com.megacrit.cardcrawl.cards.green.Prepared;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.cards.green.StormOfSteel;
import com.megacrit.cardcrawl.cards.green.Tactician;
import com.megacrit.cardcrawl.cards.green.ToolsOfTheTrade;
import com.megacrit.cardcrawl.cards.green.Unload;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class m_SnakeCloak extends M_MistRelic
{
    public static final String ID = "m_SnakeCloak";
    
    public m_SnakeCloak() {
        super(ID, "snakeRing.png", LandingSound.MAGICAL, blackrusemod.patches.AbstractCardEnum.SILVER, CardColor.GREEN);
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
		AbstractDungeon.rareRelicPool.add("Tough Bandages");
		AbstractDungeon.rareRelicPool.add("Tingsha");
        super.onEquip();
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new m_SnakeCloak();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Prepared());
        tmpPool.add(new Acrobatics());
        tmpPool.add(new AllOutAttack());
        tmpPool.add(new CalculatedGamble());
        tmpPool.add(new DaggerThrow());
        tmpPool.add(new MasterfulStab());
        tmpPool.add(new Reflex());
        tmpPool.add(new StormOfSteel());
        tmpPool.add(new Tactician());
        tmpPool.add(new ToolsOfTheTrade());
        //tmpPool.add(new UnderhandedStrike());
        tmpPool.add(new Unload());
		return tmpPool;
	}
}
