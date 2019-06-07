package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.bard.BardMod;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.bard.helpers.MelodyManager;
import com.evacipated.cardcrawl.mod.bard.ui.MelodiesPanel;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.DualPolarity;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.replayxover.bard.FocusUpMelody;
import replayTheSpire.replayxover.bard.FrostMelody;
import replayTheSpire.replayxover.bard.LightningMelody;
import replayTheSpire.replayxover.bard.OrbNote;
import replayTheSpire.replayxover.bard.PlasmaMelody;


public class M_MusicBoxCore extends M_MistRelic
{
    public static final String ID = "m_MusicBox";
    
    public M_MusicBoxCore() {
        super(ID, "crackedOrb.png", LandingSound.CLINK, Bard.Enums.COLOR, CardColor.BLUE);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    public void atPreBattle() {
    	super.atPreBattle();
    	AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(OrbNote.get()));
    }
    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    	AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(3));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_MusicBoxCore();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Barrage());
        tmpPool.add(new BasicCrystalCard());
        tmpPool.add(new BeamCell());
        tmpPool.add(new BiasedCognition());
        tmpPool.add(new Capacitor());
        tmpPool.add(new Chaos());
        tmpPool.add(new Darkness());
        tmpPool.add(new Defragment());
        tmpPool.add(new Fission());
        tmpPool.add(new Fusion());
        tmpPool.add(new GoForTheEyes());
        tmpPool.add(new Loop());
        tmpPool.add(new MultiCast());
        tmpPool.add(new Rainbow());
        tmpPool.add(new Zap());
        tmpPool.add(new ReplayRNGCard());
        if (ReplayTheSpireMod.foundmod_conspire) {
        	tmpPool.add(new DualPolarity());
        }
		if (MelodyManager.getMelodByID(FocusUpMelody.ID) == null) {
        	MelodyManager.addMelody(new FocusUpMelody());
    		MelodyManager.addMelody(new LightningMelody());
    		MelodyManager.addMelody(new FrostMelody());
    		MelodyManager.addMelody(new PlasmaMelody());
    		BardMod.melodiesPanel = new MelodiesPanel();
        }
		return tmpPool;
	}
	
	@Override 
	public void onEquip() {
		super.onEquip();
	}
}
