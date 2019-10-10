package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import basemod.interfaces.CloneablePowerInterface;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;

public class ReplayBlindPower extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "ReplayBlindPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    static TextureAtlas cardImgs;
    static TextureAtlas oldCardImgs;
    
    private static class BlindSavedCard {
    	public AbstractCard card;
    	public String name;
    	public String desc;
    	public String img;
    	
    	public BlindSavedCard(AbstractCard c) {
    		this.card = c;
    		this.name = c.name;
    		this.desc = c.rawDescription;
    		this.SetTexture("cards/replay/replayBetaAttack.png", true);
    		this.card.name = "???";
    		this.card.rawDescription = ReplayBlindPower.DESCRIPTIONS[2];
    		this.card.initializeDescription();
    	}
    	
    	public void UnblindCard() {
    		this.card.name = this.name;
    		this.card.rawDescription = this.desc;
    		this.card.initializeDescription();
    		this.SetTexture(this.img, false);
    	}
    	
    	public void SetTexture(String imgpth, boolean first) {
    		if (this.card instanceof CustomCard) {
    			if (first) this.img = ((CustomCard) this.card).textureImg;
    			((CustomCard) this.card).loadCardImage(imgpth);
    		} else if (first) {
    			this.img = this.card.assetUrl;
    			Texture cardTexture;
    	        if (CustomCard.imgMap.containsKey(imgpth)) {
    	            cardTexture = CustomCard.imgMap.get(imgpth);
    	        }
    	        else {
    	            cardTexture = ImageMaster.loadImage(imgpth);
    	            CustomCard.imgMap.put(imgpth, cardTexture);
    	        }
    	        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    	        final int tw = cardTexture.getWidth();
    	        final int th = cardTexture.getHeight();
    	        final TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
    	        this.card.portrait = cardImg;
    	        this.card.jokePortrait = cardImg;
    	        //ReflectionHacks.setPrivate(c, AbstractCard.class, "portrait", cardImg);
    	        //ReflectionHacks.setPrivate(this.card, AbstractCard.class, "portraitImg", cardTexture);
    		} else {
    			this.card.portrait = ReplayBlindPower.cardImgs.findRegion(imgpth);
    	        //this.card.jokePortrait = ReplayBlindPower.oldCardImgs.findRegion(imgpth);
    		}
    	}
    }
    
    private ArrayList<BlindSavedCard> savedcards;
    private boolean justApplied;
    
    public ReplayBlindPower(final AbstractCreature owner, final int amount) {
    	this(owner, amount, true);
    }
    
    public ReplayBlindPower(final AbstractCreature owner, final int amount, final boolean isSourceMonster) {
    	if (cardImgs == null) {
    		cardImgs = new TextureAtlas(Gdx.files.internal("cards/cards.atlas"));
    		//oldCardImgs = new TextureAtlas(Gdx.files.internal("oldcards/cards.atlas"));
    	}
        this.name = ReplayBlindPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.savedcards = new ArrayList<BlindSavedCard>();
        this.justApplied = isSourceMonster;
        this.updateDescription();
        this.loadRegion("brewmaster");
    }
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
    
    
    @Override
    public void updateDescription() {
            this.description = ReplayBlindPower.DESCRIPTIONS[0] + this.amount + ReplayBlindPower.DESCRIPTIONS[1] + ((this.amount > 1) ? ReplayTheSpireMod.MULTI_SUFFIX: ReplayTheSpireMod.SINGLE_SUFFIX) + ReplayTheSpireMod.LOC_FULLSTOP;
    }
    
    private void checkAndObscure(AbstractCard c) {
    	if (c.type == CardType.ATTACK) {
    		BlindSavedCard bsc = new BlindSavedCard(c);
    		this.savedcards.add(bsc);
    	}
    }

    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
        else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }
    
    @Override
	public void onInitialApplication() {
		if (this.owner.isPlayer) {
			for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
				checkAndObscure(c);
			}
			for (AbstractCard c : AbstractDungeon.player.hand.group) {
				checkAndObscure(c);
			}
			for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
				checkAndObscure(c);
			}
		}
	}
    @Override
    public void onRemove() {
    	for (BlindSavedCard bsc : this.savedcards) {
    		bsc.UnblindCard();
    	}
    	this.savedcards.clear();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = ReplayBlindPower.powerStrings.NAME;
        DESCRIPTIONS = ReplayBlindPower.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new ReplayBlindPower(owner, amount);
	}
}
