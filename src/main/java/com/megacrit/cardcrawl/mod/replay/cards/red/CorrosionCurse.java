package com.megacrit.cardcrawl.mod.replay.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.DecrepitPower;
import com.megacrit.cardcrawl.mod.replay.powers.LanguidPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.core.*;

public class CorrosionCurse extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Corrosion Curse";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    private static final int EXHAUSTIVE_AMT = 2;
    private static final String BETA_ART = "cards/replay/corrosionCurseBeta.png";
    
    public CorrosionCurse() {
        super(ID, CorrosionCurse.NAME, "cards/replay/corrosionCurse.png", COST, CorrosionCurse.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        ExhaustiveVariable.setBaseValue(this, EXHAUSTIVE_AMT);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        
    }
    
    @Override
    public void loadCardImage(final String img) {
    	super.loadCardImage(img);
        Texture cardTexture;
        if (CustomCard.imgMap.containsKey(BETA_ART)) {
            cardTexture = CustomCard.imgMap.get(BETA_ART);
        }
        else {
            cardTexture = ImageMaster.loadImage(BETA_ART);
            CustomCard.imgMap.put(BETA_ART, cardTexture);
        }
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        final int tw = cardTexture.getWidth();
        final int th = cardTexture.getHeight();
        final TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivateInherited(this, CustomCard.class, "jokePortrait", cardImg);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	if (this.magicNumber > 1) {
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LanguidPower(m, this.magicNumber-1, false), this.magicNumber-1));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DecrepitPower(m, this.magicNumber, false), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new CorrosionCurse();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = CorrosionCurse.cardStrings.NAME;
        DESCRIPTION = CorrosionCurse.cardStrings.DESCRIPTION;
    }
}
