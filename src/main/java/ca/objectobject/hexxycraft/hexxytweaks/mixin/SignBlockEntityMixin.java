package ca.objectobject.hexxycraft.hexxytweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ca.objectobject.hexxycraft.hexxytweaks.helper.DamageSourceShameOnYou;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.SignBlockEntity;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {

	@Inject(method = "executeClickCommands", at = @At("HEAD"), cancellable = true)
	private void executeClickCommands(ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
		player.hurt(DamageSourceShameOnYou.SHAME, player.getMaxHealth());
	}

}

