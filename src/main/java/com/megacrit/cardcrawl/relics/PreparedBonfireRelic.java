package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

public class PreparedBonfireRelic extends AbstractRelic
{
    public static final String ID = "PreparedBonfireRelic";
    private static int STR_AMT = 2;
    private static int DEX_AMT = 2;
    private static int FOC_AMT = 1;
    private static int ART_AMT = 1;
    private static int SHL_AMT = 1;
    private boolean firstTurn;
    
    public PreparedBonfireRelic() {
        super("PreparedBonfireRelic", "kindling.png", RelicTier.SPECIAL, LandingSound.FLAT);
        this.firstTurn = true;
        this.pulse = true;
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + STR_AMT + this.DESCRIPTIONS[1] + DEX_AMT + this.DESCRIPTIONS[2];
    }
    
    @Override
    public void atTurnStart() {
        if (this.firstTurn) {
            this.flash();
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR_AMT), STR_AMT));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, DEX_AMT), DEX_AMT));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.firstTurn = false;
            this.pulse = false;
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new PreparedBonfireRelic();
    }
}
