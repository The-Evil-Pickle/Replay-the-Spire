package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.AbeCurse;
import com.megacrit.cardcrawl.mod.replay.cards.status.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.ui.*;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.io.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import basemod.*;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class DrinkMe extends ReplayAbstractRelic
{
    public static final String ID = "Replay:Drink Me";
    public static final ReplayIntSliderSetting SETTING_DRAW = new ReplayIntSliderSetting("Drink_Draw", "Cards Drawn", 2, 0, 5);
    public static final ReplayIntSliderSetting SETTING_DEX = new ReplayIntSliderSetting("Drink_Dex", "Dexterity", 5, 0, 10);
    public static final ReplayIntSliderSetting SETTING_REMOVE = new ReplayIntSliderSetting("Drink_Remove", "Card Removal", 3, 0, 10);
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_DRAW);
    	settings.add(SETTING_DEX);
    	settings.add(SETTING_REMOVE);
		return settings;
	}

    private boolean cardSelected;
    public DrinkMe() {
        super(ID, "betaRelic.png", RelicTier.BOSS, LandingSound.CLINK);
        this.cardSelected = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
	
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
        AbstractDungeon.player.masterHandSize += SETTING_DRAW.value;
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), SETTING_REMOVE.value, this.DESCRIPTIONS[4], false, false, false, true);
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == SETTING_REMOVE.value) {
            this.cardSelected = true;
            float cardx = -30.0f * (SETTING_REMOVE.value);
            float cardinc = 60.0f;
            for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, Settings.WIDTH / 2.0f + cardx * Settings.scale - AbstractCard.IMG_WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                cardx += cardinc;
            }
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
        AbstractDungeon.player.masterHandSize -= SETTING_DRAW.value;
    }
    
    @Override
    public void atPreBattle() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, SETTING_DEX.value), SETTING_DEX.value));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new DrinkMe();
    }
/*
    @Override
    public void onShuffle() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new AbeCurse(), 1, true, false));
    }
    */
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
}
