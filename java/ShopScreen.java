package com.megacrit.cardcrawl.shop;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.daily.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import java.util.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.metrics.*;
import com.megacrit.cardcrawl.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.*;
import org.apache.logging.log4j.*;

public class ShopScreen
{
    private static final Logger logger;
    private static final TutorialStrings tutorialStrings;
    public static final String[] MSG;
    public static final String[] LABEL;
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    public boolean isActive;
    private Texture rugImg;
    private float rugY;
    private static final float RUG_SPEED = 5.0f;
    private ArrayList<AbstractCard> coloredCards;
    private ArrayList<AbstractCard> colorlessCards;
    private static final float DRAW_START_X;
    private static final int NUM_CARDS_PER_LINE = 5;
    private static final float CARD_PRICE_JITTER = 0.1f;
    private static final float TOP_ROW_Y;
    private static final float BOTTOM_ROW_Y;
    private float speechTimer;
    private static final float MIN_IDLE_MSG_TIME = 40.0f;
    private static final float MAX_IDLE_MSG_TIME = 60.0f;
    private static final float SPEECH_DURATION = 4.0f;
    private static final float SPEECH_TEXT_R_X;
    private static final float SPEECH_TEXT_L_X;
    private static final float SPEECH_TEXT_Y;
    private ShopSpeechBubble speechBubble;
    private SpeechTextEffect dialogTextEffect;
    private static final String WELCOME_MSG;
    private ArrayList<String> idleMessages;
    private boolean saidWelcome;
    private boolean somethingHovered;
    private ArrayList<StoreRelic> relics;
    private static final float RELIC_PRICE_JITTER = 0.05f;
    private ArrayList<StorePotion> potions;
    private static final float POTION_PRICE_JITTER = 0.05f;
    public boolean purgeAvailable;
    public static int purgeCost;
    public static int actualPurgeCost;
    private static final int PURGE_COST_RAMP = 25;
    private boolean purgeHovered;
    private float purgeCardX;
    private float purgeCardY;
    private float purgeCardScale;
    private FloatyEffect f_effect;
    private float handTimer;
    private float handX;
    private float handY;
    private float handTargetX;
    private float handTargetY;
    private Texture handImg;
    private static final float HAND_SPEED = 6.0f;
    private static float HAND_W;
    private static float HAND_H;
    private float notHoveredTimer;
    private static final float GOLD_IMG_WIDTH;
    private static final float COLORLESS_PRICE_BUMP = 1.3f;
    private OnSaleTag saleTag;
	private DoubleTag doubleTag;
	private AbstractCard doubledCard;
	private int tagType;
    private static final float GOLD_IMG_OFFSET_X;
    private static final float GOLD_IMG_OFFSET_Y;
    private static final float PRICE_TEXT_OFFSET_X;
    private static final float PRICE_TEXT_OFFSET_Y;
    
    public ShopScreen() {
        this.isActive = true;
        this.rugY = Settings.HEIGHT;
        this.coloredCards = new ArrayList<AbstractCard>();
        this.colorlessCards = new ArrayList<AbstractCard>();
        this.speechTimer = 0.0f;
        this.speechBubble = null;
        this.dialogTextEffect = null;
        this.idleMessages = new ArrayList<String>();
        this.saidWelcome = false;
        this.somethingHovered = false;
        this.relics = new ArrayList<StoreRelic>();
        this.potions = new ArrayList<StorePotion>();
        this.purgeAvailable = false;
        this.purgeHovered = false;
        this.purgeCardScale = 1.0f;
        this.f_effect = new FloatyEffect(20.0f, 0.1f);
        this.handTimer = 1.0f;
        this.handX = Settings.WIDTH / 2.0f;
        this.handY = Settings.HEIGHT;
        this.handTargetX = 0.0f;
        this.handTargetY = Settings.HEIGHT;
        this.notHoveredTimer = 0.0f;
    }
    
