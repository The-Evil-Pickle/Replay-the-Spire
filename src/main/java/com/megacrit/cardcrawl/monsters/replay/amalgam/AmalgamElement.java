package com.megacrit.cardcrawl.monsters.replay.amalgam;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;

public class AmalgamElement {
	
	public String elementID;
	public AbstractPower power;
	public String name;
	public String desc;
	public Color color;
	
	public AmalgamElement(String elementID, String name, String desc, Color color) {
		this.elementID = elementID;
		this.name = name;
		this.desc = desc;
		this.color = color;
	}

	public void onApplied(AbstractCreature c) {
		
	}
	public void onSpecialMove(AbstractCreature c) {
		
	}
	public void onStatusAttack(AbstractCreature c) {
		
	}
	public int onMainAttack(AbstractCreature c, int dmg) {
		return dmg;
	}
	public int onMainAttackSecondary(AbstractCreature c, int dmg) {
		return dmg;
	}
}
