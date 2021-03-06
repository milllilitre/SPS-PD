package com.hmdzl.spspd.change.actors.blobs.weather;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cold;
import com.hmdzl.spspd.change.actors.buffs.DeadRaise;
import com.hmdzl.spspd.change.actors.buffs.Hot;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.particles.DeadParticle;
import com.hmdzl.spspd.change.effects.particles.SnowParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.messages.Messages;

public class WeatherOfDead extends Blob {

    protected int pos;

	@Override
	protected void evolve() {
		int from = WIDTH + 1;
		int to = Level.LENGTH - WIDTH - 1;
		
		int[] map = Dungeon.level.map;
		
		for (int pos=from; pos < to; pos++) {
			if (cur[pos] > 0) {
				
				off[pos] = cur[pos];
				volume += off[pos];
				
			} else {
				off[pos] = 0;
			}
		}

		Hero hero = Dungeon.hero;
		if (hero.isAlive() && cur[hero.pos] > 0) {
			Buff.prolong( hero, DeadRaise.class, DeadRaise.DURATION );
			Buff.detach(hero,Hot.class);
		}

	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.start(DeadParticle.FACTORY, 0.3f, 0);
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
	
}