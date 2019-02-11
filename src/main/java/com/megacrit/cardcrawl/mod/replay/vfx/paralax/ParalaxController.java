package com.megacrit.cardcrawl.mod.replay.vfx.paralax;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParalaxController {
	private ArrayList<ArrayList<ParalaxObject>> objects;
	public float width;
	private ArrayList<Float> levelSpeeds;
	public boolean running;
	
	public ParalaxController(ArrayList<Float> levelSpeeds, float width) {
		objects = new ArrayList<ArrayList<ParalaxObject>>();
		this.levelSpeeds = levelSpeeds;
		for (int i=0; i < levelSpeeds.size(); i++) {
			objects.add(new ArrayList<ParalaxObject>());
		}
		this.width = width;
		this.running = false;
	}

	public void Start() {
		this.running = true;
	}
	public void AddObject(ParalaxObject obj, int level) {
		obj.speed = this.levelSpeeds.get(level);
		this.objects.get(level).add(obj);
	}
	public void DistributeObjects() {
		for (ArrayList<ParalaxObject> level : this.objects) {
			float spacing = this.width / (level.size());
			float randoff = ((float)Math.random() % (spacing));
			for (int i=0; i < level.size(); i++) {
				level.get(i).x = spacing * i + ((float)Math.random() % (spacing / 5.0f)) + randoff;
			}
		}
	}
	public void Render(SpriteBatch sb) {
		for (ArrayList<ParalaxObject> level : this.objects) {
			for (ParalaxObject obj : level) {
				if (this.running) {
					obj.x = (obj.x + obj.speed * Gdx.graphics.getDeltaTime()) % this.width;
				}
				obj.Render(sb);
			}
		}
	}
}
