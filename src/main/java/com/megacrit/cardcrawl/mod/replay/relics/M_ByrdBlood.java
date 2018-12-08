package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.*;

public class M_ByrdBlood extends AbstractRelic
{
    public static final String ID = "m_ByrdBlood";
    
    public M_ByrdBlood() {
        super(ID, "burningBlood.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onGainStrength(ApplyPowerAction __instance) {
    	if(__instance.amount > 0) {
	    	AbstractDungeon.actionManager.addToTop(new HealAction(__instance.target, __instance.source, __instance.amount));
			AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(__instance.target, this));
    	}
    }
	@Override
    public void onEquip() {
		AbstractDungeon.rareRelicPool.add("Magic Flower");
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Reaper());
        tmpPool.add(new Feed());
        tmpPool.add(new Hemogenesis());
        tmpPool.add(new Flex());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Massacre());
        tmpPool.add(new Inflame());
        tmpPool.add(new SpotWeakness());
        tmpPool.add(new DemonForm());
        tmpPool.add(new MuscleTraining());
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
    public int onPlayerHeal(final int healAmount) {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            return MathUtils.round(healAmount * 1.5f);
        }
        return healAmount;
    }
	@Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        final AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal(4);
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_ByrdBlood();
    }
}

