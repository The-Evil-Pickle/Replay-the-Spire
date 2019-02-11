package com.megacrit.cardcrawl.mod.replay.vfx.paralax;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ParalaxObject {
	public float x;
	public float y;
	public float speed;
	public Texture img;
	public boolean flipped;

	public ParalaxObject(String texturePath) {
		this(0, 400.0f, 100.0f, texturePath, false);
	}
	public ParalaxObject(String texturePath, boolean flipped) {
		this(0, 400.0f, 100.0f, texturePath, flipped);
	}
	public ParalaxObject(float x, float y, String texturePath) {
		this(x, y, 100.0f, texturePath, false);
	}
	public ParalaxObject(float x, float y, float speed, String texturePath, boolean flipped) {
		this.x = x;
		this.y = y;
		this.flipped = flipped;
		this.speed = speed;
		this.img = ImageMaster.loadImage(texturePath);
	}
	
	public void Render(SpriteBatch sb) {
		sb.setColor(Color.WHITE);
		sb.draw(this.img, this.x - this.img.getWidth(), this.y + AbstractDungeon.sceneOffsetY, this.img.getWidth() * Settings.scale, this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipped, this.flipped);
	}
}
