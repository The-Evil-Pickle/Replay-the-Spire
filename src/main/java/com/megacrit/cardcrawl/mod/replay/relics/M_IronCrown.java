package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;

import champ.cards.*;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.*;

import champ.ChampChar;

public class M_IronCrown extends M_MistRelic
{
    public static final String ID = "m_IronCrown";
    static final int HEAL_AMT = 6;
    public M_IronCrown() {
        super(ID, "m_ironCrown.png", LandingSound.CLINK, ChampChar.Enums.CHAMP_GRAY, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0] + HEAL_AMT + this.DESCRIPTIONS[1];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.IRONCLAD) {
    			desc += this.DESCRIPTIONS[2];
    		}
    		if (AbstractDungeon.player.chosenClass != ChampChar.Enums.THE_CHAMP) {
    			desc += this.DESCRIPTIONS[3];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[2] + this.DESCRIPTIONS[3];
    	}
        return desc;
    }
    
	@Override
    public void onVictory() {
        final AbstractPlayer p = AbstractDungeon.player;
		if (p.currentHealth > 0) {
	        this.flash();
	        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
			p.heal(HEAL_AMT);
		}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_IronCrown();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Bloodletting());
        tmpPool.add(new BloodForBlood());
        tmpPool.add(new Combust());
        tmpPool.add(new Feed());
        tmpPool.add(new Hemokinesis());
        tmpPool.add(new Offering());
        tmpPool.add(new Reaper());
        tmpPool.add(new Rupture());
        tmpPool.add(new DoubleTap());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new DemonForm());
        tmpPool.add(new Whirlwind());
        tmpPool.add(new LimitBreak());

        tmpPool.add(new Hemogenesis());
        tmpPool.add(new LifeLink());
        tmpPool.add(new LeadingStrike());
        tmpPool.add(new MuscleTraining());
        
        tmpPool.add(new EnragedBash());
        tmpPool.add(new VampiricStrike());
        tmpPool.add(new AdrenalArmor());
        tmpPool.add(new HeartStrike());
        tmpPool.add(new Encircle());
        tmpPool.add(new RecklessLeap());
        tmpPool.add(new DeathBlow());
        tmpPool.add(new BerserkersShout());
        tmpPool.add(new GutPunch());
        
		return tmpPool;
	}
}

