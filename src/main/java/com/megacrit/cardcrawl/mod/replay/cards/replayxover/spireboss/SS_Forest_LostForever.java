package com.megacrit.cardcrawl.mod.replay.cards.replayxover.spireboss;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.powers.AgingPower;
import com.megacrit.cardcrawl.mod.replay.powers.PondfishDrowning;

import slimebound.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;

import basemod.abstracts.CustomCard;
import replayTheSpire.patches.CardFieldStuff;
import replayTheSpire.replayxover.slimeboundbs;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;

public class SS_Forest_LostForever extends CustomCard
{
    public static final String ID = "Replay:SS_forest_5";
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    
    public SS_Forest_LostForever() {
        super(ID, SS_Forest_LostForever.NAME, "cards/replay/ss_forest_skill.png", COST, SS_Forest_LostForever.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);
        this.tags.add(slimeboundbs.STUDY_FOREST);
        this.tags.add(SlimeboundMod.STUDY);
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
        this.magicNumber = this.baseMagicNumber = 15;
        this.exhaust = true;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new AgingPower(m, this.magicNumber), this.magicNumber));
    }
    
    public AbstractCard makeCopy() {
        return new SS_Forest_LostForever();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-3);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SS_Forest_LostForever.cardStrings.NAME;
        DESCRIPTION = SS_Forest_LostForever.cardStrings.DESCRIPTION;
    }
}