    public void init(final ArrayList<AbstractCard> coloredCards, final ArrayList<AbstractCard> colorlessCards) {
        Collections.addAll(this.idleMessages, ShopScreen.TEXT);
        this.rugImg = ImageMaster.loadImage("images/npcs/rug.png");
        this.handImg = ImageMaster.loadImage("images/npcs/merchantHand.png");
        ShopScreen.HAND_W = this.handImg.getWidth() * Settings.scale;
        ShopScreen.HAND_H = this.handImg.getHeight() * Settings.scale;
        this.coloredCards.clear();
        this.colorlessCards.clear();
        this.coloredCards = coloredCards;
        this.colorlessCards = colorlessCards;
        this.initCards();
        this.initRelics();
        this.initPotions();
        this.purgeAvailable = true;
        this.purgeCardY = -1000.0f;
        this.purgeCardX = 1400.0f * Settings.scale;
        this.purgeCardScale = 0.7f;
        ShopScreen.actualPurgeCost = ShopScreen.purgeCost;
        if (AbstractDungeon.player.hasRelic("The Courier")) {
            this.applyDiscount(0.8f);
        }
        if (AbstractDungeon.player.hasRelic("Membership Card")) {
            this.applyDiscount(0.8f);
        }
        if (DailyMods.mods.get("Vintage")) {
            this.applyDiscount(1.25f);
        }
        if (AbstractDungeon.player.hasRelic("Smiling Mask")) {
            ShopScreen.actualPurgeCost = 50;
        }
    }
    
    public static void resetPurgeCost() {
        ShopScreen.purgeCost = 75;
        ShopScreen.actualPurgeCost = 75;
    }
    
    private void initCards() {
        final int tmp = (int)(Settings.WIDTH - ShopScreen.DRAW_START_X * 2.0f - AbstractCard.IMG_WIDTH_S * 5.0f) / 4;
        final float padX = (int)(tmp + AbstractCard.IMG_WIDTH_S);
        for (int i = 0; i < this.coloredCards.size(); ++i) {
            final float tmpPrice = AbstractCard.getPrice(this.coloredCards.get(i).rarity) * AbstractDungeon.merchantRng.random(0.9f, 1.1f);
            this.coloredCards.get(i).price = (int)tmpPrice;
            this.coloredCards.get(i).current_x = Settings.WIDTH / 2;
            this.coloredCards.get(i).target_x = ShopScreen.DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0f + padX * i;
        }
        for (int i = 0; i < this.colorlessCards.size(); ++i) {
            float tmpPrice = AbstractCard.getPrice(this.colorlessCards.get(i).rarity) * AbstractDungeon.merchantRng.random(0.9f, 1.1f);
            tmpPrice *= 1.3f;
            this.colorlessCards.get(i).price = (int)tmpPrice;
            this.colorlessCards.get(i).current_x = Settings.WIDTH / 2;
            this.colorlessCards.get(i).target_x = ShopScreen.DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0f + padX * i;
        }
        final AbstractCard abstractCard;
		if (AbstractDungeon.merchantRng.random(0, 1) == 0) {
			final AbstractCard saleCard = abstractCard = this.coloredCards.get(AbstractDungeon.merchantRng.random(0, 4));
			abstractCard.price /= 2;
			this.saleTag = new OnSaleTag(saleCard);
			this.tagType = 1;
		}
		else {
			final AbstractCard doubleCard = abstractCard = this.coloredCards.get(AbstractDungeon.merchantRng.random(0, 4));
			this.doubleTag = new DoubleTag(doubleCard);
			this.doubledCard = doubleCard;
			this.tagType = 2;
		}
        this.setStartingCardPositions();
    }
    
