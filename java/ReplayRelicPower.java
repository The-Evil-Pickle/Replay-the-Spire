package ReplayTheSpireMod.abstracts;

import com.megacrit.cardcrawl.relics.*;
import ReplayTheSpireMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.ui.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import java.io.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;

public abstract class ReplayRelicPower extends AbstractPower
{
	
	public AbstractRelic.RelicTier tier;
	public int defaultAmount;
	public int defaultStackAmount;
	
    public ReplayRelicPower(AbstractRelic.RelicTier tier, int defaultAmount, AbstractCreature owner, int amt) {
        this.tier = tier;
		this.defaultAmount = defaultAmount;
        this.owner = owner;
        this.amount = 3;
    }
	
	public AbstractGameAction getApplyAction() {
		return this.getApplyAction(AbstractDungeon.player);
	}
	public AbstractGameAction getApplyAction(AbstractCreature owner) {
		return this.getApplyAction(owner, this.defaultAmount);
	}
	public AbstractGameAction getApplyAction(AbstractCreature owner, int amt) {
		return null;
	}
}
