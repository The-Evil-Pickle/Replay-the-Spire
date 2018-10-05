package com.megacrit.cardcrawl.relics;

import java.util.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.defect.*;

public class IronCore extends AbstractRelic
{
    public static final String ID = "Iron Core";
    private boolean hasBlues;
    private boolean hasReds;
    private boolean firstTurn;
    
    public IronCore() {
        super("Iron Core", "ironCoreOrb.png", RelicTier.SPECIAL, LandingSound.CLINK);
        this.hasReds = false;
        this.hasBlues = false;
        this.firstTurn = true;
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = ""; 
    	if (this.hasBlues == this.hasReds) {
    		return this.DESCRIPTIONS[4];
    	} else {
    		if (this.hasReds) {
        		desc += this.DESCRIPTIONS[0];
        		desc += this.DESCRIPTIONS[2];
        	} 
        	if (this.hasBlues) {
        		desc += this.DESCRIPTIONS[1];
        		desc += this.DESCRIPTIONS[3];
        	}
    	}
        return desc;
    }
    
    @Override
    public void atPreBattle() {
    	if (this.hasBlues == false && this.hasReds == false) {
    		this.onEquip();
    	}
    	if ((!this.hasBlues && this.hasReds) || (this.hasReds == this.hasBlues && AbstractDungeon.player.maxOrbs > 0)) {
    		AbstractDungeon.player.channelOrb(new HellFireOrb());
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    		this.firstTurn = false;
    	} else {
    		this.firstTurn = true;
    	}
    }

    @Override
    public void atTurnStart() {
        if (this.firstTurn) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(2));
            this.firstTurn = false;
        }
    }
    @Override
    public AbstractRelic makeCopy() {
        return new IronCore();
    }
	
	@Override
    public void onEquip() {
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        if (!(AbstractDungeon.player instanceof Ironclad)) {
    		AbstractDungeon.rareRelicPool.add("Magic Flower");
        	tmpPool.add(new Anger());
    		tmpPool.add(new Barricade());
    		tmpPool.add(new Clash());
    		tmpPool.add(new DoubleTap());
    		tmpPool.add(new DualWield());
    		tmpPool.add(new FireBreathing());
    		tmpPool.add(new Flex());
    		tmpPool.add(new Headbutt());
    		tmpPool.add(new HeavyBlade());
    		tmpPool.add(new Inflame());
    		tmpPool.add(new IronWave());
    		tmpPool.add(new Juggernaut());
    		tmpPool.add(new LimitBreak());
    		tmpPool.add(new Offering());
    		tmpPool.add(new Reaper());
    		tmpPool.add(new SeeingRed());
    		tmpPool.add(new Warcry());
    		tmpPool.add(new Whirlwind());
    		this.hasReds = true;
        }
        if (!(AbstractDungeon.player instanceof Defect)) {
        	tmpPool.add(new BeamCell());
        	tmpPool.add(new Capacitor());
        	tmpPool.add(new Claw());
        	tmpPool.add(new ColdSnap());
        	tmpPool.add(new Defragment());
        	tmpPool.add(new FTL());
        	tmpPool.add(new ForceField());
        	tmpPool.add(new GeneticAlgorithm());
        	tmpPool.add(new Glacier());
        	tmpPool.add(new Hologram());
        	tmpPool.add(new Overclock());
        	tmpPool.add(new Rebound());
        	tmpPool.add(new Recycle());
        	tmpPool.add(new RipAndTear());
        	tmpPool.add(new Turbo());
        	tmpPool.add(new ReplayGoodbyeWorld());
        	tmpPool.add(new ReplayRepulse());
        	tmpPool.add(new MirrorShield());
        	this.hasBlues = true;
        }
		AbstractDungeon.commonCardPool.addToTop(new WeaponsOverheat());
		AbstractDungeon.srcCommonCardPool.addToBottom(new WeaponsOverheat());
		AbstractDungeon.uncommonCardPool.addToTop(new IC_ScorchingBeam());
		AbstractDungeon.srcUncommonCardPool.addToBottom(new IC_ScorchingBeam());
		AbstractDungeon.rareCardPool.addToTop(new IC_FireWall());
		AbstractDungeon.srcRareCardPool.addToBottom(new IC_FireWall());
        for (final AbstractCard c : tmpPool) {
			switch (c.rarity) {
				case COMMON: {
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
				case UNCOMMON: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
				case RARE: {
					AbstractDungeon.rareCardPool.addToTop(c);
					AbstractDungeon.srcRareCardPool.addToBottom(c);
					continue;
				}
				default: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
			}
        }
    }
}
