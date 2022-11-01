package com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;
import gremlin.actions.MakeEchoAction;

public class ResoundingBlow extends CustomCard
{
    public static final String ID = "Replay:Resounding Blow";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 2;
    private static final int ATTACK_UP = 1;
    private static final int BLOCK_AMT = 1;
    private static final int BLOCK_UP = 1;
    
    public ResoundingBlow() {
        super(ID, ResoundingBlow.NAME, "cards/replay/replayBetaAttack.png", COST, ResoundingBlow.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = ResoundingBlow.ATTACK_DMG;
        this.baseBlock = BLOCK_AMT;
        this.block = this.baseBlock;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new ResoundingBlow();
    }
    
    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToTop(new MakeEchoAction(this));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(ATTACK_UP);
            this.upgradeBlock(BLOCK_UP);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ResoundingBlow.cardStrings.NAME;
        DESCRIPTION = ResoundingBlow.cardStrings.DESCRIPTION.replace("Echo", "gremlin:Echo");
    }
}
