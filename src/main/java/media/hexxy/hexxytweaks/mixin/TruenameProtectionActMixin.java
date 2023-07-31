package media.hexxy.hexxytweaks.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;

// mixin to deployer fake player class to force it to use its fallback uuid rather than the player one passed to it
// maybe better way to balance this? perhaps using a focus on it would let it actually have that uuid, but that's too much work for now
@Mixin(DeployerFakePlayer.class)
public class TruenameProtectionActMixin {
    @Inject(method="getUUID()Ljava/util/UUID;", at=@At("HEAD"), cancellable=true)
    private void redirectGetUuid(CallbackInfoReturnable<UUID> cir){
        cir.setReturnValue(DeployerFakePlayer.fallbackID);
    }
}