    public static void purgeCard() {
        AbstractDungeon.player.loseGold(ShopScreen.actualPurgeCost);
        CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1f);
        ShopScreen.purgeCost += 25;
        ShopScreen.actualPurgeCost = ShopScreen.purgeCost;
        if (AbstractDungeon.player.hasRelic("Smiling Mask")) {
            ShopScreen.actualPurgeCost = 50;
        }
        else if (AbstractDungeon.player.hasRelic("The Courier") && AbstractDungeon.player.hasRelic("Membership Card")) {
            ShopScreen.actualPurgeCost = MathUtils.round(ShopScreen.purgeCost * 0.8f * 0.8f);
        }
        else if (AbstractDungeon.player.hasRelic("The Courier")) {
            ShopScreen.actualPurgeCost = MathUtils.round(ShopScreen.purgeCost * 0.8f);
        }
        else if (AbstractDungeon.player.hasRelic("Membership Card")) {
            ShopScreen.actualPurgeCost = MathUtils.round(ShopScreen.purgeCost * 0.8f);
        }
    }
    
    public void updatePurge() {
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            purgeCard();
            for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                CardCrawlGame.metricData.items_purged.add(card.cardID);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.shopScreen.purgeAvailable = false;
        }
    }
    
    public static String getCantBuyMsg() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add(ShopScreen.NAMES[1]);
        list.add(ShopScreen.NAMES[2]);
        list.add(ShopScreen.NAMES[3]);
        list.add(ShopScreen.NAMES[4]);
        list.add(ShopScreen.NAMES[5]);
        list.add(ShopScreen.NAMES[6]);
        return list.get(MathUtils.random(list.size() - 1));
    }
    
    public static String getBuyMsg() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add(ShopScreen.NAMES[7]);
        list.add(ShopScreen.NAMES[8]);
        list.add(ShopScreen.NAMES[9]);
        list.add(ShopScreen.NAMES[10]);
        list.add(ShopScreen.NAMES[11]);
        return list.get(MathUtils.random(list.size() - 1));
    }
    
    public void applyDiscount(final float multiplier) {
        for (final StoreRelic r : this.relics) {
            r.price = MathUtils.round(r.price * multiplier);
        }
        for (final StorePotion p : this.potions) {
            p.price = MathUtils.round(p.price * multiplier);
        }
        for (final AbstractCard c : this.coloredCards) {
            c.price = MathUtils.round(c.price * multiplier);
        }
        for (final AbstractCard c : this.colorlessCards) {
            c.price = MathUtils.round(c.price * multiplier);
        }
        if (AbstractDungeon.player.hasRelic("Smiling Mask")) {
            ShopScreen.actualPurgeCost = 50;
        }
        else {
            ShopScreen.actualPurgeCost = MathUtils.round(ShopScreen.purgeCost * multiplier);
        }
    }
    
    private void initRelics() {
        this.relics.clear();
        this.relics = new ArrayList<StoreRelic>();
        for (int i = 0; i < 3; ++i) {
            AbstractRelic tempRelic;
            for (tempRelic = AbstractDungeon.returnRandomRelicEnd(rollRelicTier()); tempRelic instanceof OldCoin || tempRelic instanceof SmilingMask || tempRelic instanceof Courier; tempRelic = AbstractDungeon.returnRandomRelicEnd(rollRelicTier())) {}
            final StoreRelic relic = new StoreRelic(tempRelic, i, this);
            if (!Settings.isDailyRun) {
                relic.price = MathUtils.round(relic.price * AbstractDungeon.merchantRng.random(0.95f, 1.05f));
            }
            this.relics.add(relic);
        }
    }
    
    private void initPotions() {
        this.potions.clear();
        this.potions = new ArrayList<StorePotion>();
        for (int i = 0; i < 3; ++i) {
            final StorePotion potion = new StorePotion(AbstractDungeon.returnRandomPotion(15), i, this);
            if (!Settings.isDailyRun) {
                potion.price = MathUtils.round(potion.price * AbstractDungeon.merchantRng.random(0.95f, 1.05f));
            }
            this.potions.add(potion);
        }
    }
    
    public void getNewPrice(final StoreRelic r) {
        int retVal = r.price;
        if (!Settings.isDailyRun) {
            retVal = MathUtils.round(retVal * AbstractDungeon.merchantRng.random(0.95f, 1.05f));
        }
        if (AbstractDungeon.player.hasRelic("The Courier")) {
            this.applyDiscountToRelic(retVal, 0.8f);
        }
        if (AbstractDungeon.player.hasRelic("Membership Card")) {
            this.applyDiscountToRelic(retVal, 0.8f);
        }
        if (DailyMods.mods.get("Vintage")) {
            this.applyDiscountToRelic(retVal, 1.25f);
        }
        r.price = retVal;
    }
    
    public void getNewPrice(final StorePotion r) {
        int retVal = r.price;
        if (!Settings.isDailyRun) {
            retVal = MathUtils.round(retVal * AbstractDungeon.merchantRng.random(0.95f, 1.05f));
        }
        if (AbstractDungeon.player.hasRelic("The Courier")) {
            this.applyDiscountToRelic(retVal, 0.8f);
        }
        if (AbstractDungeon.player.hasRelic("Membership Card")) {
            this.applyDiscountToRelic(retVal, 0.8f);
        }
        if (DailyMods.mods.get("Vintage")) {
            this.applyDiscountToRelic(retVal, 1.25f);
        }
        r.price = retVal;
    }
    
    private int applyDiscountToRelic(final int price, final float multiplier) {
        return MathUtils.round(price * multiplier);
    }
    
    public static AbstractRelic.RelicTier rollRelicTier() {
        final int roll = AbstractDungeon.merchantRng.random(99);
        ShopScreen.logger.info("ROLL " + roll);
        if (roll < 5) {
            return AbstractRelic.RelicTier.SHOP;
        }
        if (roll < 51) {
            return AbstractRelic.RelicTier.UNCOMMON;
        }
        if (roll < 83) {
            return AbstractRelic.RelicTier.COMMON;
        }
        return AbstractRelic.RelicTier.RARE;
    }
    
    private void setStartingCardPositions() {
        final int tmp = (int)(Settings.WIDTH - ShopScreen.DRAW_START_X * 2.0f - AbstractCard.IMG_WIDTH_S * 5.0f) / 4;
        final float padX = (int)(tmp + AbstractCard.IMG_WIDTH_S) + 10.0f * Settings.scale;
        for (int i = 0; i < this.coloredCards.size(); ++i) {
            this.coloredCards.get(i).updateHoverLogic();
            this.coloredCards.get(i).targetDrawScale = 0.75f;
            this.coloredCards.get(i).current_x = ShopScreen.DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0f + padX * i;
            this.coloredCards.get(i).target_x = ShopScreen.DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0f + padX * i;
            this.coloredCards.get(i).target_y = 9999.0f * Settings.scale;
            this.coloredCards.get(i).current_y = 9999.0f * Settings.scale;
        }
        for (int i = 0; i < this.colorlessCards.size(); ++i) {
            this.colorlessCards.get(i).updateHoverLogic();
            this.colorlessCards.get(i).targetDrawScale = 0.75f;
            this.colorlessCards.get(i).current_x = ShopScreen.DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0f + padX * i;
            this.colorlessCards.get(i).target_x = ShopScreen.DRAW_START_X + AbstractCard.IMG_WIDTH_S / 2.0f + padX * i;
            this.colorlessCards.get(i).target_y = 9999.0f * Settings.scale;
            this.colorlessCards.get(i).current_y = 9999.0f * Settings.scale;
        }
    }
    
    public void open() {
        CardCrawlGame.sound.play("SHOP_OPEN");
        this.setStartingCardPositions();
        this.purgeCardY = -1000.0f;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.SHOP;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.cancelButton.show(ShopScreen.NAMES[12]);
        for (final StoreRelic r : this.relics) {
            r.hide();
        }
        for (final StorePotion p : this.potions) {
            p.hide();
        }
        this.rugY = Settings.HEIGHT;
        this.handX = Settings.WIDTH / 2.0f;
        this.handY = Settings.HEIGHT;
        this.handTargetX = this.handX;
        this.handTargetY = this.handY;
        this.handTimer = 1.0f;
        this.speechTimer = 1.5f;
        this.speechBubble = null;
        this.dialogTextEffect = null;
        AbstractDungeon.overlayMenu.showBlackScreen();
        for (final AbstractCard c : this.coloredCards) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }
        for (final AbstractCard c : this.colorlessCards) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }
        for (final StoreRelic r : this.relics) {
            if (r.relic != null) {
                UnlockTracker.markRelicAsSeen(r.relic.relicId);
            }
        }
    }
    
    public void update() {
        if (this.handTimer != 0.0f) {
            this.handTimer -= Gdx.graphics.getDeltaTime();
            if (this.handTimer < 0.0f) {
                this.handTimer = 0.0f;
            }
        }
        this.f_effect.update();
        this.somethingHovered = false;
        this.updatePurgeCard();
        this.updatePurge();
        this.updateRelics();
        this.updatePotions();
        this.updateRug();
        this.updateSpeech();
        this.updateCards();
        this.updateHand();
        AbstractCard hoveredCard = null;
        for (final AbstractCard c : this.coloredCards) {
            if (c.hb.hovered) {
                hoveredCard = c;
                this.somethingHovered = true;
                this.moveHand(c.current_x - AbstractCard.IMG_WIDTH / 2.0f, c.current_y);
                break;
            }
        }
        for (final AbstractCard c : this.colorlessCards) {
            if (c.hb.hovered) {
                hoveredCard = c;
                this.somethingHovered = true;
                this.moveHand(c.current_x - AbstractCard.IMG_WIDTH / 2.0f, c.current_y);
                break;
            }
        }
        if (!this.somethingHovered) {
            this.notHoveredTimer += Gdx.graphics.getDeltaTime();
            if (this.notHoveredTimer > 1.0f) {
                this.handTargetY = Settings.HEIGHT;
            }
        }
        else {
            this.notHoveredTimer = 0.0f;
        }
        if (hoveredCard != null && InputHelper.justClickedLeft) {
            hoveredCard.hb.clickStarted = true;
        }
        if (hoveredCard != null && InputHelper.justClickedRight) {
            CardCrawlGame.cardPopup.open(hoveredCard);
        }
        if (hoveredCard != null && hoveredCard.hb.clicked) {
            hoveredCard.hb.clicked = false;
            if (AbstractDungeon.player.gold >= hoveredCard.price) {
                CardCrawlGame.metricData.items_purchased.add(hoveredCard.cardID);
                this.coloredCards.remove(hoveredCard);
                this.colorlessCards.remove(hoveredCard);
                AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(hoveredCard, hoveredCard.current_x, hoveredCard.current_y));
				if (hoveredCard.equals(doubledCard)) {
					AbstractCard secondCard;
					secondCard = doubledCard.makeCopy();
					AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(secondCard, doubledCard.current_x, doubledCard.current_y));
				}
                AbstractDungeon.player.loseGold(hoveredCard.price);
                CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1f);
                if (AbstractDungeon.player.hasRelic("The Courier")) {
                    if (hoveredCard.color == AbstractCard.CardColor.COLORLESS) {
                        AbstractCard.CardRarity tempRarity = AbstractCard.CardRarity.UNCOMMON;
                        if (AbstractDungeon.merchantRng.random() < AbstractDungeon.colorlessRareChance) {
                            tempRarity = AbstractCard.CardRarity.RARE;
                        }
                        final AbstractCard tmp = AbstractDungeon.getColorlessCardFromPool(tempRarity).makeCopy();
                        tmp.current_x = hoveredCard.current_x;
                        tmp.current_y = hoveredCard.current_y;
                        tmp.target_x = tmp.current_x;
                        tmp.target_y = tmp.current_y;
                        this.setPrice(tmp);
                        this.colorlessCards.add(tmp);
                    }
                    else {
                        final AbstractCard tmp2 = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), hoveredCard.type, false).makeCopy();
                        tmp2.current_x = hoveredCard.current_x;
                        tmp2.current_y = hoveredCard.current_y;
                        tmp2.target_x = tmp2.current_x;
                        tmp2.target_y = tmp2.current_y;
                        this.setPrice(tmp2);
                        this.coloredCards.add(tmp2);
                    }
                }
                hoveredCard = null;
                InputHelper.justClickedLeft = false;
                this.notHoveredTimer = 1.0f;
                this.speechTimer = MathUtils.random(40.0f, 60.0f);
                this.playBuySfx();
                this.createSpeech(getBuyMsg());
            }
            else {
                this.speechTimer = MathUtils.random(40.0f, 60.0f);
                this.playCantBuySfx();
                this.createSpeech(getCantBuyMsg());
            }
        }
    }
    
    private void updateCards() {
        for (int i = 0; i < this.coloredCards.size(); ++i) {
            this.coloredCards.get(i).update();
            this.coloredCards.get(i).updateHoverLogic();
            this.coloredCards.get(i).current_y = this.rugY + ShopScreen.TOP_ROW_Y;
            this.coloredCards.get(i).target_y = this.coloredCards.get(i).current_y;
        }
        for (int i = 0; i < this.colorlessCards.size(); ++i) {
            this.colorlessCards.get(i).update();
            this.colorlessCards.get(i).updateHoverLogic();
            this.colorlessCards.get(i).current_y = this.rugY + ShopScreen.BOTTOM_ROW_Y;
            this.colorlessCards.get(i).target_y = this.colorlessCards.get(i).current_y;
        }
    }
    
    private void setPrice(final AbstractCard card) {
        float tmpPrice = AbstractCard.getPrice(card.rarity) * AbstractDungeon.merchantRng.random(0.9f, 1.1f);
        if (card.color == AbstractCard.CardColor.COLORLESS) {
            tmpPrice *= 1.3f;
        }
        if (AbstractDungeon.player.hasRelic("The Courier")) {
            tmpPrice *= 0.8f;
        }
        if (AbstractDungeon.player.hasRelic("Membership Card")) {
            tmpPrice *= 0.8f;
        }
        tmpPrice = MathUtils.round(tmpPrice * 1.25f);
        card.price = (int)tmpPrice;
    }
    
    public void moveHand(final float x, final float y) {
        this.handTargetX = x - 50.0f * Settings.scale;
        this.handTargetY = y + 90.0f * Settings.scale;
    }
    
    private void updatePurgeCard() {
        this.purgeCardX = 1554.0f * Settings.scale;
        this.purgeCardY = this.rugY + ShopScreen.BOTTOM_ROW_Y;
        if (this.purgeAvailable) {
            final float CARD_W = 110.0f * Settings.scale;
            final float CARD_H = 150.0f * Settings.scale;
            if (InputHelper.mX > this.purgeCardX - CARD_W && InputHelper.mX < this.purgeCardX + CARD_W && InputHelper.mY > this.purgeCardY - CARD_H && InputHelper.mY < this.purgeCardY + CARD_H) {
                this.purgeHovered = true;
                this.moveHand(this.purgeCardX - AbstractCard.IMG_WIDTH / 2.0f, this.purgeCardY);
                this.somethingHovered = true;
                this.purgeCardScale = Settings.scale;
            }
            else {
                this.purgeHovered = false;
            }
            if (!this.purgeHovered) {
                this.purgeCardScale = MathHelper.cardScaleLerpSnap(this.purgeCardScale, 0.75f * Settings.scale);
            }
            else {
                if (InputHelper.justClickedLeft) {
                    this.purgeHovered = false;
                    if (AbstractDungeon.player.gold >= ShopScreen.actualPurgeCost) {
                        final MetricData metricData = CardCrawlGame.metricData;
                        ++metricData.purchased_purges;
                        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.SHOP;
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, ShopScreen.NAMES[13], false, false, true, true);
                    }
                    else {
                        this.playCantBuySfx();
                        this.createSpeech(getCantBuyMsg());
                    }
                }
                TipHelper.renderGenericTip(InputHelper.mX - 360.0f * Settings.scale, InputHelper.mY - 70.0f * Settings.scale, ShopScreen.LABEL[0], ShopScreen.MSG[0] + 25 + ShopScreen.MSG[1]);
            }
        }
        else {
            this.purgeCardScale = MathHelper.cardScaleLerpSnap(this.purgeCardScale, 0.75f * Settings.scale);
        }
    }
    
    private void updateRelics() {
        for (final StoreRelic r : this.relics) {
            r.update(this.rugY);
        }
    }
    
    private void updatePotions() {
        for (final StorePotion p : this.potions) {
            p.update(this.rugY);
        }
    }
    
    public void createSpeech(final String msg) {
        final boolean isRight = MathUtils.randomBoolean();
        final float x = MathUtils.random(660.0f, 1260.0f) * Settings.scale;
        final float y = Settings.HEIGHT - 380.0f * Settings.scale;
        this.speechBubble = new ShopSpeechBubble(x, y, 4.0f, msg, isRight);
        float offset_x = 0.0f;
        if (isRight) {
            offset_x = ShopScreen.SPEECH_TEXT_R_X;
        }
        else {
            offset_x = ShopScreen.SPEECH_TEXT_L_X;
        }
        this.dialogTextEffect = new SpeechTextEffect(x + offset_x, y + ShopScreen.SPEECH_TEXT_Y, 4.0f, msg, DialogWord.AppearEffect.BUMP_IN);
    }
    
    private void updateSpeech() {
        if (this.speechBubble != null) {
            this.speechBubble.update();
            if (this.speechBubble.hitbox.hovered && this.speechBubble.duration > 0.3f) {
                this.speechBubble.duration = 0.3f;
                this.dialogTextEffect.duration = 0.3f;
            }
            if (this.speechBubble.isDone) {
                this.speechBubble = null;
            }
        }
        if (this.dialogTextEffect != null) {
            this.dialogTextEffect.update();
            if (this.dialogTextEffect.isDone) {
                this.dialogTextEffect = null;
            }
        }
        this.speechTimer -= Gdx.graphics.getDeltaTime();
        if (this.speechBubble == null && this.dialogTextEffect == null && this.speechTimer <= 0.0f) {
            this.speechTimer = MathUtils.random(40.0f, 60.0f);
            if (!this.saidWelcome) {
                this.createSpeech(ShopScreen.WELCOME_MSG);
                this.saidWelcome = true;
                this.welcomeSfx();
            }
            else {
                this.playMiscSfx();
                this.createSpeech(this.getIdleMsg());
            }
        }
    }
    
    private void welcomeSfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        }
        else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        }
        else {
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
        }
    }
    
    private void playMiscSfx() {
        final int roll = MathUtils.random(5);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_MA");
        }
        else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_MB");
        }
        else if (roll == 2) {
            CardCrawlGame.sound.play("VO_MERCHANT_MC");
        }
        else if (roll == 3) {
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        }
        else if (roll == 4) {
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        }
        else {
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
        }
    }
    
    public void playBuySfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_KA");
        }
        else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_KB");
        }
        else {
            CardCrawlGame.sound.play("VO_MERCHANT_KC");
        }
    }
    
    public void playCantBuySfx() {
        final int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MERCHANT_2A");
        }
        else if (roll == 1) {
            CardCrawlGame.sound.play("VO_MERCHANT_2B");
        }
        else {
            CardCrawlGame.sound.play("VO_MERCHANT_2C");
        }
    }
    
    private String getIdleMsg() {
        return this.idleMessages.get(MathUtils.random(this.idleMessages.size() - 1));
    }
    
    private void updateRug() {
        if (this.rugY != 0.0f) {
            this.rugY = MathUtils.lerp(this.rugY, 0.0f, Gdx.graphics.getDeltaTime() * 5.0f);
            if (Math.abs(this.rugY - 0.0f) < 0.5f) {
                this.rugY = 0.0f;
            }
        }
    }
    
    private void updateHand() {
        if (this.handTimer == 0.0f) {
            if (this.handX != this.handTargetX) {
                this.handX = MathUtils.lerp(this.handX, this.handTargetX, Gdx.graphics.getDeltaTime() * 6.0f);
            }
            if (this.handY != this.handTargetY) {
                if (this.handY > this.handTargetY) {
                    this.handY = MathUtils.lerp(this.handY, this.handTargetY, Gdx.graphics.getDeltaTime() * 6.0f);
                }
                else {
                    this.handY = MathUtils.lerp(this.handY, this.handTargetY, Gdx.graphics.getDeltaTime() * 6.0f / 4.0f);
                }
            }
        }
    }
    
    public void render(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.rugImg, 0.0f, this.rugY, (float)Settings.WIDTH, (float)Settings.HEIGHT);
        this.renderCardsAndPrices(sb);
        this.renderRelics(sb);
        this.renderPotions(sb);
        this.renderPurge(sb);
        sb.draw(this.handImg, this.handX + this.f_effect.x, this.handY + this.f_effect.y, ShopScreen.HAND_W, ShopScreen.HAND_H);
        if (this.speechBubble != null) {
            this.speechBubble.render(sb);
        }
        if (this.dialogTextEffect != null) {
            this.dialogTextEffect.render(sb);
        }
    }
    
    private void renderRelics(final SpriteBatch sb) {
        for (final StoreRelic r : this.relics) {
            r.render(sb);
        }
    }
    
    private void renderPotions(final SpriteBatch sb) {
        for (final StorePotion p : this.potions) {
            p.render(sb);
        }
    }
    
    private void renderCardsAndPrices(final SpriteBatch sb) {
        for (final AbstractCard c : this.coloredCards) {
            c.render(sb);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, c.current_x + ShopScreen.GOLD_IMG_OFFSET_X, c.current_y + ShopScreen.GOLD_IMG_OFFSET_Y - (c.drawScale - 0.75f) * 200.0f * Settings.scale, ShopScreen.GOLD_IMG_WIDTH, ShopScreen.GOLD_IMG_WIDTH);
            Color color = Color.WHITE.cpy();
            if (c.price > AbstractDungeon.player.gold) {
                color = Color.SALMON.cpy();
            }
            else if (this.tagType == 1) {
                if (c.equals(this.saleTag.card)) {
					color = Color.SKY.cpy();
				}
            }
            else if (this.tagType == 2) {
                if (c.equals(this.doubleTag.card)) {
					color = Color.SKY.cpy();
				}
            }
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(c.price), c.current_x + ShopScreen.PRICE_TEXT_OFFSET_X, c.current_y + ShopScreen.PRICE_TEXT_OFFSET_Y - (c.drawScale - 0.75f) * 200.0f * Settings.scale, color);
        }
        for (final AbstractCard c : this.colorlessCards) {
            c.render(sb);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, c.current_x + ShopScreen.GOLD_IMG_OFFSET_X, c.current_y + ShopScreen.GOLD_IMG_OFFSET_Y - (c.drawScale - 0.75f) * 200.0f * Settings.scale, ShopScreen.GOLD_IMG_WIDTH, ShopScreen.GOLD_IMG_WIDTH);
            Color color = Color.WHITE.cpy();
            if (c.price > AbstractDungeon.player.gold) {
                color = Color.SALMON.cpy();
            }
            else if (this.tagType == 1) {
                if (c.equals(this.saleTag.card)) {
					color = Color.SKY.cpy();
				}
            }
            else if (this.tagType == 2) {
                if (c.equals(this.doubleTag.card)) {
					color = Color.SKY.cpy();
				}
            }
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(c.price), c.current_x + ShopScreen.PRICE_TEXT_OFFSET_X, c.current_y + ShopScreen.PRICE_TEXT_OFFSET_Y - (c.drawScale - 0.75f) * 200.0f * Settings.scale, color);
        }
        if (this.tagType == 1) {
			if (this.coloredCards.contains(this.saleTag.card)) {
				this.saleTag.render(sb);
			}
			if (this.colorlessCards.contains(this.saleTag.card)) {
				this.saleTag.render(sb);
			}
		}
        else if (this.tagType == 2) {
			if (this.coloredCards.contains(this.doubleTag.card)) {
				this.doubleTag.render(sb);
			}
			if (this.colorlessCards.contains(this.doubleTag.card)) {
				this.doubleTag.render(sb);
			}
		}
        for (final AbstractCard c : this.coloredCards) {
            c.renderCardTip(sb);
        }
        for (final AbstractCard c : this.colorlessCards) {
            c.renderCardTip(sb);
        }
    }
    
    private void renderPurge(final SpriteBatch sb) {
        sb.setColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
        sb.draw(ImageMaster.CARD_SKILL_BG_SILHOUETTE, this.purgeCardX - 256.0f + 18.0f * Settings.scale, this.purgeCardY - 256.0f - 14.0f * Settings.scale, 256.0f, 256.0f, 512.0f, 512.0f, this.purgeCardScale, this.purgeCardScale, 0.0f, 0, 0, 512, 512, false, false);
        sb.setColor(Color.WHITE);
        if (this.purgeAvailable) {
            sb.draw(ImageMaster.PURGE_CARD_IMG, this.purgeCardX - 256.0f, this.purgeCardY - 256.0f, 256.0f, 256.0f, 512.0f, 512.0f, this.purgeCardScale, this.purgeCardScale, 0.0f, 0, 0, 512, 512, false, false);
            sb.draw(ImageMaster.UI_GOLD, this.purgeCardX + ShopScreen.GOLD_IMG_OFFSET_X, this.purgeCardY + ShopScreen.GOLD_IMG_OFFSET_Y - (this.purgeCardScale / Settings.scale - 0.75f) * 200.0f * Settings.scale, ShopScreen.GOLD_IMG_WIDTH, ShopScreen.GOLD_IMG_WIDTH);
            Color color = Color.WHITE;
            if (ShopScreen.actualPurgeCost > AbstractDungeon.player.gold) {
                color = Color.SALMON;
            }
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, Integer.toString(ShopScreen.actualPurgeCost), this.purgeCardX + ShopScreen.PRICE_TEXT_OFFSET_X, this.purgeCardY + ShopScreen.PRICE_TEXT_OFFSET_Y - (this.purgeCardScale / Settings.scale - 0.75f) * 200.0f * Settings.scale, color);
        }
        else {
            sb.draw(ImageMaster.SOLD_OUT_IMG, this.purgeCardX - 256.0f, this.purgeCardY - 256.0f, 256.0f, 256.0f, 512.0f, 512.0f, this.purgeCardScale, this.purgeCardScale, 0.0f, 0, 0, 512, 512, false, false);
        }
    }
    
    static {
        logger = LogManager.getLogger(ShopScreen.class.getName());
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Shop Tip");
        MSG = ShopScreen.tutorialStrings.TEXT;
        LABEL = ShopScreen.tutorialStrings.LABEL;
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Shop Screen");
        NAMES = ShopScreen.characterStrings.NAMES;
        TEXT = ShopScreen.characterStrings.TEXT;
        DRAW_START_X = Settings.WIDTH * 0.16f;
        TOP_ROW_Y = 730.0f * Settings.scale;
        BOTTOM_ROW_Y = 307.0f * Settings.scale;
        SPEECH_TEXT_R_X = 164.0f * Settings.scale;
        SPEECH_TEXT_L_X = -166.0f * Settings.scale;
        SPEECH_TEXT_Y = 126.0f * Settings.scale;
        WELCOME_MSG = ShopScreen.NAMES[0];
        ShopScreen.purgeCost = 75;
        ShopScreen.actualPurgeCost = 75;
        GOLD_IMG_WIDTH = ImageMaster.UI_GOLD.getWidth() * Settings.scale;
        GOLD_IMG_OFFSET_X = -50.0f * Settings.scale;
        GOLD_IMG_OFFSET_Y = -215.0f * Settings.scale;
        PRICE_TEXT_OFFSET_X = 16.0f * Settings.scale;
        PRICE_TEXT_OFFSET_Y = -180.0f * Settings.scale;
    }
}
