package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public abstract class M_MistRelic extends AbstractRelic
{
	CardColor mainColor;
	CardColor baseColor;
	int orbSlots;
	boolean hasbuilt = false;
    public M_MistRelic(String setId, String imgName, LandingSound sfx, CardColor mainColor, CardColor baseColor) {
    	this(setId, imgName, sfx, mainColor, baseColor, 0);
    }
    public M_MistRelic(String setId, String imgName, LandingSound sfx, CardColor mainColor, CardColor baseColor, int orbSlots) {
		super(setId, imgName, RelicTier.STARTER, sfx);
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
		hasbuilt = true;
	}

	@Override
    public void onEquip() {
        this.addCardsToPools();
        AbstractDungeon.player.masterMaxOrbs = Math.max(this.orbSlots, AbstractDungeon.player.masterMaxOrbs);
    }
	@Override
    public void onEnterRoom(final AbstractRoom room) {
		if (!this.hasbuilt) {
			this.addCardsToPools();
			 AbstractDungeon.player.masterMaxOrbs = Math.max(this.orbSlots, AbstractDungeon.player.masterMaxOrbs);
		}
    }

    @Override
    public void atPreBattle() {
    	if (!this.hasbuilt) {
			this.addCardsToPools();
			 AbstractDungeon.player.masterMaxOrbs = Math.max(this.orbSlots, AbstractDungeon.player.masterMaxOrbs);
		}
    }
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.CardLibrary", method = "initialize")
	public static class PatchThisShit {
		public static void Postfix() {
			if (AbstractDungeon.player != null) {
				for (AbstractRelic r : AbstractDungeon.player.relics) {
					if (r instanceof M_MistRelic) {
						((M_MistRelic)r).addCardsToPools();
					}
				}
			}
		}
	}
}