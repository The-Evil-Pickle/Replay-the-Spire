package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.Berserk;
import com.megacrit.cardcrawl.cards.red.Brutality;
import com.megacrit.cardcrawl.cards.red.Carnage;
import com.megacrit.cardcrawl.cards.red.DarkEmbrace;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.cards.red.FeelNoPain;
import com.megacrit.cardcrawl.cards.red.GhostlyArmor;
import com.megacrit.cardcrawl.cards.red.Headbutt;
import com.megacrit.cardcrawl.cards.red.Rampage;
import com.megacrit.cardcrawl.cards.red.RecklessCharge;
import com.megacrit.cardcrawl.cards.red.Warcry;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import fruitymod.seeker.patches.AbstractCardEnum;
import replayTheSpire.ReplayTheSpireMod;

public class m_ArcaneBlood extends M_MistRelic
{
    public static final String ID = "m_ArcaneBlood";
    private static final int HEALTH_AMT = 3;
    
    public m_ArcaneBlood() {
        super(ID, "burningBlood_purple.png", LandingSound.MAGICAL, AbstractCardEnum.SEEKER_PURPLE, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + m_ArcaneBlood.HEALTH_AMT + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void onVictory() {
        int healcounter = 0;
        for (AbstractPower p : AbstractDungeon.player.powers) {
        	if (p != null && p.type == AbstractPower.PowerType.DEBUFF) {
        		healcounter += HEALTH_AMT;
        	}
        }
        if (healcounter > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.heal(healcounter);
        }
    }

    
    @Override
    public AbstractRelic makeCopy() {
        return new m_ArcaneBlood();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Headbutt());
        tmpPool.add(new Warcry());
        tmpPool.add(new Carnage());
        tmpPool.add(new FeelNoPain());
        tmpPool.add(new GhostlyArmor());
        tmpPool.add(new RecklessCharge());
        tmpPool.add(new Berserk());
        tmpPool.add(new Brutality());
        tmpPool.add(new DarkEmbrace());
        tmpPool.add(new Exhume());
        tmpPool.add(new Rampage());
		return tmpPool;
	}
}
