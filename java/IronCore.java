package com.megacrit.cardcrawl.relics;

import java.util.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.defect.*;

public class IronCore extends AbstractRelic
{
    public static final String ID = "Iron Core";
    
    public IronCore() {
        super("Iron Core", "ironCoreOrb.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void atPreBattle() {
        AbstractDungeon.player.channelOrb(new HellFireOrb());
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new IronCore();
    }
	
	@Override
    public void onEquip() {
		AbstractDungeon.rareRelicPool.add("Magic Flower");
        final long startTime = System.currentTimeMillis();
        final ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();
		tmpPool.add(new Anger());
		tmpPool.add(new Barricade());
		tmpPool.add(new Clash());
		tmpPool.add(new DoubleTap());
		tmpPool.add(new DualWield());
		tmpPool.add(new FireBreathing());
		tmpPool.add(new Flex());
		tmpPool.add(new Headbutt());
		tmpPool.add(new HeavyBlade());
		tmpPool.add(new Inflame());
		tmpPool.add(new IronWave());
		tmpPool.add(new Juggernaut());
		tmpPool.add(new LimitBreak());
		tmpPool.add(new Offering());
		tmpPool.add(new Reaper());
		tmpPool.add(new SeeingRed());
		tmpPool.add(new Warcry());
		tmpPool.add(new Whirlwind());
		//tmpPool.add(new WeaponsOverheat());
		//tmpPool.add(new IC_ScorchingBeam());
		//tmpPool.add(new IC_FireWall());
		AbstractDungeon.commonCardPool.addToTop(new IC_BasicHellfireCard());
		AbstractDungeon.srcCommonCardPool.addToBottom(new IC_BasicHellfireCard());
		AbstractDungeon.uncommonCardPool.addToTop(new WeaponsOverheat());
		AbstractDungeon.srcUncommonCardPool.addToBottom(new WeaponsOverheat());
		AbstractDungeon.uncommonCardPool.addToTop(new IC_ScorchingBeam());
		AbstractDungeon.srcUncommonCardPool.addToBottom(new IC_ScorchingBeam());
		AbstractDungeon.rareCardPool.addToTop(new IC_FireWall());
		AbstractDungeon.srcRareCardPool.addToBottom(new IC_FireWall());
        for (final AbstractCard c : tmpPool) {
			switch (c.rarity) {
				case COMMON: {
					AbstractDungeon.commonCardPool.addToTop(c);
					AbstractDungeon.srcCommonCardPool.addToBottom(c);
					continue;
				}
				case UNCOMMON: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
				case RARE: {
					AbstractDungeon.rareCardPool.addToTop(c);
					AbstractDungeon.srcRareCardPool.addToBottom(c);
					continue;
				}
				default: {
					AbstractDungeon.uncommonCardPool.addToTop(c);
					AbstractDungeon.srcUncommonCardPool.addToBottom(c);
					continue;
				}
			}
        }
    }
}
