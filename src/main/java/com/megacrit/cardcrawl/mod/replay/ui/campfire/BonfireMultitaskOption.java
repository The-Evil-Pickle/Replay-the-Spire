package com.megacrit.cardcrawl.mod.replay.ui.campfire;

import java.util.ArrayList;
import java.util.Collections;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.vfx.campfire.*;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import replayTheSpire.ReplayTheSpireMod;

public class BonfireMultitaskOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public BonfireMultitaskOption() {
        this.label = RestOption.TEXT[0];
        int healAmt;
        healAmt = (int)(AbstractDungeon.player.maxHealth * 0.2f);
        if (Settings.isEndless && AbstractDungeon.getBlight("FullBelly") != null) {
            healAmt /= 2;
        }
        this.description = BonfireMultitaskOption.TEXT[1] + healAmt + BonfireMultitaskOption.TEXT[2];
        this.img = ReplayTheSpireMod.multitaskButton;
    }
    
    @Override
    public void useOption() {
        CardCrawlGame.sound.play("SLEEP_BLANKET");
        AbstractDungeon.effectList.add(new CampfireSleepEffect());
        for (int i = 0; i < 30; ++i) {
            AbstractDungeon.topLevelEffects.add(new CampfireSleepScreenCoverEffect());
        }
        final ArrayList<AbstractCard> upgradableCards = new ArrayList<AbstractCard>();
		for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c.canUpgrade() && c.rarity != AbstractCard.CardRarity.BASIC) {
				upgradableCards.add(c);
			}
		}
		Collections.shuffle(upgradableCards);
		if (!upgradableCards.isEmpty()) {
			upgradableCards.get(0).upgrade();
			AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
			AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
		}
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Multitask Option");
        TEXT = BonfireMultitaskOption.uiStrings.TEXT;
    }
}
