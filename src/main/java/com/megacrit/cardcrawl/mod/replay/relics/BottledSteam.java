package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.function.Predicate;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomBottleRelic;
import replayTheSpire.patches.BottlePatches;

public class BottledSteam extends AbstractRelic implements CustomBottleRelic
{
    public static final String ID = "Replay:Bottled Steam";
    private boolean cardSelected;
    public AbstractCard card;
    public boolean isActive;
    
    public BottledSteam() {
        super(ID, "bottledSteam.png", RelicTier.UNCOMMON, LandingSound.CLINK);
        this.cardSelected = true;
        this.card = null;
        this.isActive = false;
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
            	BottlePatches.BottleFields.inBottleSteam.set(cardInDeck, false);
            }
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottlePatches.BottleFields.inBottleSteam.set(card, true);
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
    public void atBattleStart() {
    	this.isActive = true;
    }

    @Override
    public void onVictory() {
    	this.isActive = false;
    }

    @Override
    public void atPreBattle() {
    	this.isActive = false;
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new BottledSteam();
    }
    
	@Override
	public Predicate<AbstractCard> isOnCard() {
		return BottlePatches.BottleFields.inBottleSteam::get;
	}
	
	public static void save(final SpireConfig config) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
            final BottledSteam relic = (BottledSteam)AbstractDungeon.player.getRelic(ID);
            config.setInt("bottledSteam", AbstractDungeon.player.masterDeck.group.indexOf(relic.card));
        }
        else {
            config.remove("bottledSteam");
        }
    }
    
    public static void load(final SpireConfig config) {
        if (AbstractDungeon.player.hasRelic(ID) && config.has("bottledSteam")) {
            final BottledSteam relic = (BottledSteam)AbstractDungeon.player.getRelic(ID);
            final int cardIndex = config.getInt("bottledSteam");
            if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
                relic.card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
                if (relic.card != null) {
                	BottlePatches.BottleFields.inBottleSteam.set(relic.card, true);
                    relic.setDescriptionAfterLoading();
                }
            }
        }
    }
    
    public static void clear() {
    }
}