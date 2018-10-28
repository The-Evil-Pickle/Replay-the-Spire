package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class TrialRelic_Library extends AbstractRelic
{
    public static final String ID = "replay:Trail_Library";
	private boolean cardsSelected;
	private boolean cardsOpened;
    
    public TrialRelic_Library() {
        super(ID, "betaRelic.png", RelicTier.SPECIAL, LandingSound.FLAT);
        this.cardsOpened = true;
        this.cardsSelected = true;
    }
    
    @Override
    public void onChestOpenAfter(final boolean bossChest) {
        if (!bossChest) {
            this.cardsOpened = false;
            this.cardsSelected = false;
        }
    }
    @Override
    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
        	if (!this.cardsOpened) {
        		this.flash();
        		this.cardsOpened = true;
        		final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        		AbstractCard card = null;
                for (int i = 0; i < 15; ++i) {
                    card = AbstractDungeon.getCard(AbstractDungeon.rollRarity());
                    if (!tmp.contains(card)) {
                        if (card.type == AbstractCard.CardType.ATTACK && AbstractDungeon.player.hasRelic("Molten Egg 2")) {
                            card.upgrade();
                        }
                        else if (card.type == AbstractCard.CardType.SKILL && AbstractDungeon.player.hasRelic("Toxic Egg 2")) {
                            card.upgrade();
                        }
                        else if (card.type == AbstractCard.CardType.POWER && AbstractDungeon.player.hasRelic("Frozen Egg 2")) {
                            card.upgrade();
                        }
                        tmp.addToBottom(card);
                    }
                    else {
                        --i;
                    }
                }
                for (final AbstractCard c : tmp.group) {
                    UnlockTracker.markCardAsSeen(c.cardID);
                }
    			if (AbstractDungeon.isScreenUp) {
    	            AbstractDungeon.dynamicBanner.hide();
    	            AbstractDungeon.overlayMenu.cancelButton.hide();
    	            AbstractDungeon.previousScreen = AbstractDungeon.screen;
    	        }
    	        //AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
    	        AbstractDungeon.gridSelectScreen.open(tmp, 1, this.DESCRIPTIONS[1], false, false, false, false);
    			
        	} else if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
        		this.cardsSelected = true;
                final AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeCopy();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new TrialRelic_Library();
    }
    
    @Override
    public int getPrice() {
    	return 0;
    }
}
