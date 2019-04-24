package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.relics.*;

import blackrusemod.powers.*;

public class M_IronSupplements extends M_MistRelic
{
    public static final String ID = "m_IronSupplements";
    
    public M_IronSupplements() {
        super(ID, "redPill.png", LandingSound.FLAT, gluttonmod.patches.AbstractCardEnum.GLUTTON, CardColor.RED);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
	@Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        final AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal(6);
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_IronSupplements();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        tmpPool.add(new Hemokinesis());
        tmpPool.add(new Offering());
        tmpPool.add(new Hemogenesis());
        tmpPool.add(new Brutality());
        tmpPool.add(new BurningPact());
        tmpPool.add(new Corruption());
        tmpPool.add(new DarkEmbrace());
        tmpPool.add(new Evolve());
        tmpPool.add(new Exhume());
        tmpPool.add(new FeelNoPain());
        tmpPool.add(new FiendFire());
        tmpPool.add(new Bloodletting());
        tmpPool.add(new Reaper());
        tmpPool.add(new SeverSoul());
        tmpPool.add(new TrueGrit());
        tmpPool.add(new Sentinel());
        tmpPool.add(new Abandon());
        tmpPool.add(new DefyDeath());
		return tmpPool;
	}
}

