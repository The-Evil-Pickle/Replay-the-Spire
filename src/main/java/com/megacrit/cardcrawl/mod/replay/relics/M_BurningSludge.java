package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.*;

import slimebound.orbs.SpawnedSlime;

public class M_BurningSludge extends AbstractRelic
{
    public static final String ID = "m_BurningSludge";
    
    public M_BurningSludge() {
        super(ID, "burningBlood_green.png", RelicTier.STARTER, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        final AbstractPlayer p = AbstractDungeon.player;
        int slimeCount = 0;
        if (p.orbs.get(0) != null) {
            for (final AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof SpawnedSlime) {
                    ++slimeCount;
                }
            }
            p.heal(slimeCount * 2);
        }
    }
	@Override
    public void onEquip() {
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Offering());
        tmpPool.add(new Hemogenesis());
        tmpPool.add(new Pummel());
        tmpPool.add(new Whirlwind());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new SwordBoomerang());
        tmpPool.add(new Reaper());
        tmpPool.add(new BloodForBlood());
        tmpPool.add(new Rupture());
        tmpPool.add(new Evolve());
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
        return new M_BurningSludge();
    }
}

