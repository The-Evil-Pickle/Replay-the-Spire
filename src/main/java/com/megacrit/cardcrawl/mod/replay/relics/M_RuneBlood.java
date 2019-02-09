package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.ArmamentsMkIIB;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.cards.Runesmith.*;

public class M_RuneBlood extends M_MistRelic
{
    public static final String ID = "m_RuneBlood";
    private static final int HEALTH_AMT = 3;
    private static final int IGNIS_AMT = 2;
    private static final int DAMAGE_AMT = 20;
    
    public M_RuneBlood() {
        super(ID, "burningBlood_orange.png", RelicTier.STARTER, LandingSound.MAGICAL, runesmith.patches.AbstractCardEnum.RUNESMITH_BEIGE, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + M_RuneBlood.DAMAGE_AMT + this.DESCRIPTIONS[1] + M_RuneBlood.IGNIS_AMT + this.DESCRIPTIONS[2] + M_RuneBlood.HEALTH_AMT + this.DESCRIPTIONS[3];
    }
    
    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.heal(HEALTH_AMT);
        this.counter = -1;
    }

    @Override
    public void atPreBattle() {
        this.counter = 0;
    }
    
    @Override
    public void atTurnStart() {
    	this.counter = 0;
    }
    
    @Override
    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature abstractCreature) {
		if (damageInfo.output > 0 && abstractCreature != null && abstractCreature != AbstractDungeon.player) {
			this.counter += damageInfo.output;
			int ignis = 0;
			while (this.counter >= DAMAGE_AMT) {
				this.counter -= DAMAGE_AMT;
				ignis += IGNIS_AMT;
			}
			if (ignis > 0) {
				AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(AbstractDungeon.player, AbstractDungeon.player, ignis, 0, 0));
			}
        }
    }
    
	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
		//ironclad cards
        tmpPool.add(new Headbutt());
        tmpPool.add(new SearingBlow());
        tmpPool.add(new Armaments());
        tmpPool.add(new Bash());
        tmpPool.add(new Rampage());
        tmpPool.add(new PerfectedStrike());
        tmpPool.add(new LeadingStrike());
        tmpPool.add(new DemonicInfusion());
        tmpPool.add(new ArmamentsMkIIB());
        //runesmith cards
        tmpPool.add(new Grindstone());
        tmpPool.add(new Fortify());
        tmpPool.add(new HammerThrow());
        tmpPool.add(new FieryHammer());
        tmpPool.add(new ShiftingStrike());
        tmpPool.add(new MetallurgicalResearch());
        tmpPool.add(new Rearm());
        tmpPool.add(new DoubleUp());
        tmpPool.add(new Reinforce());
        tmpPool.add(new CraftFirestone());
        return tmpPool;
	}
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_RuneBlood();
    }
}
