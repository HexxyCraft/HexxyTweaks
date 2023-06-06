/*
MIT License

Copyright (c) 2019 simibubi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package ca.objectobject.hexxycraft.hexxytweaks.common;

import java.util.function.Predicate;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.simibubi.create.content.curiosities.tools.ExtendoGripItem;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.utility.placement.IPlacementHelper;
import com.simibubi.create.foundation.utility.placement.PlacementOffset;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import static net.minecraft.world.level.block.LadderBlock.FACING;

/**
 * Code directly copied from Create since I couldn't figure out how to get access to it.
 * @see com.simibubi.create.content.curiosities.deco.MetalLadderBlock.PlacementHelper
 */
@MethodsReturnNonnullByDefault
public class LadderPlacementHelper implements IPlacementHelper {

	@Override
	public Predicate<ItemStack> getItemPredicate() {
		return i -> i.getItem() instanceof BlockItem bItem && bItem.getBlock() instanceof LadderBlock;
	}

	@Override
	public Predicate<BlockState> getStatePredicate() {
		return s -> s.getBlock() instanceof LadderBlock;
	}

	public int attachedLadders(Level world, BlockPos pos, Direction direction) {
		BlockPos checkPos = pos.relative(direction);
		BlockState state = world.getBlockState(checkPos);
		int count = 0;
		while (getStatePredicate().test(state)) {
			count++;
			checkPos = checkPos.relative(direction);
			state = world.getBlockState(checkPos);
		}
		return count;
	}

	@Override
	public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
									 BlockHitResult ray) {
		Direction dir = player.getXRot() < 0 ? Direction.UP : Direction.DOWN;

		int range = AllConfigs.SERVER.curiosities.placementAssistRange.get();
		if (player != null) {
			AttributeInstance reach = player.getAttribute(ReachEntityAttributes.REACH);
			if (reach != null && reach.hasModifier(ExtendoGripItem.singleRangeAttributeModifier))
				range += 4;
		}

		int ladders = attachedLadders(world, pos, dir);
		if (ladders >= range)
			return PlacementOffset.fail();

		BlockPos newPos = pos.relative(dir, ladders + 1);
		BlockState newState = world.getBlockState(newPos);

		if (!state.canSurvive(world, newPos))
			return PlacementOffset.fail();

		if (newState.getMaterial().isReplaceable())
			return PlacementOffset.success(newPos, bState -> bState.setValue(FACING, state.getValue(FACING)));
		return PlacementOffset.fail();
	}

}
