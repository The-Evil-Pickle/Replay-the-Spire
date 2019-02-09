package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.PondfishDrowning;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_BronzeScales;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_GiryaPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_HourglassPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_IncenseBurner;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_KunaiPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_LetterOpenerPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_OrichalcumPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_SelfFormingClay;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_SmoothStonePower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_ThreadPower;
import com.megacrit.cardcrawl.mod.replay.powers.relicPowers.RP_VajraPower;

import slimebound.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;

import basemod.abstracts.CustomCard;
import replayTheSpire.replayxover.slimeboundbs;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Forest_Treasure extends CustomCard
{
    public static final String ID = "Replay:SS_forest_2";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    
    public SS_Forest_Treasure() {
        super(ID, SS_Forest_Treasure.NAME, "cards/replay/ss_forest_treasure.png", COST, SS_Forest_Treasure.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
        this.tags.add(slimeboundbs.STUDY_FOREST);
        this.tags.add(SlimeboundMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 2;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	for (int i=0; i<this.magicNumber; i++) {
    		switch (AbstractDungeon.miscRng.random(0, 10)) {
    		case 0:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_HourglassPower(p), 3));
    			break;
    		case 1:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_ThreadPower(p), 1));
    			break;
    		case 2:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_OrichalcumPower(p), 6));
    			break;
    		case 3:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_KunaiPower(p), 1));
    			break;
    		case 4:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_GiryaPower(p), 3));
    			break;
    		case 5:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_LetterOpenerPower(p), 5));
    			break;
    		case 6:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_IncenseBurner(p), -1));
    			break;
    		case 7:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_SelfFormingClay(p), 3));
    			break;
    		case 8:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_SmoothStonePower(p), 1));
    			break;
    		case 9:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_VajraPower(p), 1));
    			break;
    		case 10:
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RP_BronzeScales(p), 3));
    			break;
    		}
    	}
    }
    
    public AbstractCard makeCopy() {
        return new SS_Forest_Treasure();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Forest_Treasure.cardStrings.NAME;
        DESCRIPTION = SS_Forest_Treasure.cardStrings.DESCRIPTION;
    }
}
