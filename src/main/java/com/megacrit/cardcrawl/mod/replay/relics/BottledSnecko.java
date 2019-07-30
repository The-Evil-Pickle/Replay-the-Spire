package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.function.Predicate;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SneckoField;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomBottleRelic;
import replayTheSpire.patches.BottlePatches;

public class BottledSnecko extends AbstractRelic implements CustomBottleRelic
{
    public static final String ID = "Replay:Bottled Snecko";
    private boolean cardSelected;
    public AbstractCard card;
    
    public BottledSnecko() {
        super(ID, "bottledSnecko.png", RelicTier.UNCOMMON, LandingSound.CLINK);
        this.cardSelected = true;
        this.card = null;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public AbstractCard getCard() {
        return this.card.makeCopy();
    }
    
    @Override
    public void onEquip() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck), 1, "", false, false, true, true);
    }
    
    @Override
    public void onUnequip() {
        if (this.card != null) {
            final AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            if (cardInDeck != null) {
            	BottlePatches.BottleFields.inBottleSnecko.set(cardInDeck, false);
            }
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID) {
            this.cardSelected = true;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            	this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                BottlePatches.BottleFields.inBottleSnecko.set(card, true);
                SneckoField.snecko.set(card, true);
                card.cost = card.costForTurn = -1;
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BottledSnecko();
    }

	@Override
	public Predicate<AbstractCard> isOnCard() {
		return BottlePatches.BottleFields.inBottleSnecko::get;
	}
	
	public static void save(final SpireConfig config) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
            final BottledSnecko relic = (BottledSnecko)AbstractDungeon.player.getRelic(ID);
            config.setInt("bottledSnecko", AbstractDungeon.player.masterDeck.group.indexOf(relic.card));
        }
        else {
            config.remove("bottledSnecko");
        }
    }
    
    public static void load(final SpireConfig config) {
        if (AbstractDungeon.player.hasRelic(ID) && config.has("bottledSnecko")) {
            final BottledSnecko relic = (BottledSnecko)AbstractDungeon.player.getRelic(ID);
            final int cardIndex = config.getInt("bottledSnecko");
            if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
                relic.card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
                if (relic.card != null) {
                	BottlePatches.BottleFields.inBottleSnecko.set(relic.card, true);
                	SneckoField.snecko.set(relic.card, true);
                }
            }
        }
    }
    
    public static void clear() {
    }
	
}