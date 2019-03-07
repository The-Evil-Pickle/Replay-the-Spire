package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;

import java.util.ArrayList;

import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.panelUI.ReplayIntSliderSetting;
import replayTheSpire.panelUI.ReplayRelicSetting;

public class RubberDucky extends ReplayAbstractRelic
{
    public static final String ID = "Replay:Rubber Ducky";

    public static final ReplayIntSliderSetting SETTING_BLOCK = new ReplayIntSliderSetting("Ducky_Block", "Shielding", 3, 1, 5);
    
    public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
    	ArrayList<ReplayRelicSetting> settings = new ArrayList<ReplayRelicSetting>();
    	settings.add(SETTING_BLOCK);
		return settings;
	}
    
    public RubberDucky() {
        super(ID, "replayDucky.png", RelicTier.COMMON, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + SETTING_BLOCK.value + DESCRIPTIONS[1];
    }
    
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && !card.freeToPlayOnce && card.costForTurn > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ReplayGainShieldingAction(AbstractDungeon.player, AbstractDungeon.player, SETTING_BLOCK.value * card.costForTurn));
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RubberDucky();
    }
}
