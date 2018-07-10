package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import madsciencemod.actions.common.GainFuelAction;
import replayTheSpire.ReplayTheSpireMod;

public class m_ArcaneBlood  extends AbstractRelic
{
    public static final String ID = "m_ArcaneBlood";
    private static final int HEALTH_AMT = 3;
    
    public m_ArcaneBlood() {
        super(ID, "sizzlingBlood.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
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
    public void onEquip() {
		//AbstractDungeon.rareRelicPool.add("Magic Flower");
        final long startTime = System.currentTimeMillis();
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
    
    @Override
    public AbstractRelic makeCopy() {
        return new m_ArcaneBlood();
    }
}
