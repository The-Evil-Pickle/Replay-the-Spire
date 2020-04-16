package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.Hemogenesis;
import com.megacrit.cardcrawl.mod.replay.cards.red.LimbFromLimb;
import com.megacrit.cardcrawl.mod.replay.cards.red.Massacre;
import com.megacrit.cardcrawl.mod.replay.cards.red.MuscleTraining;
import com.megacrit.cardcrawl.mod.replay.cards.red.UndeathsTouch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import stsjorbsmod.actions.RememberSpecificMemoryAction;
import stsjorbsmod.cards.cull.Apparate;
import stsjorbsmod.cards.wanderer.ColorSpray;
import stsjorbsmod.cards.wanderer.DeathThroes;
import stsjorbsmod.cards.wanderer.FindFamiliar;
import stsjorbsmod.cards.wanderer.MagicMissiles;
import stsjorbsmod.cards.wanderer.PurgeMind;
import stsjorbsmod.cards.wanderer.SmithingStrike;
import stsjorbsmod.cards.wanderer.Stalwart;
import stsjorbsmod.characters.Wanderer;
import stsjorbsmod.memories.MemoryManager;
import stsjorbsmod.memories.TemperanceMemory;

public class M_EldritchBlood extends M_MistRelic
{
    public static final String ID = "m_EldritchBlood";
    private static final int HEALING = 1;
    
    public M_EldritchBlood() {
        super(ID, "burningBlood_purple.png", LandingSound.MAGICAL, Wanderer.Enums.WANDERER_CARD_COLOR, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0] + HEALING + this.DESCRIPTIONS[1];
    	if (AbstractDungeon.player == null || AbstractDungeon.player.getCardColor() != CardColor.RED) {
    		desc = desc + this.DESCRIPTIONS[2];
    	}
    	if (AbstractDungeon.player == null || AbstractDungeon.player.getCardColor() != Wanderer.Enums.WANDERER_CARD_COLOR) {
    		desc = desc + this.DESCRIPTIONS[3];
    	}
        return desc;
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RememberSpecificMemoryAction(AbstractDungeon.player, TemperanceMemory.STATIC.ID));
    }
    
	@Override
    public void onVictory() {
		this.counter = -1;
        this.stopPulse();
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (AbstractDungeon.player.currentHealth > 0) {
        	AbstractDungeon.player.heal(MemoryManager.forPlayer(AbstractDungeon.player).countCurrentClarities() * 1);
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_EldritchBlood();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Reaper());
        tmpPool.add(new Feed());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new SwordBoomerang());
        tmpPool.add(new Whirlwind());
        tmpPool.add(new ThunderClap());
        tmpPool.add(new Dropkick());
        tmpPool.add(new Pummel());
        tmpPool.add(new Exhume());
        tmpPool.add(new LimbFromLimb());
        tmpPool.add(new UndeathsTouch());

        tmpPool.add(new Stalwart());
        tmpPool.add(new MagicMissiles());
        tmpPool.add(new PurgeMind());
        tmpPool.add(new ColorSpray());
        tmpPool.add(new DeathThroes());
        tmpPool.add(new SmithingStrike());
        tmpPool.add(new FindFamiliar());
        
		return tmpPool;
	}
}

