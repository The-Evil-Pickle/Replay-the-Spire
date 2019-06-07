package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class M_CorellonBlood extends M_MistRelic
{
    public static final String ID = "m_CorellonBlood";
    
    public M_CorellonBlood() {
        super(ID, "burningBlood_blue.png", LandingSound.MAGICAL, Bard.Enums.COLOR, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    	AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 6));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_CorellonBlood();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Clothesline());
        tmpPool.add(new DemonForm());
        tmpPool.add(new Dropkick());
        tmpPool.add(new Flex());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Inflame());
        tmpPool.add(new InfernalBlade());
        tmpPool.add(new LimitBreak());
        tmpPool.add(new Pummel());
        tmpPool.add(new Shockwave());
        tmpPool.add(new ThunderClap());
        tmpPool.add(new Uppercut());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new LimbFromLimb());
        
        
		return tmpPool;
	}
}
