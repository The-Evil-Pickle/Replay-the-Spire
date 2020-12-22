package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;

import theHexaghost.TheHexaghost;
import theHexaghost.cards.*;
import theHexaghost.cards.seals.FirstSeal;
import theHexaghost.cards.seals.SixthSeal;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.util.OnChargeSubscriber;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.*;

public class M_DevilBlood extends M_MistRelic implements OnChargeSubscriber
{
    public static final String ID = "m_DevilBlood";
    static final int HEAL_PER_IGNITION = 2;
    private ArrayList<AbstractGhostflame> flamesIgnitedThisCombat;
    public M_DevilBlood() {
        super(ID, "burningBlood_purple.png", LandingSound.MAGICAL, TheHexaghost.Enums.GHOST_GREEN, CardColor.RED);
        this.flamesIgnitedThisCombat = new ArrayList<AbstractGhostflame>();
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0] + HEAL_PER_IGNITION + this.DESCRIPTIONS[1];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.IRONCLAD) {
    			desc += this.DESCRIPTIONS[2];
    		}
    		if (AbstractDungeon.player.chosenClass != TheHexaghost.Enums.THE_SPIRIT) {
    			desc += this.DESCRIPTIONS[3];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[2] + this.DESCRIPTIONS[3];
    	}
        return desc;
    }

    @Override
    public void atBattleStart() {
        this.flamesIgnitedThisCombat.clear();
        this.counter = 0;
    }
    
	@Override
    public void onVictory() {
        this.flamesIgnitedThisCombat.clear();
        final AbstractPlayer p = AbstractDungeon.player;
		if (this.counter > 0 && p.currentHealth > 0) {
	        this.flash();
	        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			p.heal(HEAL_PER_IGNITION * this.counter);
		}
		this.counter = -1;
    }
	
	
	@Override
	public void onCharge(AbstractGhostflame gf) {
		if (!this.flamesIgnitedThisCombat.contains(gf)) {
			this.counter++;
			this.flamesIgnitedThisCombat.add(gf);
			this.flash();
		}
	}
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_DevilBlood();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Carnage());
        tmpPool.add(new DarkEmbrace());
        tmpPool.add(new Exhume());
        tmpPool.add(new FeelNoPain());
        tmpPool.add(new FiendFire());
        tmpPool.add(new GhostlyArmor());
        tmpPool.add(new Reaper());
        tmpPool.add(new SearingBlow());
        tmpPool.add(new Sentinel());
        tmpPool.add(new TrueGrit());
        tmpPool.add(new Warcry());

        tmpPool.add(new Abandon());
        tmpPool.add(new DefyDeath());
        

        tmpPool.add(new GhostLash());
        tmpPool.add(new GhostShield());
        tmpPool.add(new Hexaguard());
        tmpPool.add(new NightmareGuise());
        tmpPool.add(new NightmareStrike());
        tmpPool.add(new SpectersWail());
        tmpPool.add(new EtherealExpedition());
        tmpPool.add(new Haunt());
        tmpPool.add(new RecurringNightmare());
        tmpPool.add(new PowerFromBeyond());
        tmpPool.add(new NecessarySacrifice());
        tmpPool.add(new GiftsFromTheDead());
        tmpPool.add(new ParanormalForm());
        tmpPool.add(new UnleashSpirits());
        tmpPool.add(new FirstSeal());
        tmpPool.add(new SixthSeal());
        
		return tmpPool;
	}
}

