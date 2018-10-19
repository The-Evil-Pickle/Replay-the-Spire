package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.rooms.*;
import java.util.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

public class Kintsugi extends AbstractRelic
{
    public static final String ID = "Kintsugi";
    private boolean cardsSelected;
	private boolean cursesSelected;
	private boolean cursesOpened;
	public static final int REMOVECOUNT = 5;
	public static final int CURSECOUNT = 2;
	public static final int CURSEOPTIONS = 5;
    
    public Kintsugi() {
        super("Kintsugi", "kintsugi.png", RelicTier.BOSS, LandingSound.FLAT);
        this.cardsSelected = true;
		this.cursesSelected = true;
		this.cursesOpened = false;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.REMOVECOUNT + this.DESCRIPTIONS[1] + this.CURSECOUNT + this.DESCRIPTIONS[2];
    }
    
    @Override
    public void onEquip() {
        this.cardsSelected = false;
		this.cursesSelected = false;
		this.cursesOpened = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), REMOVECOUNT, this.DESCRIPTIONS[3], false, false, false, true);
    }
    
    @Override
    public void update() {
        super.update();
        
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == REMOVECOUNT) {
            this.cardsSelected = true;
            //AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), Settings.WIDTH / 2.0f - 30.0f * Settings.scale - AbstractCard.IMG_WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            //AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(1), Settings.WIDTH / 2.0f + 30.0f * Settings.scale + AbstractCard.IMG_WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
            	if (card.type != AbstractCard.CardType.CURSE) {
                    AbstractDungeon.player.masterDeck.removeCard(card);
            	}
                AbstractDungeon.transformCard(card, false, AbstractDungeon.miscRng);
            }
            //AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        } else if (this.cardsSelected && !this.cursesSelected) {
        	if (!this.cursesOpened) {
        		this.cursesOpened = true;
        		final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    			for (int i=0; i < this.CURSEOPTIONS; i++) {
    				AbstractCard bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE).makeCopy();
    				while (bowlCurse.rarity == AbstractCard.CardRarity.SPECIAL) {
    					bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE).makeCopy();
    				}
    				UnlockTracker.markCardAsSeen(bowlCurse.cardID);
    				tmp.addToTop(bowlCurse);
    			}
    			if (AbstractDungeon.isScreenUp) {
    	            AbstractDungeon.dynamicBanner.hide();
    	            AbstractDungeon.overlayMenu.cancelButton.hide();
    	            AbstractDungeon.previousScreen = AbstractDungeon.screen;
    	        }
    	        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
    	        AbstractDungeon.gridSelectScreen.open(tmp, CURSECOUNT, this.DESCRIPTIONS[3], false, false, false, false);
    			
        	} else if (AbstractDungeon.gridSelectScreen.selectedCards.size() == CURSECOUNT) {
        		this.cursesSelected = true;
				for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
					if (AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0) {
						((Omamori)AbstractDungeon.player.getRelic("Omamori")).use();
					} else {
						AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card.makeCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
					}
				}
	            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
	            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        	}
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new Kintsugi();
    }
}
