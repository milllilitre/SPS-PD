/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.change.actors.mobs;

import java.util.HashSet;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Badges.Badge;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.DolyaStale;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.change.levels.CavesBossLevel;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.items.journalpages.Sokoban3;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.DM300Sprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class DM300 extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	{
		spriteClass = DM300Sprite.class;

		HP = HT = 800;
		EXP = 30;
		evadeSkill = 24;

		loot = new CapeOfThorns().identify();
		lootChance = 0.2f;
		
		lootOther = Generator.Category.NORNSTONE;
		lootChanceOther = 1f;		
		
		properties.add(Property.MECH);
		properties.add(Property.BOSS);
	}

	private int bossAlive = 0;
	private int towerAlive = 0;
	
	@Override
	public int damageRoll() {
		
		return Random.NormalIntRange(15, 19)*towerAlive;
	}

	@Override
	public int hitSkill(Char target) {
		return 35;
	}

	@Override
	public int drRoll() {
		return 10+(4*towerAlive);
	}

	public void spawnTower() {
		Tower a = new Tower();  
	    Tower b = new Tower(); 	

		do {
			a.pos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			b.pos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
		} while (!Dungeon.level.passable[a.pos] || !Dungeon.level.passable[b.pos] || a.pos == b.pos);
		
		GameScene.add(a);
		GameScene.add(b);	
	}	
	
	@Override
	public boolean act() {
		
		if (towerAlive < 1){
			spawnTower();
			towerAlive++;
		}

		GameScene.add(Blob.seed(pos, 30, ToxicGas.class));

		return super.act();
	}
	
	@Override
	public void move(int step) {
		super.move(step);

		if (Dungeon.level.map[step] == Terrain.INACTIVE_TRAP && HP < HT) {

			HP += Random.Int(1, HT - HP);
			sprite.emitter().burst(ElmoParticle.FACTORY, 5);

			if (Dungeon.visible[step] && Dungeon.hero.isAlive()) {
				GLog.n(Messages.get(this,"heal"));
			}
		}

		int[] cells = { step - 1, step + 1, step - Level.getWidth(),
				step + Level.getWidth(), step - 1 - Level.getWidth(),
				step - 1 + Level.getWidth(), step + 1 - Level.getWidth(),
				step + 1 + Level.getWidth() };
		int cell = cells[Random.Int(cells.length)];

		if (Dungeon.visible[cell]) {
			CellEmitter.get(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
			Camera.main.shake(3, 0.7f);
			Sample.INSTANCE.play(Assets.SND_ROCKS);

			if (Level.water[cell]) {
				GameScene.ripple(cell);
			} else if (Dungeon.level.map[cell] == Terrain.EMPTY) {
				Level.set(cell, Terrain.EMPTY_DECO);
				GameScene.updateMap(cell);
			}
		}

		Char ch = Actor.findChar(cell);
		if (ch != null && ch != this ) {
			Buff.prolong(ch, Paralysis.class, 2);
		}
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

           for (Mob mob : Dungeon.level.mobs) {
			
			  if (mob instanceof Tower){
				   bossAlive++;
				   }
			
			}
			
			 if(bossAlive==0){
				 
					GameScene.bossSlain();
					((CavesBossLevel) Dungeon.level).unseal();
					Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
					Badges.validateBossSlain();
			 }

			 Badges.Badge badgeToCheck = null;
				switch (Dungeon.hero.heroClass) {
				case WARRIOR:
					badgeToCheck = Badge.MASTERY_WARRIOR;
					break;
				case MAGE:
					badgeToCheck = Badge.MASTERY_MAGE;
					break;
				case ROGUE:
					badgeToCheck = Badge.MASTERY_ROGUE;
					break;
				case HUNTRESS:
					badgeToCheck = Badge.MASTERY_HUNTRESS;
					break;
				case PERFORMER:
					badgeToCheck = Badge.MASTERY_PERFORMER;
					break;					
				}
				
				
				Dungeon.level.drop(new Sokoban3(), pos).sprite.drop();
		       

		yell(Messages.get(this,"die"));
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}
	
	@Override
	public void call() {
		next();
	}
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(EnchantmentDark.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(Terror.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	
}
