package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class M_MistRelic extends AbstractRelic
{
	CardColor mainColor;
	CardColor baseColor;
	int orbSlots;
    public M_MistRelic(String setId, String imgName, RelicTier tier, LandingSound sfx, CardColor mainColor, CardColor baseColor) {
    	this(setId, imgName, tier, sfx, mainColor, baseColor, 0);
    }
    public M_MistRelic(String setId, String imgName, RelicTier tier, LandingSound sfx, CardColor mainColor, CardColor baseColor, int orbSlots) {
		super(setId, imgName, tier, sfx);
		this.mainColor = mainColor;
		this.baseColor = baseColor;
		this.orbSlots = orbSlots;
	}

	abstract ArrayList<AbstractCard> getNewCards();
	static boolean cardIsInPool(AbstractCard c) {
		switch (c.rarity) {
			case BASIC:
			case COMMON: {
				return (AbstractDungeon.srcCommonCardPool.findCardById(c.cardID) != null);
			}
			case UNCOMMON: {
				return (AbstractDungeon.srcUncommonCardPool.findCardById(c.cardID) != null);
			}
			case RARE: {
				return (AbstractDungeon.srcRareCardPool.findCardById(c.cardID) != null);
			}
			default: {
				return (AbstractDungeon.srcUncommonCardPool.findCardById(c.cardID) != null);
			}
		}
	}
	public void addCardsToPools() {
        for (final AbstractCard c : this.getNewCards()) {
        	if (!cardIsInPool(c)) {
        		switch (c.rarity) {
					case BASIC:
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
	}
	
	@Override
    public void onEquip() {
        this.addCardsToPools();
        AbstractDungeon.player.masterMaxOrbs = Math.max(this.orbSlots, AbstractDungeon.player.masterMaxOrbs);
    }
}