package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import guardian.cards.*;

public class M_GuardianBlood extends M_MistRelic
{
    public static final String ID = "m_GuardianBlood";
    
    public M_GuardianBlood() {
        super(ID, "burningBlood_orange.png", RelicTier.STARTER, LandingSound.MAGICAL, guardian.patches.AbstractCardEnum.GUARDIAN, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.heal(6);
    }
    
	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
		//ironclad cards
        tmpPool.add(new BodySlam());
        tmpPool.add(new DemonForm());
        tmpPool.add(new DoubleTap());
        tmpPool.add(new Entrench());
        tmpPool.add(new Flex());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Inflame());
        tmpPool.add(new LimitBreak());
        tmpPool.add(new Pummel());
        tmpPool.add(new Reaper());
        tmpPool.add(new SpotWeakness());
        tmpPool.add(new ThunderClap());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new Whirlwind());
        tmpPool.add(new Massacre());
        tmpPool.add(new MuscleTraining());
        //guardian cards
        tmpPool.add(new Gem_Red());
        tmpPool.add(new DonusPower());
        tmpPool.add(new HyperBeam_Guardian());
        tmpPool.add(new WalkerClaw());
        tmpPool.add(new WeakpointTargeting());
        tmpPool.add(new PolyBeam());
        tmpPool.add(new GuardianWhirl());
        tmpPool.add(new FloatingOrbs());
        tmpPool.add(new RefractedBeam());
        return tmpPool;
	}
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_GuardianBlood();
    }
}
