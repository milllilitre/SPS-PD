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
package com.hmdzl.spspd.change.items.food.fruit;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Locked;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.SpellSprite;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Fruit extends Food {

	{
		stackable = true;
		//name = "ration of food";
		image = ItemSpriteSheet.RATION;

		bones = true;
	}


}
