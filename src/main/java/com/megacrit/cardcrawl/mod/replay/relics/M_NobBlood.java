package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.green.*;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.HexaringAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.*;

import gremlin.GremlinMod;
import gremlin.actions.GremlinSwapAction;
import gremlin.cards.*;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;

public class M_NobBlood extends M_MistRelic
{
    public static final String ID = "m_NobBlood";
    public static final int HEAL_AMT = 6;
    public static final int HEAL_ALL_AMT = 2;
    public M_NobBlood() {
        super(ID, "burningBlood.png", LandingSound.MAGICAL, gremlin.patches.AbstractCardEnum.GREMLIN, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0] + HEAL_AMT + this.DESCRIPTIONS[1] + HEAL_ALL_AMT + this.DESCRIPTIONS[2];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.IRONCLAD) {
    			desc += this.DESCRIPTIONS[3];
    		}
    		if (AbstractDungeon.player.chosenClass != gremlin.patches.GremlinEnum.GREMLIN) {
    			desc += this.DESCRIPTIONS[4];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[3] + this.DESCRIPTIONS[4];
    	}
        return desc;
    }
    

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(true));
    }
    
    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (AbstractDungeon.player.currentHealth > 0) {
        	if (AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 2) {
        		AbstractDungeon.player.heal(HEAL_AMT);
        	} else {
                if (AbstractDungeon.player.chosenClass == gremlin.patches.GremlinEnum.GREMLIN) {
                    final GremlinCharacter gremlinMob = (GremlinCharacter)AbstractDungeon.player;
                    gremlinMob.healGremlins(1);
                    for (int i = 0; i < gremlinMob.maxOrbs; ++i) {
                        if (gremlinMob.orbs.get(i) instanceof GremlinStandby) {
                            final GremlinStandby grem = (GremlinStandby)AbstractDungeon.player.orbs.get(i);
                            if (grem.hp < gremlinMob.maxHealth) {
                            	grem.hp = Math.min(grem.hp + HEAL_ALL_AMT, gremlinMob.maxHealth);
                            }
                        }
                    }
                    
                }
            	AbstractDungeon.player.heal(HEAL_ALL_AMT);
        	}
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_NobBlood();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();

        M_MistRelic.cardlist_Strength(tmpPool);
        M_MistRelic.cardlist_Multihit(tmpPool);
        M_MistRelic.cardlist_BlockRed(tmpPool);

        tmpPool.add(new ArmsTheft());
        tmpPool.add(new FollowThrough());
        tmpPool.add(new GremlinOffensive());
        tmpPool.add(new GremlinToss());
        tmpPool.add(new IrksomeBlow());
        tmpPool.add(new MakeshiftArmor());
        tmpPool.add(new RageBreak());
        tmpPool.add(new SharpenBlades());
        tmpPool.add(new Duplicate());
        tmpPool.add(new Fury());
        tmpPool.add(new Erupt());
        tmpPool.add(new FlipOut());

		return tmpPool;
	}

}

