package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class FaultyEquipment extends CustomCard
{
    public static final String ID = "Faulty Equipment";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
	private boolean blockExhaust;
    
    public FaultyEquipment() {
        super("Faulty Equipment", FaultyEquipment.NAME, "cards/replay/betaCurse.png", -2, FaultyEquipment.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
		this.blockExhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new FaultyEquipment();
    }
    
	@Override
	public boolean canUpgrade() {
        return true;
    }
	
    @Override
    public void triggerOnExhaust() {
		if (this.blockExhaust){
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.makeCopy()));
		}
    }
	
    @Override
    public void upgrade() {
		boolean isSmithUpgrade = (AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getMonsters().areMonstersBasicallyDead());
		this.blockExhaust = false;
		if (isSmithUpgrade) {
			AbstractDungeon.effectList.add(new PurgeCardEffect(this));
            AbstractDungeon.player.masterDeck.removeCard(this);
		} else {
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.drawPile));
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
		}
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Faulty Equipment");
        NAME = FaultyEquipment.cardStrings.NAME;
        DESCRIPTION = FaultyEquipment.cardStrings.DESCRIPTION;
    }
}
