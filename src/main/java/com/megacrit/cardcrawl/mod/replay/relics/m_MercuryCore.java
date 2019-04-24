package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.blue.Amplify;
import com.megacrit.cardcrawl.cards.blue.CreativeAI;
import com.megacrit.cardcrawl.cards.blue.FTL;
import com.megacrit.cardcrawl.cards.blue.ForceField;
import com.megacrit.cardcrawl.cards.blue.Heatsinks;
import com.megacrit.cardcrawl.cards.blue.MachineLearning;
import com.megacrit.cardcrawl.cards.blue.Overclock;
import com.megacrit.cardcrawl.cards.blue.SelfRepair;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import chronomuncher.actions.ChronoChannelAction;
import chronomuncher.cards.*;
import chronomuncher.orbs.UnlockedMercury;

public class m_MercuryCore extends M_MistRelic
{
    public static final String ID = "m_MercuryCore";
    
    public m_MercuryCore() {
        super(ID, "crackedOrb.png", LandingSound.CLINK, chronomuncher.patches.Enum.CHRONO_GOLD, CardColor.BLUE);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        //this.tips.add(new PowerTip("Replicate", "A #yReplica will float above your character, activating its effect every turn. When the countdown reaches 0, it will #yshatter and be removed."));
        this.tips.add(new PowerTip("Mercury", "Deals #g3 damage to ALL enemies at the start of your turn. NL #pShatters #pin #b4 #pturns."));
        this.initializeTips();
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
    	AbstractDungeon.actionManager.addToBottom(new ChronoChannelAction(new UnlockedMercury(false)));
    }
    
    
    @Override
    public AbstractRelic makeCopy() {
        return new m_MercuryCore();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Amplify());
        tmpPool.add(new CreativeAI());
        tmpPool.add(new FTL());
        tmpPool.add(new MachineLearning());
        tmpPool.add(new Overclock());
        tmpPool.add(new SelfRepair());
        tmpPool.add(new Heatsinks());
        tmpPool.add(new WhiteNoise());
        tmpPool.add(new ForceField());
        

        tmpPool.add(new MasterKey());
        tmpPool.add(new LockedMercury());
        tmpPool.add(new LockedIceCream());
        tmpPool.add(new LockedOrichalcum());
        tmpPool.add(new LockedUrn());
        tmpPool.add(new LockedBlood());
        tmpPool.add(new LockedFlame());
        tmpPool.add(new LockedLightning());
        tmpPool.add(new LockedTornado());
        tmpPool.add(new LockedMedicine());
        tmpPool.add(new LockedThread());
        tmpPool.add(new LockedWarPaint());
        tmpPool.add(new LockedWhetstone());
        tmpPool.add(new LockedScales());
        tmpPool.add(new Fragmentalize());
		return tmpPool;
	}
}
