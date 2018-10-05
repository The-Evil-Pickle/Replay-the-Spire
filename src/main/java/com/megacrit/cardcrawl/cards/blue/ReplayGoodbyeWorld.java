package com.megacrit.cardcrawl.cards.blue;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;

public class ReplayGoodbyeWorld extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:GoodbyeWorld";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    public ArrayList<TooltipInfo> tips;
    
    public ReplayGoodbyeWorld() {
        super(ID, ReplayGoodbyeWorld.NAME, "cards/replay/replayBetaPower.png", ReplayGoodbyeWorld.COST, ReplayGoodbyeWorld.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
		this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.tips = new ArrayList<TooltipInfo>();
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GoodbyeWorldPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
    	this.tips.clear();
        this.tips.add(new TooltipInfo("Common", "Common cards are cards with a dark gray image border. NL Does not include basic cards (cards in your starting deck), or event-only cards."));
        return this.tips;
    }
    @Override
    public AbstractCard makeCopy() {
        return new ReplayGoodbyeWorld();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.isInnate = true;
            this.rawDescription = ReplayGoodbyeWorld.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ReplayGoodbyeWorld.cardStrings.NAME;
        DESCRIPTION = ReplayGoodbyeWorld.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplayGoodbyeWorld.cardStrings.UPGRADE_DESCRIPTION;
    }
}