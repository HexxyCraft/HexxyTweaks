package media.hexxy.hexxytweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

// https://www.fabricmc.net/wiki/tutorial:mixinheritance
@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviourMixin {

	// don't listen to the IDE, we need cancellable here (I think)
	@Inject(method = "skipRendering", at = @At("RETURN"), cancellable = true)
	protected void skipRenderingReturnMixin(BlockState state, BlockState adjacentBlockState, Direction direction,
											CallbackInfoReturnable<Boolean> cir) {}

	@Inject(method = "use", at = @At("RETURN"), cancellable = true)
	protected void useReturnMixin(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
								  BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {}

}
