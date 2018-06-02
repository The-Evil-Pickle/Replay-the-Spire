package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import java.util.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

public class Kintsugi extends AbstractRelic
{
    public static final String ID = "Kintsugi";
    private boolean cardsSelected;
	public static final int REMOVECOUNT = 5;
	public static final int CURSECOUNT = 2;
    
    public Kintsugi() {
        super("Kintsugi", "kintsugi.png", RelicTier.BOSS, LandingSound.FLAT);
        this.cardsSelected = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.REMOVECOUNT + this.DESCRIPTIONS[1] + this.CURSECOUNT + this.DESCRIPTIONS[2];
    }
    
    @Override
    public void onEquip() {
        this.cardsSelected = false;
        final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (final AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (!card.cardID.equals("Necronomicurse") && !card.cardID.equals("AscendersBane")) {
                tmp.addToTop(card);
            }
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
            return;
        }
        if (tmp.group.size() <= this.REMOVECOUNT) {
            AbstractDungeon.player.masterDeck.group.clear();
            for (final AbstractCard card : tmp.group) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                //AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
            }
            this.cardsSelected = true;
            float displayCount = 0.0f;
			for (int i=0; i < this.CURSECOUNT - 1; i++) {
				if (AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0) {
					((Omamori)AbstractDungeon.player.getRelic("Omamori")).use();
				}
				else {
					final AbstractCard bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE);
					UnlockTracker.markCardAsSeen(bowlCurse.cardID);
					AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(bowlCurse, Settings.WIDTH / 2.0f + displayCount, Settings.HEIGHT / 2.0f, false));
					displayCount += Settings.WIDTH / 4.0f;
				}
			}
			final AbstractCard bowlCurse = new Hallucinations();
			UnlockTracker.markCardAsSeen(bowlCurse.cardID);
			AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(bowlCurse, Settings.WIDTH / 2.0f + displayCount, Settings.HEIGHT / 2.0f, false));
			displayCount += Settings.WIDTH / 4.0f;
        }
        else if (!AbstractDungeon.isScreenUp) {
            AbstractDungeon.gridSelectScreen.open(tmp, this.REMOVECOUNT, this.DESCRIPTIONS[3], false, false, false, false);
        }
        else {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(tmp, this.REMOVECOUNT, this.DESCRIPTIONS[3], false, false, false, false);
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == this.REMOVECOUNT) {
            this.cardsSelected = true;
            for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                card.untip();
                card.unhover();
                AbstractDungeon.player.masterDeck.removeCard(card);
				/*
                AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
                if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM && AbstractDungeon.transformedCard != null) {
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), Settings.WIDTH / 3.0f + displayCount, Settings.HEIGHT / 2.0f, false));
                    displayCount += Settings.WIDTH / 6.0f;
                }*/
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            float displayCount = 0.0f;
			for (int i=0; i < this.CURSECOUNT; i++) {
				if (AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0) {
					((Omamori)AbstractDungeon.player.getRelic("Omamori")).use();
				}
				else {
					final AbstractCard bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE);
					UnlockTracker.markCardAsSeen(bowlCurse.cardID);
					AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(bowlCurse, Settings.WIDTH / 2.0f + displayCount, Settings.HEIGHT / 2.0f, false));
					displayCount += Settings.WIDTH / 4.0f;
				}
			}
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25f;
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Kintsugi();
    }
}
