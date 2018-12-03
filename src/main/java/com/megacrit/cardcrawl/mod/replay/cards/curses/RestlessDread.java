package com.megacrit.cardcrawl.mod.replay.cards.curses;

import basemod.abstracts.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class RestlessDread extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Restless Dread";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public RestlessDread() {
        super("hubris:Sloth", RestlessDread.NAME, "cards/replay/betaCurse.png", COST, RestlessDread.DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF);
        AutoplayField.autoplay.set(this, true);
        GraveField.grave.set(this, true);
        this.exhaust = true;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        for (int i=0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeCopy(), 1));
        }
    }
    
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return true;
    }
    
    public boolean canUpgrade() {
        return false;
    }
    
    public void upgrade() {
    }
    
    public AbstractCard makeCopy() {
        return new RestlessDread();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = RestlessDread.cardStrings.NAME;
        DESCRIPTION = RestlessDread.cardStrings.DESCRIPTION;
    }
}
