package com.megacrit.cardcrawl.mod.replay.cards.blue;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.core.*;

public class Zip extends AbstractCard
{
    public static final String ID = "Replay:Zip";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public Zip() {
        super(ID, NAME, "blue/skill/zap", COST, DESCRIPTION + " NL Exhaust.", CardType.SKILL, CardColor.BLUE, CardRarity.BASIC, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final AbstractOrb orb = new Lightning();
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(orb));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Zip();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            ExhaustiveVariable.setBaseValue(this, 2);
            ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
            this.rawDescription = DESCRIPTION + " NL Exhaustive !stslib:ex!.";
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Zap");
        NAME = "Zip";
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
