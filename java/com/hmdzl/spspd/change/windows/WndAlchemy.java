/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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

package com.hmdzl.spspd.change.windows;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ShatteredPixelDungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.Torch;
import com.hmdzl.spspd.change.items.WaterItem;
import com.hmdzl.spspd.change.items.artifacts.AlchemistsToolkit;
import com.hmdzl.spspd.change.items.bombs.Bomb;
import com.hmdzl.spspd.change.items.bombs.Honeypot;
import com.hmdzl.spspd.change.items.food.completefood.Chickennugget;
import com.hmdzl.spspd.change.items.food.completefood.Crystalnucleus;
import com.hmdzl.spspd.change.items.food.completefood.Ediblegrass;
import com.hmdzl.spspd.change.items.food.completefood.Foamedbeverage;
import com.hmdzl.spspd.change.items.food.completefood.Fruitsalad;
import com.hmdzl.spspd.change.items.food.completefood.Garbage;
import com.hmdzl.spspd.change.items.food.completefood.Gel;
import com.hmdzl.spspd.change.items.food.completefood.Hamburger;
import com.hmdzl.spspd.change.items.food.completefood.Hardpill;
import com.hmdzl.spspd.change.items.food.completefood.Herbmeat;
import com.hmdzl.spspd.change.items.food.completefood.HoneyGel;
import com.hmdzl.spspd.change.items.food.completefood.HoneyWater;
import com.hmdzl.spspd.change.items.food.completefood.Honeymeat;
import com.hmdzl.spspd.change.items.food.completefood.Honeyrice;
import com.hmdzl.spspd.change.items.food.completefood.Icecream;
import com.hmdzl.spspd.change.items.food.completefood.Kebab;
import com.hmdzl.spspd.change.items.food.completefood.Meatroll;
import com.hmdzl.spspd.change.items.food.completefood.Porksoup;
import com.hmdzl.spspd.change.items.food.completefood.Powerpill;
import com.hmdzl.spspd.change.items.food.completefood.Ricefood;
import com.hmdzl.spspd.change.items.food.completefood.Smashpill;
import com.hmdzl.spspd.change.items.food.completefood.Vegetablekebab;
import com.hmdzl.spspd.change.items.food.completefood.Vegetableroll;
import com.hmdzl.spspd.change.items.food.completefood.Vegetablesoup;
import com.hmdzl.spspd.change.items.food.fruit.Blandfruit;
import com.hmdzl.spspd.change.items.food.fruit.Fruit;
import com.hmdzl.spspd.change.items.food.meatfood.MeatFood;
import com.hmdzl.spspd.change.items.food.Nut;
import com.hmdzl.spspd.change.items.food.completefood.PerfectFood;
import com.hmdzl.spspd.change.items.food.completefood.PetFood;
import com.hmdzl.spspd.change.items.food.completefood.Honey;
import com.hmdzl.spspd.change.items.food.staplefood.NormalRation;
import com.hmdzl.spspd.change.items.food.staplefood.StapleFood;
import com.hmdzl.spspd.change.items.food.vegetables.Vegetable;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.plants.Icecap;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.Icons;
import com.hmdzl.spspd.change.ui.ItemSlot;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class WndAlchemy extends Window {

	private WndBlacksmith.ItemButton[] inputs = new WndBlacksmith.ItemButton[5];
	private ItemSlot output;

	private Emitter smokeEmitter;
	private Emitter bubbleEmitter;

	private RedButton btnCombine;

	private static final int WIDTH_P = 116;
	private static final int WIDTH_L = 160;

	private static final int BTN_SIZE	= 28;

	public WndAlchemy(){

		int w = WIDTH_P;

		int h = 0;

		IconTitle titlebar = new IconTitle();
		titlebar.icon(HeroSprite.avatar(hero.heroClass, hero.tier()));
		titlebar.label( Messages.get(this, "title") );
		titlebar.setRect( 0, 0, w, 0 );
		add( titlebar );

		h += titlebar.height() + 2;

		RenderedTextMultiline desc = PixelScene.renderMultiline(6);
		desc.text( Messages.get(this, "text") );
		desc.setPos(0, h);
		desc.maxWidth(w);
		add(desc);

		h += desc.height() + 6;

		AlchemistsToolkit.alchemy alchemytool = Dungeon.hero.buff(AlchemistsToolkit.alchemy.class);
		int bonus = alchemytool != null ? alchemytool.level()/5 : 0;
		for (int i = 0; i < (inputs.length - 2 + bonus ); i++) {
			inputs[i] = new WndBlacksmith.ItemButton(){
				@Override
				protected void onClick() {
					super.onClick();
					if (item != null){
						if (!item.collect()){
							Dungeon.level.drop(item, hero.pos);
						}
						item = null;
						slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
					}
					GameScene.selectItem( itemSelector, WndBag.Mode.COOKING, Messages.get(WndAlchemy.class, "select") );
				}
			};
			inputs[i].setRect(15, h, BTN_SIZE, BTN_SIZE);
			add(inputs[i]);
			h += BTN_SIZE + 2;
		}

		Image arrow = Icons.get(Icons.RESUME);
		arrow.hardlight(0, 0, 0);
		arrow.x = (w - arrow.width)/2f;
		arrow.y = inputs[1].top() + (inputs[1].height() - arrow.height)/2f;
		PixelScene.align(arrow);
		add(arrow);

		output = new ItemSlot(){
			@Override
			protected void onClick() {
				super.onClick();
				/*if (visible){
					GameScene.show(new WndInfoItem);
				}*/
			}
		};
		output.setRect(w - BTN_SIZE - 15, inputs[1].top(), BTN_SIZE, BTN_SIZE);

		ColorBlock outputBG = new ColorBlock(output.width(), output.height(), 0x9991938C);
		outputBG.x = output.left();
		outputBG.y = output.top();
		add(outputBG);

		add(output);
		output.visible = false;

		bubbleEmitter = new Emitter();
		smokeEmitter = new Emitter();
		bubbleEmitter.pos(outputBG.x + (BTN_SIZE-16)/2f, outputBG.y + (BTN_SIZE-16)/2f, 16, 16);
		smokeEmitter.pos(bubbleEmitter.x, bubbleEmitter.y, bubbleEmitter.width, bubbleEmitter.height);
		bubbleEmitter.autoKill = false;
		smokeEmitter.autoKill = false;
		add(bubbleEmitter);
		add(smokeEmitter);

		h += 4;

		float btnWidth = (w-14)/2f;

		btnCombine = new RedButton(Messages.get(this, "combine")){
			@Override
			protected void onClick() {
				super.onClick();
				combine();
			}
		};
		btnCombine.setRect(5, h, btnWidth, 18);
		PixelScene.align(btnCombine);
		btnCombine.enable(false);
		add(btnCombine);

		RedButton btnCancel = new RedButton(Messages.get(this, "cancel")){
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnCancel.setRect(w - 5 - btnWidth, h, btnWidth, 18);
		PixelScene.align(btnCancel);
		add(btnCancel);

		h += btnCancel.height();

		resize(w, h);
	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				AlchemistsToolkit.alchemy alchemytool = Dungeon.hero.buff(AlchemistsToolkit.alchemy.class);
				int bonus = alchemytool != null ? alchemytool.level()/5 : 0;
				for (int i = 0; i < (inputs.length - 2 + bonus ); i++) {
					if (inputs[i].item == null){
						inputs[i].item(item.detach(hero.belongings.backpack));
						break;
					}
				}
			}
			updateState();
		}
	};

	private<T extends Item> ArrayList<T> filterInput(Class<? extends T> itemClass){
		ArrayList<T> filtered = new ArrayList<>();
		AlchemistsToolkit.alchemy alchemytool = Dungeon.hero.buff(AlchemistsToolkit.alchemy.class);
		int bonus = alchemytool != null ? alchemytool.level()/5 : 0;
		for (int i = 0; i < (inputs.length - 2 + bonus ); i++){
			Item item = inputs[i].item;
			if (item != null && itemClass.isInstance(item)){
				filtered.add((T)item);
			}
		}
		return filtered;
	}

	private void updateState(){
		//potion creation
            if (filterInput(Plant.Seed.class).size() == 3){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.POTION));
			output.visible = true;
			btnCombine.enable(true);

			//blandfruit cooking
		} else if(filterInput(Blandfruit.class).size() == 1 && filterInput(Plant.Seed.class).size() == 1){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
			output.visible = true;
			btnCombine.enable(true);

		/*} else if (filterInput(Honeypot.class).size() == 1 || filterInput(Honeypot.ShatteredPot.class).size() == 1 ){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
			output.visible = true;
			btnCombine.enable(true);*/
		} else if(filterInput(Item.class).size() > 0){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
			output.visible = true;
			btnCombine.enable(true);
		} else {
			btnCombine.enable(false);
			output.visible = false;
		}
	}

	private void combine(){
		ArrayList<Plant.Seed> seeds = filterInput(Plant.Seed.class);
		ArrayList<Blandfruit> blandfruits = filterInput(Blandfruit.class);
		ArrayList<Honeypot> honeypot = filterInput(Honeypot.class);
		ArrayList<Honey> honey = filterInput(Honey.class);
		ArrayList<Honeypot.ShatteredPot> shatteredpot = filterInput(Honeypot.ShatteredPot.class);
		ArrayList<WaterItem> water = filterInput(WaterItem.class);
		ArrayList<Vegetable> vegetables = filterInput(Vegetable.class);
		ArrayList<StoneOre> ore = filterInput(StoneOre.class);
		ArrayList<Nut> nut = filterInput(Nut.class);
		ArrayList<Fruit> fruits = filterInput(Fruit.class);
		ArrayList<StapleFood> staplefoods = filterInput(StapleFood.class);
		ArrayList<MeatFood> meatfoods = filterInput(MeatFood.class);
		ArrayList<Icecap.Seed> iceseed = filterInput(Icecap.Seed.class);
		ArrayList<Potion> potions = filterInput(Potion.class);
		ArrayList<Scroll> scrolls = filterInput(Scroll.class);
		ArrayList<Gel> gels = filterInput(Gel.class);
		Item result = null;

		//potion creation
		if (seeds.size() == 3){

			if (Random.Int( 3 ) == 0) {

				result = Generator.random( Generator.Category.POTION );

			} else {

				Class<? extends Item> itemClass = Random.element(seeds).alchemyClass;
				try {
					result = itemClass.newInstance();
				} catch (Exception e) {
					ShatteredPixelDungeon.reportException(e);
					result = Generator.random( Generator.Category.POTION );
				}

			}

			Statistics.potionsCooked++;
			Badges.validatePotionsCooked();

			//blandfruit cooking
		} else if (blandfruits.size() == 1 && seeds.size() == 1) {
			result = fruits.get(0);
			((Blandfruit)result).cook(seeds.get(0));
		} else if (nut.size() == 2 && water.size() == 1){
			result = new PetFood();
		} else if (honeypot.size() == 1 || shatteredpot.size() == 1) {
			result = new Honey();
		} else if (honey.size() == 1  &&  water.size() == 1 && iceseed.size() == 1 ){
			result = new Icecream();
		} else if (honey.size() == 1  && ore.size() == 1 && staplefoods.size() ==1 && water.size() == 1 && seeds.size() == 1 ){
			result = new PerfectFood();
		} else if (vegetables.size() == 1  && ore.size() == 1 && staplefoods.size() ==1 && water.size() == 1 && fruits.size() == 1 ){
			result = new PerfectFood();
		} else if ( ore.size() == 3 && water.size() == 1 && seeds.size() == 1 ){
			result = new Crystalnucleus();
		} else if ( staplefoods.size() ==2 && vegetables.size() == 1 && meatfoods.size() == 2 ){
			result = new Hamburger();
		} else if ( meatfoods.size() ==3 && vegetables.size() == 1  ){
			result = new Powerpill();
		} else if ( meatfoods.size() ==2  && ore.size() == 1 && vegetables.size() ==1 ){
			result = new Hardpill();
		} else if (meatfoods.size() ==2  && potions.size() == 1 && vegetables.size() ==1 ){
			result = new Smashpill();
		} else if (meatfoods.size() ==1  && water.size() == 1 && vegetables.size() ==1 ){
			result = new Porksoup();
		} else if ( ore.size() == 1 && water.size() == 2 && seeds.size() == 1 && fruits.size() == 1){
			result = new Foamedbeverage();
		} else if (fruits.size() == 2  && water.size() == 1  ){
			result = new Fruitsalad();
		} else if (vegetables.size() == 2 && meatfoods.size() == 1){
			result = new Vegetablekebab();
		} else if (vegetables.size() == 1 && meatfoods.size() == 2 ){
			result = new Kebab();
		} else if (water.size() == 1 && vegetables.size() == 2 ){
			result = new Vegetablesoup();
		} else if ( ore.size() == 1 && seeds.size() == 1 && scrolls.size() == 1 ){
			result = new Bomb();
		} else if (honey.size() == 1  && staplefoods.size() ==1  ){
			result = new Honeyrice();
		} else if (water.size() == 1 && seeds.size() == 1 ){
			result = new Ediblegrass();
		} else if (honey.size() == 1  && meatfoods.size() ==1  ){
			result = new Honeymeat();
		} else if ( meatfoods.size() == 1 && seeds.size() == 1 ){
			result = new Herbmeat();
		} else if ( ore.size() == 1 && meatfoods.size() == 1 ){
			result = new Chickennugget();
		} else if ( staplefoods.size() == 1 && water.size() == 1 ){
			result = new Ricefood();
		} else if ( scrolls.size() == 1 && meatfoods.size() == 1 ){
			result = new Meatroll();
		} else if ( scrolls.size() == 1 && vegetables.size() == 1 ){
			result = new Vegetableroll();
		} else if ( ore.size() == 1 && water.size() == 1 ){
			result = new Gel();
		} else if ( nut.size() == 3 ){
			result = new NormalRation();
		} else if ( honey.size() == 1 && gels.size() == 1 ){
			result = new HoneyGel();
	    } else if ( ore.size() == 1 && gels.size() == 1 ){
		    result = new Bomb();
		} else if ( gels.size() == 1  ){
			result = new Torch();
	} else result = new Garbage();

		if (result != null){
			bubbleEmitter.start(Speck.factory( Speck.BUBBLE ), 0.2f, 10 );
			smokeEmitter.burst(Speck.factory( Speck.WOOL ), 10 );
			Sample.INSTANCE.play( Assets.SND_PUFF );

			output.item(result);
			if (!result.collect()){
				Dungeon.level.drop(result, hero.pos);
			}
			AlchemistsToolkit.alchemy alchemytool = Dungeon.hero.buff(AlchemistsToolkit.alchemy.class);
			int bonus = alchemytool != null ? alchemytool.level()/5 : 0;
			for (int i = 0; i < (inputs.length - 2 + bonus ); i++){
				inputs[i].slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
				inputs[i].item = null;
			}

			btnCombine.enable(false);
		}

	}

	@Override
	public void onBackPressed() {
		AlchemistsToolkit.alchemy alchemytool = Dungeon.hero.buff(AlchemistsToolkit.alchemy.class);
		int bonus = alchemytool != null ? alchemytool.level()/5 : 0;
		for (int i = 0; i < (inputs.length - 2 + bonus ); i++) {
			if (inputs[i].item != null){
				if (!inputs[i].item.collect()){
					Dungeon.level.drop(inputs[i].item, hero.pos);
				}
			}
		}
		super.onBackPressed();
	}
}