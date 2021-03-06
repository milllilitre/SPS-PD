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
package com.hmdzl.spspd.change.items.armor.glyphs;

import android.provider.DocumentsContract;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.change.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.EarthParticle;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.armor.Armor.Glyph;
import com.hmdzl.spspd.change.plants.Earthroot;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;

import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphDark;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphEarth;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphElectricity;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphFire;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphIce;
import com.hmdzl.spspd.change.actors.buffs.armorbuff.GlyphLight;

import com.watabou.noosa.Camera;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Earthglyph extends Glyph {

	private static ItemSprite.Glowing GERY = new ItemSprite.Glowing(	0xCCCCCC);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

	    GlyphDark gdark = defender.buff(GlyphDark.class);
		GlyphIce gice = defender.buff(GlyphIce.class);
	    GlyphLight glight = defender.buff(GlyphLight.class);
	    GlyphFire gfire = defender.buff(GlyphFire.class);
		GlyphEarth gearth = defender.buff(GlyphEarth.class);
		GlyphElectricity gelect = defender.buff(GlyphElectricity.class);
	
		if (defender.isAlive() && gearth == null)
		{
			Buff.detach(defender,GlyphIce.class);
			Buff.detach(defender,GlyphLight.class);
			Buff.detach(defender,GlyphFire.class);
			Buff.detach(defender,GlyphDark.class);
			Buff.detach(defender,GlyphElectricity.class);
			Buff.affect(defender,GlyphEarth.class);
		}	
	
		int level = Math.max(0, armor.level);
		int levelRoots = Math.min(4, armor.level);
		
		if (Random.Int(4) == 0) {

			Buff.affect(defender, Earthroot.Armor.class).level(5 * (level + 1));
			CellEmitter.bottom(defender.pos).start(EarthParticle.FACTORY,
					0.05f, 8);
			Camera.main.shake(1, 0.4f);

		}

		if (Random.Int(level + 6) >= 5) {
			Buff.prolong(defender, DefenceUp.class,5f).level(20);
		}

		return damage;
		
	}

	@Override
	public Glowing glowing() {
		return GERY;
	}

}
