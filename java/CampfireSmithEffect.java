package com.megacrit.cardcrawl.vfx.campfire;

import com.megacrit.cardcrawl.localization.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import java.util.*;
import com.megacrit.cardcrawl.metrics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;

public class CampfireSmithEffect extends AbstractGameEffect
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DUR = 1.5f;
    private boolean openedScreen;
    private boolean selectedCard;
    private Color screenColor;
	private boolean secondUpgrade;
    
    public CampfireSmithEffect() {
        this.openedScreen = false;
        this.selectedCard = false;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5f;
        this.screenColor.a = 0.0f;
        AbstractDungeon.overlayMenu.proceedButton.hide();
		if (AbstractDungeon.player.hasRelic("Arrowhead")) {
			this.secondUpgrade = true;
		}
		else {
			this.secondUpgrade = false;
		}
    }
    
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }
        if (!this.selectedCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (final AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                final MetricData metricData = CardCrawlGame.metricData;
                ++metricData.campfire_upgraded;
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            }
			if (this.secondUpgrade == false) {
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				this.selectedCard = true;
				((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
			}
			else {
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, CampfireSmithEffect.TEXT[0], true);
				this.secondUpgrade = false;
			}
        }
        if (this.duration < 1.0f && !this.openedScreen) {
            this.openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, CampfireSmithEffect.TEXT[0], true);
            for (final AbstractRelic r : AbstractDungeon.player.relics) {
                r.onSmith();
            }
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
    
    public void render(final SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("CampfireSmithEffect");
        TEXT = CampfireSmithEffect.uiStrings.TEXT;
    }
}
