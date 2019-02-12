package com.megacrit.cardcrawl.mod.replay.actions.utility;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import java.util.*;

public class MoveCreaturesAction extends AbstractGameAction
{
	
	private ArrayList<AbstractCreature> monsters;
	private float x;
	private float y;
	private float cx;
	private float cy;
	private float limiter;
	
    public MoveCreaturesAction(ArrayList<AbstractCreature> monsters, final float x, final float y, final float setDur) {
        this.setValues(null, null, 0);
        if (Settings.FAST_MODE && setDur > 0.5f) {
            this.duration = 0.5f;
        }
        else {
            this.duration = setDur;
        }
        this.actionType = ActionType.WAIT;
		this.monsters = monsters;
		this.limiter = this.duration;
		this.x = x * Settings.scale;
		this.y = y * Settings.scale;
		this.cx = 0;
		this.cy = 0;
    }
    
    @Override
    public void update() {
		float tx;
		float ty;
		if (this.duration - Gdx.graphics.getDeltaTime() <= 0.0f) {
			tx = this.x - this.cx;
			ty = this.y - this.cy;
			this.isDone = true;
		} else {
			tx = (this.x * (Gdx.graphics.getDeltaTime() / this.limiter));
			ty = (this.y * (Gdx.graphics.getDeltaTime() / this.limiter));
		}
		for (AbstractCreature target : this.monsters) {
			//target.hb_x += tx;
			//target.hb_y += ty;
			target.drawX += tx;
			target.drawY += ty;
			if (target instanceof AbstractPlayer) {
				AbstractPlayer p = (AbstractPlayer)target;
				for (int i = 0; i < p.orbs.size(); ++i) {
		            p.orbs.get(i).setSlot(i, p.maxOrbs);
		        }
			}
			//target.hb.move(target.hb.cX + tx, target.hb.cY + ty);
			//target.healthHb.move(target.healthHb.cX + tx, target.healthHb.cY + ty);
			//target.intentHb.move(target.intentHb.cX + tx, target.intentHb.cY + ty);
		}
		this.cx += tx;
		this.cy += ty;
        this.tickDuration();
    }
}
