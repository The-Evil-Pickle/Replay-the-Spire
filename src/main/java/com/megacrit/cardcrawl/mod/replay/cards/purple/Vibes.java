package com.megacrit.cardcrawl.mod.replay.cards.purple;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.DecrepitPower;
import com.megacrit.cardcrawl.mod.replay.powers.LanguidPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class Vibes extends CustomCard
{
    public static final String ID = "Replay:Vibes";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    
    public Vibes() {
        super(ID, Vibes.NAME, "cards/replay/vibes.png", COST, Vibes.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        //this.updateStuff();
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	if (p.stance.ID.equals(WrathStance.STANCE_ID)) {
    		this.addToBot(new ApplyPowerAction(m, p, new DecrepitPower(m, this.magicNumber, false), this.magicNumber));
        } else if (p.stance.ID.equals(CalmStance.STANCE_ID)) {
        	this.addToBot(new ApplyPowerAction(m, p, new LanguidPower(m, this.magicNumber, false), this.magicNumber));
        } else {
        	this.addToBot(new ApplyPowerAction(p, p, new MantraPower(p, this.magicNumber), this.magicNumber));
        }
        
    }

    @Override
    public void triggerWhenDrawn() {
    	this.updateStuff();
    }
    
    @Override
    public void switchedStance() {
    	this.updateStuff();
    }
    private void changeImage(String img) {
    	this.textureImg = img;
        if (img != null) {
            this.loadCardImage(img);
        }
    }
    private void updateStuff() {
    	if (AbstractDungeon.player != null && AbstractDungeon.player.stance != null && AbstractDungeon.player.stance.ID.equals(WrathStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(CalmStance.STANCE_ID)) {
    		this.target = CardTarget.ENEMY;
    		if (AbstractDungeon.player.stance.ID.equals(CalmStance.STANCE_ID)) {
        		this.name = Vibes.EXTENDED_DESCRIPTION[0] + Vibes.NAME;
        		this.changeImage("cards/replay/good_vibes.png");
            } else {
            	this.name = Vibes.EXTENDED_DESCRIPTION[1] + Vibes.NAME;
        		this.changeImage("cards/replay/bad_vibes.png");
            }
    		if (this.upgraded) {
    			this.name += "+";
    	        this.initializeTitle();
    		}
    	} else {
    		this.target = CardTarget.SELF;
    		this.name = Vibes.NAME;
    		this.changeImage("cards/replay/vibes.png");
    		if (this.upgraded) {
    			this.name += "+";
    	        this.initializeTitle();
    		}
    	}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Vibes();
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
        NAME = Vibes.cardStrings.NAME;
        DESCRIPTION = Vibes.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = Vibes.cardStrings.EXTENDED_DESCRIPTION;
    }
}
