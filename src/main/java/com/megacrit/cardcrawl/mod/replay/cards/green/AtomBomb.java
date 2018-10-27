package com.megacrit.cardcrawl.mod.replay.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;
import replayTheSpire.patches.CardFieldStuff;

public class AtomBomb extends CustomCard
{
    public static final String ID = "Atom Bomb";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int DAMAGE = 60;
    private static final int POISON_AMT = 3;
    private static final int COST = 4;
    private static final int POOL = 1;
    
    public AtomBomb() {
        super("Atom Bomb", AtomBomb.NAME, "cards/replay/atomBomb.png", 4, AtomBomb.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = 60;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(CardFieldStuff.CHAOS_NEGATIVE_MAGIC);
    }
    
    public void use(final AbstractPlayer abstractPlayer, final AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new PoisonPower(abstractPlayer, abstractPlayer, this.magicNumber), this.magicNumber));
        for (final AbstractMonster abstractMonster2 : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractMonster2, abstractPlayer, new PoisonPower(abstractMonster2, abstractPlayer, this.magicNumber), this.magicNumber));
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new AtomBomb();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(15);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Atom Bomb");
        NAME = AtomBomb.cardStrings.NAME;
        DESCRIPTION = AtomBomb.cardStrings.DESCRIPTION;
    }
}
