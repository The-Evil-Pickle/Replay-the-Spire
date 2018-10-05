package com.megacrit.cardcrawl.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.replayxover.BoundBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import madsciencemod.actions.common.GainFuelAction;
import mysticmod.cards.cantrips.AcidSplash;
import mysticmod.cards.cantrips.Prestidigitation;
import mysticmod.cards.cantrips.RayOfFrost;
import mysticmod.cards.cantrips.ReadMagic;
import mysticmod.cards.cantrips.Spark;
import replayTheSpire.ReplayTheSpireMod;

public class m_BookOfShivs extends AbstractRelic
{
    public static final String ID = "m_BookOfShivs";
    
    public m_BookOfShivs() {
        super(ID, "betaRelic.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        for (int i=0; i < 2; i++) {
        	final int randomlyGeneratedNumber = AbstractDungeon.cardRandomRng.random(5);
	        switch (randomlyGeneratedNumber) {
	            case 0: {
	                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new AcidSplash(), 1, true, true));
	                break;
	            }
	            case 1: {
	                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Prestidigitation(), 1, true, true));
	                break;
	            }
	            case 2: {
	                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new RayOfFrost(), 1, true, true));
	                break;
	            }
	            case 3: {
	                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Spark(), 1, true, true));
	                break;
	            }
	            case 4: {
	                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new ReadMagic(), 1, true, true));
	                break;
	            }
	            case 5: {
	                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new BoundBlade(), 1, true, true));
	                break;
	            }
	        }
        }
        this.flash();
    }
	@Override
    public void onEquip() {
		AbstractDungeon.bossRelicPool.add(WristBlade.ID);
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new AThousandCuts());
        tmpPool.add(new Adrenaline());
        tmpPool.add(new Acrobatics());
        tmpPool.add(new AfterImage());
        tmpPool.add(new CalculatedGamble());
        tmpPool.add(new Burst());
        tmpPool.add(new Choke());
        tmpPool.add(new DaggerThrow());
        tmpPool.add(new Concentrate());
        tmpPool.add(new EndlessAgony());
        tmpPool.add(new EscapePlan());
        tmpPool.add(new Expertise());
        tmpPool.add(new Finisher());
        tmpPool.add(new Nightmare());
        tmpPool.add(new Outmaneuver());
        tmpPool.add(new Prepared());
        tmpPool.add(new Setup());
        tmpPool.add(new ToolsOfTheTrade());
        tmpPool.add(new ScrapShanks());
        tmpPool.add(new TheWorks());
        tmpPool.add(new HiddenBlade());
        for (final AbstractCard c : tmpPool) {
			switch (c.rarity) {
				case COMMON: {
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
				case UNCOMMON: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
				case RARE: {
					AbstractDungeon.rareCardPool.addToTop(c);
					AbstractDungeon.srcRareCardPool.addToBottom(c);
					continue;
				}
				default: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
			}
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new m_BookOfShivs();
    }
}
