package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;

import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.panelUI.ReplayIntSliderSetting;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;

import java.util.*;

public class Sigil extends AbstractRelic
{
	
	//for some reason, this isn't replacing textures on non-attack cards. I find this very confusing.
	
    public static final String ID = "Replay:Sigil";
    public static final ReplayIntSliderSetting SETTING_CHANCE = new ReplayIntSliderSetting("sigil_chance", "Chance per Card", 33, 1, 100, "%");
    private ArrayList<AbstractCard> obscuredCards; 
    public Sigil() {
        super(ID, "betaRelic.png", RelicTier.BOSS, LandingSound.MAGICAL);
    }
	
    @Override
    public String getUpdatedDescription() {
    	return this.DESCRIPTIONS[0];
    }
    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
    }
    
    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
    private void setTexture(AbstractCard c, String imgpth) {
    	if (c.type == AbstractCard.CardType.ATTACK) {
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
	        ReflectionHacks.setPrivate(c, AbstractCard.class, "portrait", cardImg);
	        ReflectionHacks.setPrivate(c, AbstractCard.class, "portraitImg", cardTexture);
		}
    }
    private void obscureCard(AbstractCard c) {
    	c.name = "???";
		c.rawDescription = "???????";
		c.initializeDescription();
		//no matter what texture i use, or what i check for, only attack cards can have their textures changed. ?????
		if (c.type == AbstractCard.CardType.ATTACK) {
			setTexture(c, "cards/replay/replayBetaAttack.png");
		}
		if (c.type == AbstractCard.CardType.POWER) {
			setTexture(c, "cards/replay/replayBetaPower.png");
		}
		if (c.type == AbstractCard.CardType.SKILL){
			setTexture(c, "cards/replay/replayBetaSkill.png");
		}
		if (c.type == AbstractCard.CardType.CURSE){
			setTexture(c, "cards/replay/betaCurse.png");
		}
    }
    @Override
    public void atBattleStartPreDraw() {
    	this.obscuredCards = new ArrayList<AbstractCard>();
    	for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
    		if (c != null && SETTING_CHANCE.testChance(AbstractDungeon.miscRng)) {
    			this.obscuredCards.add(c);
    			obscureCard(c);
    		}
    	}
    }

    @Override
    public void onCardDraw(final AbstractCard drawnCard) {
    	if (this.obscuredCards != null && drawnCard != null && this.obscuredCards.contains(drawnCard)) {
    		obscureCard(drawnCard);
    	}
    }
    @Override
    public AbstractRelic makeCopy() {
        return new Sigil();
    }
}
