package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
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
