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
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;

public class M_SmolderingBlood extends M_MistRelic implements ClickableRelic
{
    public static final String ID = "Replay:M_SmolderingBlood";
    private boolean usedThisFight;
    public M_SmolderingBlood() {
        super(ID, "burningBlood_purple.png", LandingSound.MAGICAL, CardColor.PURPLE, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.IRONCLAD) {
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
    

    public void atBattleStart() {
        this.usedThisFight = false;
        this.beginLongPulse();
    }
    public void onVictory() {
    	this.usedThisFight = true;
        this.stopPulse();
    }
    
	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
		//ironclad cards
        tmpPool.add(new Headbutt());
        tmpPool.add(new SearingBlow());
        tmpPool.add(new Whirlwind());
        tmpPool.add(new Uppercut());
        tmpPool.add(new Bludgeon());
        tmpPool.add(new Reaper());
        tmpPool.add(new Feed());
        tmpPool.add(new Carnage());
        tmpPool.add(new Clash());
        tmpPool.add(new InfernalBlade());
        //watcher cards
        tmpPool.add(new Crescendo());
        tmpPool.add(new CrushJoints());
        tmpPool.add(new EmptyFist());
        tmpPool.add(new FollowUp());
        tmpPool.add(new ForeignInfluence());
        tmpPool.add(new Tantrum());
        tmpPool.add(new Swivel());
        tmpPool.add(new Ragnarok());
        tmpPool.add(new WreathOfFlame());
        tmpPool.add(new ReachHeaven());
        tmpPool.add(new FlyingSleeves());
        tmpPool.add(new Halt());
        tmpPool.add(new BowlingBash());
        return tmpPool;
	}
	
    @Override
    public AbstractRelic makeCopy() {
        return new M_SmolderingBlood();
    }

	@Override
	public void onRightClick() {
		// TODO Auto-generated method stub
		if (!this.usedThisFight) {
			this.usedThisFight = true;
			this.stopPulse();
			AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Calm"));
		}
	}
}
