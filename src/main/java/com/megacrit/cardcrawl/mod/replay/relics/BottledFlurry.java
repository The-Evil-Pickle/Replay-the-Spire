package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.function.Predicate;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.mod.replay.actions.unique.FlurryBottleAction;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomBottleRelic;
import replayTheSpire.patches.BottlePatches;

public class BottledFlurry extends AbstractRelic implements CustomBottleRelic
{
    public static final String ID = "ReplayTheSpireMod:Bottled Flurry";
    private boolean cardSelected;
    public AbstractCard card;
    
    public BottledFlurry() {
        super(ID, "bottledFlurry.png", RelicTier.UNCOMMON, LandingSound.CLINK);
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
        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck), 1, this.DESCRIPTIONS[1] + this.name + ".", false, false, false, false);
    }
    
    @Override
    public void onUnequip() {
        if (this.card != null) {
            final AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            if (cardInDeck != null) {
            	BottlePatches.BottleFields.inBottleFlurry.set(cardInDeck, false);
            }
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottlePatches.BottleFields.inBottleFlurry.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
    }
    
    public void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    @Override
    public void onPlayerEndTurn() {
    	AbstractDungeon.actionManager.addToTop(new FlurryBottleAction(this));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BottledFlurry();
    }

	@Override
	public Predicate<AbstractCard> isOnCard() {
		return BottlePatches.BottleFields.inBottleFlurry::get;
	}
	
	public static void save(final SpireConfig config) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
            final BottledFlurry relic = (BottledFlurry)AbstractDungeon.player.getRelic(ID);
            config.setInt("bottledFlurry", AbstractDungeon.player.masterDeck.group.indexOf(relic.card));
        }
        else {
            config.remove("bottledFlurry");
        }
    }
    
    public static void load(final SpireConfig config) {
        if (AbstractDungeon.player.hasRelic(ID) && config.has("bottledFlurry")) {
            final BottledFlurry relic = (BottledFlurry)AbstractDungeon.player.getRelic(ID);
            final int cardIndex = config.getInt("bottledFlurry");
            if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
                relic.card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
                if (relic.card != null) {
                	BottlePatches.BottleFields.inBottleFlurry.set(relic.card, true);
                    relic.setDescriptionAfterLoading();
                }
            }
        }
    }
    
    public static void clear() {
    }
	
}