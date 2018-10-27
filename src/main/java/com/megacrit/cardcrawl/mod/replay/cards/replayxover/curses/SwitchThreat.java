package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import chronomuncher.patches.*;
import chronomuncher.patches.Enum;

import java.util.*;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.core.*;

import chronomuncher.actions.*;
import chronomuncher.cards.AbstractSelfSwitchCard;

public class SwitchThreat extends AbstractSelfSwitchCard
{
    public List<switchCard> switchListInherit;
    
    public SwitchThreat(String switchID) {
        super("SwitchThreat", "None", null, 0, "None", AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE, SwitchThreat.class);
        this.switchListInherit = Arrays.asList(new switchCard(ImminentThreat.ID, VengefulThreat.ID, 1, 0, 0, 0, 0, ImminentThreat.DAMAGEAMT, 0, AbstractCard.CardType.CURSE, AbstractCard.CardTarget.SELF, false, true, true, false), new switchCard(VengefulThreat.ID, ImminentThreat.ID, 0, 0, 0, 0, 0, 0, 0, AbstractCard.CardType.CURSE, AbstractCard.CardTarget.SELF, false, false, true, false));
        for (switchCard c : this.switchListInherit) {
        	c.portraitImg = "cards/replay/betaCurse.png";
        }
        if (switchID == null) {
            switchID = this.switchListInherit.get(new Random().nextInt(this.switchListInherit.size())).cardID;
        }
        this.switchList = this.switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        }
        else {
            this.switchTo(switchID);
        }
        this.tags.add(Enum.SWITCH_CARD);
    }
    
    public SwitchThreat() {
        this(null);
    }
    
    public void switchTo(String switchID) {
    	super.switchTo(switchID);
    	GraveField.grave.set(this, switchID.equals(VengefulThreat.ID));
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final String currentID = this.currentID;
        switch (currentID) {
            case ImminentThreat.ID: {
            	AbstractDungeon.actionManager.addToBottom(new DamageAction(p, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.THORNS)));
                break;
            }
            case VengefulThreat.ID: {
            	AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new SwitchAction(this));
    }
    
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return this.cardPlayable(m) && this.hasEnoughEnergy();
    }
}
