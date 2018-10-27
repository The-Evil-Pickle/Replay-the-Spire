package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;

import java.util.*;

public class GuideBook extends AbstractRelic
{
    public static final String ID = "Guide Book";
    private static final int CARD_AMT = 2;
    
    public GuideBook() {
        super("Guide Book", "guidebook.png", RelicTier.SHOP, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + GuideBook.CARD_AMT + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void onEquip() {
        final ArrayList<AbstractCard> upgradableCards = new ArrayList<AbstractCard>();
        for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade() && c.rarity == AbstractCard.CardRarity.RARE) {
                upgradableCards.add(c);
            }
        }
		if (upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT) {
			for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if (!(upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT)) {
					break;
				}
				if (c.canUpgrade() && c.rarity == AbstractCard.CardRarity.UNCOMMON) {
					upgradableCards.add(c);
				}
			}
		}
		if (upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT) {
			for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if (!(upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT)) {
					break;
				}
				if (c.canUpgrade() && c.rarity == AbstractCard.CardRarity.SPECIAL) {
					upgradableCards.add(c);
				}
			}
		}
		if (upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT) {
			for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if (!(upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT)) {
					break;
				}
				if (c.canUpgrade() && c.rarity == AbstractCard.CardRarity.COMMON) {
					upgradableCards.add(c);
				}
			}
		}
		if (upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT) {
			for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if (!(upgradableCards.isEmpty() || upgradableCards.size() < GuideBook.CARD_AMT)) {
					break;
				}
				if (c.canUpgrade() && c.rarity == AbstractCard.CardRarity.BASIC) {
					upgradableCards.add(c);
				}
			}
		}
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
            if (upgradableCards.size() == 1) {
                upgradableCards.get(0).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            }
            else {
                upgradableCards.get(0).upgrade();
                upgradableCards.get(1).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), Settings.WIDTH / 2.0f - AbstractCard.IMG_WIDTH / 2.0f - 20.0f * Settings.scale, Settings.HEIGHT / 2.0f));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy(), Settings.WIDTH / 2.0f + AbstractCard.IMG_WIDTH / 2.0f + 20.0f * Settings.scale, Settings.HEIGHT / 2.0f));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            }
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new GuideBook();
    }
	
	@Override
	public int getPrice() {
		return 150;
	}
}
