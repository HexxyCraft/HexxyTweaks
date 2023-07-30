package ca.objectobject.hexxycraft.hexxytweaks.helper;

import net.minecraft.world.damagesource.DamageSource;

public class DamageSourceShameOnYou extends DamageSource {
	public static final DamageSource SHAME = new DamageSourceShameOnYou();
	public DamageSourceShameOnYou() {
		super("hexcasting.shame");
		this.bypassArmor();
		this.bypassMagic();
		this.setMagic();
	}
}
