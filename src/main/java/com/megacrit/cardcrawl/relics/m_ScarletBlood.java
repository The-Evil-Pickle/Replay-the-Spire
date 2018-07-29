package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

import blackrusemod.powers.*;

public class m_ScarletBlood extends AbstractRelic
{
    public static final String ID = "m_ScarletBlood";
    
    public m_ScarletBlood() {
        super(ID, "burningBlood.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public void onGainStrength(ApplyPowerAction __instance) {
    	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__instance.target, __instance.source, new KnivesPower(__instance.target, __instance.amount * 2), __instance.amount * 2));
		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(__instance.target, this));
    }
    
	@Override
    public void onEquip() {
		AbstractDungeon.rareRelicPool.add("Red Skull");
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Flex());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Inflame());
        tmpPool.add(new Pummel());
        tmpPool.add(new SpotWeakness());
        tmpPool.add(new DemonForm());
        tmpPool.add(new DoubleTap());
        tmpPool.add(new LimitBreak());
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
        return new m_ScarletBlood();
    }
}

