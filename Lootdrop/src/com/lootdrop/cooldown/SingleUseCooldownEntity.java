package com.lootdrop.cooldown;

public abstract class SingleUseCooldownEntity extends CooldownEntity{
	
	public int timer;
	public final int initialTime;
	public boolean running = true;
	public boolean forever = false;
	
	public SingleUseCooldownEntity(int timer) {
		this.timer = timer;
		this.initialTime = timer;
	}
	
	public SingleUseCooldownEntity(int timer, boolean forever) {
		this.timer = timer;
		this.forever = forever;
		this.initialTime = timer;
	}
	
	@Override
	public void onTick() {
		if(!running && !forever) return;
		this.timer -= 1;
		this.tick();
		if(timer <= 0) {
			this.running = false;
			this.onTaskEnd();
			if(this.forever) {
				this.timer = initialTime;
			}
		}
	}
	
	public abstract void tick();
	public abstract void onTaskEnd();

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
}
