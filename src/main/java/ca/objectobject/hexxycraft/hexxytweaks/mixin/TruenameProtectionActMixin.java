package ca.objectobject.hexxycraft.hexxytweaks.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;

// mixin to deployer fake player class to force it to use its fallback uuid rather than the player one passed to it
// maybe better way to balance this? perhaps using a focus on it would let it actually have that uuid, but that's too much work for now
@Mixin(DeployerFakePlayer.class)
public class TruenameProtectionActMixin {
    @ModifyArg (method="<init>(Lnet/minecraft/server/level/ServerLevel;Ljava/util/UUID;)V", index=2,
        at=@At(value="INVOKE", target="com/simibubi/create/content/kinetics/deployer/DeployerFakePlayer$DeployerGameProfile.<init> (Ljava/util/UUID;Ljava/lang/String;Ljava/util/UUID;)V"))
    private static UUID nullifyUUID(UUID owner){
        return null;
    }
    // @Inject (method="<init>(Lnet/minecraft/server/level/ServerLevel;Ljava/util/UUID;)V", 
    //     at=@At("HEAD"))
    // private static void nullifyUUID(CallbackInfo ci, @Local LocalRef<UUID> ownerRef){
    //     ownerRef.set(null);
    // }
}
