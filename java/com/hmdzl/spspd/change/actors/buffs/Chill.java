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

package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Thief;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.food.meatfood.FrozenCarpaccio;
import com.hmdzl.spspd.change.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.items.potions.PotionOfStrength;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

import java.text.DecimalFormat;

public class Chill extends FlavourBuff {

	{
		type = buffType.NEGATIVE;
	}

	@Override
	public boolean attachTo(Char target) {
		//can't chill what's frozen!
		if (target.buff(Frost.class) != null) return false;

		if (super.attachTo(target)){
			Burning.detach( target, Burning.class );

			//chance of potion breaking is the same as speed factor.
			if (Random.Float(1f) > speedFactor() && target instanceof Hero) {

				Hero hero = (Hero)target;
				Item item = hero.belongings.randomUnequipped();
				if (item instanceof Potion
						&& !(item instanceof PotionOfStrength || item instanceof PotionOfMight)) {

					item = item.detach( hero.belongings.backpack );
					GLog.w(Messages.get(this, "freezes", item.toString()));
					((Potion) item).shatter(hero.pos);

				} else if (item instanceof MysteryMeat) {

					item = item.detach( hero.belongings.backpack );
					FrozenCarpaccio carpaccio = new FrozenCarpaccio();
					if (!carpaccio.collect( hero.belongings.backpack )) {
						Dungeon.level.drop( carpaccio, target.pos ).sprite.drop();
					}
					GLog.w(Messages.get(this, "freezes", item.toString()));

				}
			} else if (target instanceof Thief) {

				Item item = ((Thief) target).item;

				if (item instanceof Potion && !(item instanceof PotionOfStrength || item instanceof PotionOfMight)) {
					((Potion) ((Thief) target).item).shatter(target.pos);
					((Thief) target).item = null;
				}

			}
			return true;
		} else {
			return false;
		}
	}

	//reduces speed by 10% for every turn remaining, capping at 50%
	public float speedFactor(){
		return Math.max(0.5f, 1 - cooldown()*0.1f);
	}

	@Override
	public int icon() {
		return BuffIndicator.FROST;
	}

	//@Override
	public void fx(boolean on) {
		if (on) target.sprite.add(CharSprite.State.CHILLED);
		else target.sprite.remove(CharSprite.State.CHILLED);
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns(), new DecimalFormat("#.##").format((1f-speedFactor())*100f));
	}

}
