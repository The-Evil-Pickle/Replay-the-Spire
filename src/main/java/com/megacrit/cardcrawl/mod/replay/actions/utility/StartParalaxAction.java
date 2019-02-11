package com.megacrit.cardcrawl.mod.replay.actions.utility;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.mod.replay.vfx.paralax.ParalaxController;

public class StartParalaxAction extends AbstractGameAction
{
	
	ParalaxController controller;
    public StartParalaxAction(ParalaxController controller) {
        this.duration = 0.5f;
        this.controller = controller;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.5f) {
            this.controller.Start();
            this.isDone=true;
        }
        this.tickDuration();
    }
}

