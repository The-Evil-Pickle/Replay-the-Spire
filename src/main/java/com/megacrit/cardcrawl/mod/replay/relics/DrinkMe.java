package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import basemod.*;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;
import replayTheSpire.patches.LoadAnimPatch;

public class DrinkMe extends ReplayAbstractRelic
{
    public static final String ID = "Replay:Drink Me";
    public static final ReplayIntSliderSetting SETTING_DRAW = new ReplayIntSliderSetting("Drink_Draw", "Cards Drawn", 2, 0, 5);
    public static final ReplayIntSliderSetting SETTING_DEX = new ReplayIntSliderSetting("Drink_Dex", "Dexterity", 4, 0, 5);
    public static final ReplayIntSliderSetting SETTING_REMOVE = new ReplayIntSliderSetting("Drink_Remove", "Card Removal", 3, 0, 5);
    public static final ReplayIntSliderSetting SETTING_ENERGY = new ReplayIntSliderSetting("Drink_Energy", "Cards for Energy", 6, 5, 10);
    public static final ReplayIntSliderSetting SETTING_HEALTH = new ReplayIntSliderSetting("Drink_Health", "HP Loss", 25, 0, 100, "%");
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_DRAW);
    	settings.add(SETTING_DEX);
    	settings.add(SETTING_REMOVE);
    	settings.add(SETTING_HEALTH);
    	settings.add(SETTING_ENERGY);
		return settings;
	}

    private boolean cardSelected;
    public DrinkMe() {
        super(ID, "betaRelic.png", RelicTier.BOSS, LandingSound.CLINK);
        this.cardSelected = true;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Synergy", this.DESCRIPTIONS[7]));
        this.initializeTips();
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SETTING_DRAW.value + this.DESCRIPTIONS[1] + SETTING_DEX.value + this.DESCRIPTIONS[2] + SETTING_ENERGY.value + this.DESCRIPTIONS[3] + SETTING_REMOVE.value + this.DESCRIPTIONS[4] + SETTING_HEALTH.value + this.DESCRIPTIONS[5];
    }
	
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
        AbstractDungeon.player.masterHandSize += SETTING_DRAW.value;
        if (SETTING_HEALTH.value > 0 && AbstractDungeon.player.maxHealth > 1) {
        	AbstractDungeon.player.decreaseMaxHealth((AbstractDungeon.player.maxHealth * SETTING_HEALTH.value) / 100);
        }
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        scalePlayer(AbstractDungeon.player, 1.5f);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), SETTING_REMOVE.value, this.DESCRIPTIONS[6], false, false, false, true);
    }

    private static void scalePlayer(AbstractPlayer player, float _scale) {
    	if (player == null || LoadAnimPatch.LAST_SKELETON_URL == null) {
    		return;
    	}
        //Skeleton skeleton = (Skeleton)ReflectionHacks.getPrivate(AbstractDungeon.player, AbstractCreature.class, "skeleton");
        TextureAtlas atlas = (TextureAtlas)ReflectionHacks.getPrivate(player, AbstractCreature.class, "atlas");
    	final SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(Settings.scale / _scale);
        final SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(LoadAnimPatch.LAST_SKELETON_URL));
        Skeleton skeleton = new Skeleton(skeletonData);
        ReflectionHacks.setPrivateInherited(player, AbstractPlayer.class, "skeleton", skeleton);
        skeleton.setColor(Color.WHITE);
        AnimationStateData stateData = new AnimationStateData(skeletonData);
        ReflectionHacks.setPrivateInherited(player, AbstractPlayer.class, "stateData", stateData);
        ReflectionHacks.setPrivateInherited(player, AbstractPlayer.class, "state", new AnimationState(stateData));
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
        //AbstractDungeon.player.increaseMaxHp(AbstractDungeon.player.maxHealth, false);
    }
    
    @Override
    public void atPreBattle() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, SETTING_DEX.value), SETTING_DEX.value));
        if (AbstractDungeon.player.hasRelic(TinyHouse.ID)) {
        	AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(AbstractDungeon.player, AbstractDungeon.player, 15));
        }
        this.counter = 0;
    }
    
    @Override
    public void atTurnStart() {
    	this.counter = 0;
    }
    
    @Override
    public void onVictory() {
    	this.counter = -1;
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
    	if (card != null && action != null) {
    		this.counter ++;
    		if (this.counter == SETTING_ENERGY.value) {
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
    		}
    	}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new DrinkMe();
    }
    
    public static void load(SpireConfig config) {
    	if(AbstractDungeon.player.hasRelic(ID)) {
    		scalePlayer(AbstractDungeon.player, 1.5f);
    	}
    }
}
