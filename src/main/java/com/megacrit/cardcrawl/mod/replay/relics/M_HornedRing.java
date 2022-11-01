package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.green.*;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.replayxover.HexaringAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.*;

import gremlin.GremlinMod;
import gremlin.actions.GremlinSwapAction;
import gremlin.cards.*;

public class M_HornedRing extends M_MistRelic
{
    public static final String ID = "m_HornedRing";
    public M_HornedRing() {
        super(ID, "snakeRing.png", LandingSound.MAGICAL, gremlin.patches.AbstractCardEnum.GREMLIN, CardColor.GREEN);
    }
    
    @Override
    public String getUpdatedDescription() {
    	String desc = this.DESCRIPTIONS[0];
    	if (AbstractDungeon.player != null) {
    		if (AbstractDungeon.player.chosenClass != PlayerClass.THE_SILENT) {
    			desc += this.DESCRIPTIONS[1];
    		}
    		if (AbstractDungeon.player.chosenClass != gremlin.patches.GremlinEnum.GREMLIN) {
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
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(true));
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new M_HornedRing();
    }

	@Override
	ArrayList<AbstractCard> getNewCards() {
		final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
        
        M_MistRelic.cardlist_Shivs(tmpPool);
        M_MistRelic.cardlist_Weak(tmpPool);
        tmpPool.add(new Footwork());
        tmpPool.add(new AThousandCuts());
        tmpPool.add(new AfterImage());
        tmpPool.add(new Adrenaline());
        
        
        tmpPool.add(new PourSalt());
        tmpPool.add(new ToeStub());
        tmpPool.add(new TwistTheKnife());
        tmpPool.add(new BurlyBlow());
        tmpPool.add(new Mockery());
        tmpPool.add(new Polish());
        tmpPool.add(new GremlinArms());
        tmpPool.add(new ProperTools());
        tmpPool.add(new ShankStone());
        tmpPool.add(new BrokenShin());
        tmpPool.add(new ShadowShiv());
        tmpPool.add(new SecondVolley());
        tmpPool.add(new Exacerbate());
        tmpPool.add(new TargetWeakness());

		return tmpPool;
	}

}

