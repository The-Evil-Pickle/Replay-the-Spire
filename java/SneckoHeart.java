package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;
import ReplayTheSpireMod.*;
import tobyspowerhouse.powers.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.ui.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import java.io.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import basemod.*;

public class SneckoHeart extends AbstractRelic
{
    public static final String ID = "Snecko Heart";
    
	private boolean isFirstTurn;
	
    public SneckoHeart() {
        super("Snecko Heart", "betaRelic.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
	public boolean isActive() {
		return (this.pulse);
	}
	
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
	@Override
	public void atTurnStart() {
		if (!this.isFirstTurn) {
			this.pulse = true;
		}
	}
	@Override
	public void atBattleStartPreDraw() {
		this.isFirstTurn = true;
		this.pulse = true;
	}
	@Override
	public void onPlayerEndTurn() {
		this.isFirstTurn = false;
	}
    
    @Override
    public void atPreBattle() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TPH_ConfusionPower(AbstractDungeon.player)));
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
	
	public boolean checkCard(AbstractCard drawnCard) {
		if (this.isActive() && drawnCard != null && drawnCard.cost >= 0) {
			this.pulse = false;
			return true;
		}
		return false;
	}
	
	@Override
    public void renderLock(final SpriteBatch sb, final Color outlineColor) {
		final float rot = (float)ReflectionHacks.getPrivate((Object)this, (Class)AbstractRelic.class, "rotation");
        sb.setColor(outlineColor);
        sb.draw(ImageMaster.RELIC_LOCK_OUTLINE, this.currentX - 64.0f, this.currentY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_LOCK, this.currentX - 64.0f, this.currentY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        if (this.hb.hovered) {
            String unlockReq = UnlockTracker.unlockReqs.get(this.relicId);
            if (unlockReq == null) {
                unlockReq = "Missing unlock req.";
            }
            if (InputHelper.mX < 1400.0f * Settings.scale) {
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.RELIC_VIEW && InputHelper.mY < Settings.HEIGHT / 5.0f) {
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY + 100.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
                }
                else {
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
                }
            }
            else {
                TipHelper.renderGenericTip(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[3], unlockReq);
            }
            float tmpX = this.currentX;
            float tmpY = this.currentY;
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.RELIC_LOCK, tmpX - 64.0f, tmpY - 64.0f, 64.0f, 64.0f, 128.0f, 128.0f, this.scale, this.scale, rot, 0, 0, 128, 128, false, false);
        }
        this.hb.render(sb);
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new SneckoHeart();
    }
}
