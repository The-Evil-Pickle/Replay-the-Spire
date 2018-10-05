package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import chronomuncher.actions.ChronoChannelAction;
import chronomuncher.orbs.UnlockedMercury;

public class m_MercuryCore extends AbstractRelic
{
    public static final String ID = "m_MercuryCore";
    
    public m_MercuryCore() {
        super(ID, "crackedOrb.png", RelicTier.SPECIAL, LandingSound.CLINK);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Replicate", "A #yReplica will float above your character, activating its effect every turn. When the countdown reaches 0, it will #yshatter and be removed."));
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
    public void onEquip() {
        final long startTime = System.currentTimeMillis();
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
        return new m_MercuryCore();
    }
}
