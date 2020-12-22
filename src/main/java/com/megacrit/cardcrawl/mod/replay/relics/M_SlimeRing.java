package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import slimebound.cards.*;
import slimebound.orbs.SpawnedSlime;

public class M_SlimeRing extends M_MistRelic
{
    public static final String ID = "m_SlimeRing";
    
    public M_SlimeRing() {
        super(ID, "snakeRing.png", LandingSound.MAGICAL, slimebound.patches.AbstractCardEnum.SLIMEBOUND, CardColor.GREEN);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.THE_SILENT) {
    			desc += this.DESCRIPTIONS[1];
    		}
    		if (AbstractDungeon.player.chosenClass != slimebound.patches.SlimeboundEnum.SLIMEBOUND) {
    			desc += this.DESCRIPTIONS[2];
    		}
    	} else {
    		desc += this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2];
    	}
        return desc;
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2, false));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_SlimeRing();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        M_MistRelic.cardlist_Shivs(tmpPool);

        tmpPool.add(new Backstab());
        tmpPool.add(new EndlessAgony());
        tmpPool.add(new AThousandCuts());
        tmpPool.add(new AfterImage());

        tmpPool.add(new Prepare());
        tmpPool.add(new SlimeSpikes());
        tmpPool.add(new DisruptingSlam());
        tmpPool.add(new MegaLick());
        tmpPool.add(new Recycling());
        tmpPool.add(new WasteNot());
        tmpPool.add(new Nibble());
        tmpPool.add(new Recollect());

        /*M_MistRelic.cardlist_Poison(tmpPool);
        tmpPool.add(new VenomTackle());
        tmpPool.add(new PoisonLick());
        tmpPool.add(new AcidGelatin());*/
        
		return tmpPool;
	}
}
