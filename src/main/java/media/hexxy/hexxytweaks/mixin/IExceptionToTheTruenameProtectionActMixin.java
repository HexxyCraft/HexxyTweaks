package media.hexxy.hexxytweaks.mixin;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(DeployerFakePlayer.class)
public interface IExceptionToTheTruenameProtectionActMixin {
	@Accessor
	UUID getOwner();
}
