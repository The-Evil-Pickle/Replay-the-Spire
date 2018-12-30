package com.megacrit.cardcrawl.mod.replay.ui.campfire;

import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import java.util.*;
import com.megacrit.cardcrawl.metrics.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;

public class CampfireTransformEffect extends AbstractGameEffect
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DUR = 1.5f;
    private boolean openedScreen;
    private boolean selectedCard;
    private Color screenColor;
    private boolean makeFree;
    
    public CampfireTransformEffect() {
        this.openedScreen = false;
        this.selectedCard = false;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5f;
        this.screenColor.a = 0.0f;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }
    
    @Override
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }
        if (!this.selectedCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forTransform) {
            for (final AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                 AbstractDungeon.player.masterDeck.removeCard(c);
                 AbstractDungeon.transformCard(c);
                 AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.selectedCard = true;
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
        }
        if (this.duration < 1.0f && !this.openedScreen) {
            this.openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, CampfireTransformEffect.TEXT[2], false, true, true, false);
        }
        if (this.duration < 0.0f) {
            this.isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0f;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }
    }
    
    private void updateBlackScreenColor() {
        if (this.duration > 1.0f) {
            this.screenColor.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - 1.0f) * 2.0f);
        }
        else {
            this.screenColor.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration / 1.5f);
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, Settings.WIDTH, Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Polymerize Option");
        TEXT = CampfireTransformEffect.uiStrings.TEXT;
    }

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
