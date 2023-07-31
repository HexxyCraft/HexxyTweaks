package ca.objectobject.hexxycraft.hexxytweaks.mixin;

import ca.objectobject.hexxycraft.hexxytweaks.HexxyTweaks;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.entity.SignBlockEntity;

import java.util.UUID;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin extends BlockEntity {
	// reuse hex's shame damage
	final DamageSource SHAME_ON_YOU = new DamageSource("hexcasting.shame")
			.bypassArmor().bypassMagic().setMagic();

	public SignBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	@Redirect(method = "executeClickCommands", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/Commands;performPrefixedCommand(Lnet/minecraft/commands/CommandSourceStack;Ljava/lang/String;)I"))
	private int goToJailGoDirectlyToJailDoNotPassGoDoNotRunCommand(Commands instance, CommandSourceStack source, String command) {
		// void the sign
		if (level != null) level.destroyBlock(getBlockPos(), false);

		// smite the infidels
		ServerPlayer player = source.getPlayer();
		if (player != null) {
			if (player instanceof DeployerFakePlayer deployerFakePlayer) {
				shameDeployer(deployerFakePlayer);
			} else {
				shamePlayer(player);
			}
		}

		return 0;
	}

	private void shameDeployer(DeployerFakePlayer deployerFakePlayer) {
		HexxyTweaks.LOGGER.warn("A rogue deployer tried to use a command sign at {}",
				getBlockPos().toShortString());

		// shame the owner of the deployer that tried to use the sign
		UUID ownerUUID = ((IExceptionToTheTruenameProtectionActMixin) deployerFakePlayer).getOwner();
		if (ownerUUID != null) shamePlayer(ownerUUID);

		// shame the deployer (by breaking it)
		// bad deployer, no enlightenment
		Vec3 deployerLook = deployerFakePlayer.getLookAngle();
		Direction deployerLookDir = Direction.getNearest(deployerLook.x, deployerLook.y, deployerLook.z);
		BlockPos deployerPos = deployerFakePlayer.blockPosition().relative(deployerLookDir, -2);
		if (level != null) level.destroyBlock(deployerPos, true);
	}

	private void shamePlayer(UUID playerUUID) {
		Player player;
		if (level != null && (player = level.getPlayerByUUID(playerUUID)) != null) {
			shamePlayer(player);
		} else {
			HexxyTweaks.LOGGER.warn("Offline player with UUID {} tried to use a command sign at {}",
					playerUUID, getBlockPos().toShortString());
		}
	}

	private void shamePlayer(Player player) {
		HexxyTweaks.LOGGER.warn("Player {} tried to use a command sign at {}",
				player.getName().getString(), getBlockPos().toShortString());
		player.hurt(SHAME_ON_YOU, 69420f);
	}
}

