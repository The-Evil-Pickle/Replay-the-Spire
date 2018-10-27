package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.characters.*;

import java.util.*;

public class CrystalOrbUpdateAction extends AbstractGameAction
{
    public CrystalOrbUpdateAction() {
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.WAIT;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            for (final AbstractOrb o : AbstractDungeon.player.orbs) {
				if (o != null) {// && o.ID != null && !o.ID.equals("Empty") && !o.ID.equals("Crystal")
					o.updateDescription();
				}
			}
            this.isDone = true;
        }
    }
}
