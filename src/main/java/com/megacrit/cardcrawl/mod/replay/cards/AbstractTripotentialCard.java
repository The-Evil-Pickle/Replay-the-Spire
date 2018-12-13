package com.megacrit.cardcrawl.mod.replay.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.abstracts.CustomCard;

public abstract class AbstractTripotentialCard extends CustomCard {

	public AbstractTripotentialCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
		
	}
	
	public abstract void upgrade(int branch);
	
	@Override
	public void upgrade() {
		if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof RestRoom) {
			//choose
			
		} else {
			//random
			this.upgrade(1);
		}
	}
	@Override
    public void renderInLibrary(final SpriteBatch sb) {
    	super.renderInLibrary(sb);
    	if (this.isOnScreen() && SingleCardViewPopup.isViewingUpgrade && this.isSeen && !this.isLocked) {
            final AbstractTripotentialCard copy = (AbstractTripotentialCard)this.makeCopy();
            copy.current_x = this.current_x / 2.0f;
            copy.current_y = this.current_y;
            copy.drawScale = this.drawScale;
            copy.upgrade(0);
            copy.displayUpgrades();
            copy.render(sb);
            final AbstractTripotentialCard copy2 = (AbstractTripotentialCard)this.makeCopy();
            copy2.current_x = this.current_x * 1.5f;
            copy2.current_y = this.current_y;
            copy2.drawScale = this.drawScale;
            copy2.upgrade(2);
            copy2.displayUpgrades();
            copy2.render(sb);
        }
    }
    private boolean isOnScreen() {
        return this.current_y >= -200.0f * Settings.scale && this.current_y <= Settings.HEIGHT + 200.0f * Settings.scale;
    }
}
