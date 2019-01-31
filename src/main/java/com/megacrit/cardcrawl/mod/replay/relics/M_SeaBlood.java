package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.relics.*;

import blackrusemod.powers.*;

public class M_SeaBlood extends AbstractRelic
{
    public static final String ID = "m_SeaBlood";
    
    public M_SeaBlood() {
        super(ID, "burningBlood_blue.png", RelicTier.STARTER, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
	@Override
    public void onEquip() {
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Hemokinesis());
        tmpPool.add(new Offering());
        tmpPool.add(new Hemogenesis());
        tmpPool.add(new Flex());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Massacre());
        tmpPool.add(new Inflame());
        tmpPool.add(new SpotWeakness());
        tmpPool.add(new DemonForm());
        tmpPool.add(new LimitBreak());
        tmpPool.add(new Pummel());
        tmpPool.add(new MuscleTraining());
        tmpPool.add(new FireBreathing());
        tmpPool.add(new Whirlwind());
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
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        final AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal(6);
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_SeaBlood();
    }
}

