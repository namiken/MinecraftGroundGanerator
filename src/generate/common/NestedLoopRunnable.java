package generate.common;

import org.bukkit.scheduler.BukkitRunnable;

public class NestedLoopRunnable extends BukkitRunnable {
	int start1 = 0;
	int end1;
	int start2 = 0;
	int end2;

	int step1 = 1;
	int step2 = 1;

	NestedLoopExcuter excuter;

	public NestedLoopRunnable(int end1, int end2, NestedLoopExcuter excuter) {
		this.end1 = end1;
		this.end2 = end2;
		this.excuter = excuter;
	}

	int count = 0;

	int loop1 = 0;
	int loop2 = 0;

	@Override
	public void run() {
		for (;loop1 < end1; loop1+=step1) {
			for (;loop2 < end2; loop2+=step2) {
				boolean isGoReturn = excuter.excute(loop1, loop2, count);
				count++;
				if (isGoReturn) {
					return;
				}
			}
			loop2 = start2;
		}
		cancel();
	}
}
