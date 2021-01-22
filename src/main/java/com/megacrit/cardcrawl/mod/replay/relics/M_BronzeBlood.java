package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import automaton.cards.*;

public class M_BronzeBlood extends M_MistRelic
{
    public static final String ID = "m_BronzeBlood";
    static final int HEAL_AMT = 6;
    public M_BronzeBlood() {
        super(ID, "burningBlood_orange.png", LandingSound.MAGICAL, automaton.AutomatonChar.Enums.BRONZE_AUTOMATON, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0] + HEAL_AMT + this.DESCRIPTIONS[1];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.IRONCLAD) {
    			desc += this.DESCRIPTIONS[2];
    		}
    		if (AbstractDungeon.player.chosenClass != automaton.AutomatonChar.Enums.THE_AUTOMATON) {
    			desc += this.DESCRIPTIONS[3];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[2] + this.DESCRIPTIONS[3];
    	}
        return desc;
    }
    
    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.heal(HEAL_AMT);
    }
    
	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
		//ironclad cards
		M_MistRelic.cardlist_BlockRed(tmpPool);
		M_MistRelic.cardlist_StatusRed(tmpPool);
        //automaton cards
		tmpPool.add(new BugBarrage());
		tmpPool.add(new BuggyMess());
		tmpPool.add(new Shield());
		tmpPool.add(new Allocate());
		tmpPool.add(new Turbo());
		tmpPool.add(new ItsAFeature());
		tmpPool.add(new MaxOutput());
		tmpPool.add(new Repulsor());
		tmpPool.add(new BurnOut());
		tmpPool.add(new FindAndReplace());
		tmpPool.add(new Mutator());
		tmpPool.add(new ShipIt());
        return tmpPool;
	}
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_BronzeBlood();
    }
}
