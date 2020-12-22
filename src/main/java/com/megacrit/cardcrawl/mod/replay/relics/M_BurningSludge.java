package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.*;

import slimebound.cards.*;
import slimebound.orbs.SpawnedSlime;

public class M_BurningSludge extends M_MistRelic
{
    public static final String ID = "m_BurningSludge";
    
    public M_BurningSludge() {
        super(ID, "burningBlood_green.png", LandingSound.MAGICAL, slimebound.patches.AbstractCardEnum.SLIMEBOUND, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.IRONCLAD) {
    			desc += this.DESCRIPTIONS[1];
    		}
    		if (AbstractDungeon.player.chosenClass != slimebound.patches.SlimeboundEnum.SLIMEBOUND) {
    			desc += this.DESCRIPTIONS[2];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
    	}
        return desc;
    }
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        final AbstractPlayer p = AbstractDungeon.player;
        int slimeCount = 0;
        if (p.maxOrbs > 0 && p.orbs.get(0) != null) {
            for (final AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof SpawnedSlime) {
                    ++slimeCount;
                }
            }
            p.heal(slimeCount * 2);
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_BurningSludge();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Offering());
        tmpPool.add(new Hemogenesis());
        tmpPool.add(new Pummel());
        tmpPool.add(new Whirlwind());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new SwordBoomerang());
        tmpPool.add(new Reaper());
        tmpPool.add(new BloodForBlood());
        tmpPool.add(new Rupture());
        tmpPool.add(new Evolve());
        tmpPool.add(new LifeLink());

        tmpPool.add(new FinishingTackle());
        tmpPool.add(new CheckThePlaybook());
        tmpPool.add(new ViciousTackle());
        tmpPool.add(new FeelOurPain());
        tmpPool.add(new FlameTackle());
        tmpPool.add(new HungryTackle());
        tmpPool.add(new MassFeed());
		return tmpPool;
	}
}

