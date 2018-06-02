package com.megacrit.cardcrawl.relics;


import java.util.*;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;

public class ChameleonRing extends AbstractRelic
{
    public static final String ID = "Chameleon Ring";
    
    public ChameleonRing() {
        super("Chameleon Ring", "serpentRing.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
	
    @Override
    public void onEquip() {
		for (int i = 0; i < AbstractDungeon.player.potions.size(); i++) {
			AbstractPotion p = AbstractDungeon.player.potions.get(i);
			if (!p.ID.equals("Potion Slot")) {
				AbstractDungeon.player.obtainPotion(i, p.makeCopy());
			}
		}
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ChameleonRing();
    }
}
