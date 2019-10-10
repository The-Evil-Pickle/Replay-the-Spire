package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.DemonicInfusion;
import com.megacrit.cardcrawl.mod.replay.cards.red.LeadingStrike;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.ArmamentsMkIIB;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import runesmith.cards.Runesmith.CraftFirestone;
import runesmith.cards.Runesmith.DoubleUp;
import runesmith.cards.Runesmith.FieryHammer;
import runesmith.cards.Runesmith.Fortify;
import runesmith.cards.Runesmith.Grindstone;
import runesmith.cards.Runesmith.HammerThrow;
import runesmith.cards.Runesmith.MetallurgicalResearch;
import runesmith.cards.Runesmith.Reinforce;
import runesmith.cards.Runesmith.ShiftingStrike;

//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.core.*;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;

public class M_StaffOfTheSnake extends M_MistRelic
{
    public static final String ID = "Replay:M_StaffOfTheSnake";
    public M_StaffOfTheSnake() {
        super(ID, "sizzlingBlood.png", LandingSound.MAGICAL, CardColor.PURPLE, CardColor.GREEN);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.THE_SILENT) {
    			desc += this.DESCRIPTIONS[1];
    		}
    		if (AbstractDungeon.player.chosenClass != PlayerClass.WATCHER) {
    			desc += this.DESCRIPTIONS[2];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
    	}
        return desc;
    }
    
    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Insight(), 1, false));
    }
    
	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
		//silent cards
        tmpPool.add(new Acrobatics());
        tmpPool.add(new Adrenaline());
        tmpPool.add(new BulletTime());
        tmpPool.add(new DaggerThrow());
        tmpPool.add(new DieDieDie());
        tmpPool.add(new Doppelganger());
        tmpPool.add(new EscapePlan());
        tmpPool.add(new FlyingKnee());
        tmpPool.add(new GlassKnife());
        tmpPool.add(new GrandFinale());
        tmpPool.add(new MasterfulStab());
        tmpPool.add(new Nightmare());
        tmpPool.add(new Outmaneuver());
        tmpPool.add(new PhantasmalKiller());
        tmpPool.add(new Predator());
        tmpPool.add(new Setup());
        tmpPool.add(new ToolsOfTheTrade());
        tmpPool.add(new WellLaidPlans());
        tmpPool.add(new WraithForm());
        //watcher cards
        tmpPool.add(new CutThroughFate());
        tmpPool.add(new Flick());
        tmpPool.add(new Weave());
        tmpPool.add(new Nirvana());
        tmpPool.add(new SashWhip());
        tmpPool.add(new ThirdEye());
        tmpPool.add(new Crescendo());
        tmpPool.add(new Sanctity());
        tmpPool.add(new JustLucky());
        tmpPool.add(new SimmeringFury());
        tmpPool.add(new EmptyMind());
        //tmpPool.add(new FlowState());
        tmpPool.add(new InnerPeace());
        tmpPool.add(new Tranquility());
        tmpPool.add(new TalkToTheHand());
        tmpPool.add(new WaveOfTheHand());
        tmpPool.add(new Establishment());
        tmpPool.add(new MasterReality());
        return tmpPool;
	}
	
    @Override
    public AbstractRelic makeCopy() {
        return new M_StaffOfTheSnake();
    }
}
