/*
MIT License

Copyright (c) 2019 simibubi, object-Object

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

package ca.objectobject.hexxycraft.hexxytweaks.mixin;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.LadderBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

/**
 * Overrides the item predicate to allow extending metal ladders with vanilla ones.
 */
@Mixin(targets = "com.simibubi.create.content.curiosities.deco.MetalLadderBlock$PlacementHelper", remap = false)
public abstract class MetalLadderBlockPlacementHelperMixin {

	@Inject(method = "getItemPredicate", at = @At("RETURN"), cancellable = true)
	private void getItemPredicateReturnMixin(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
		cir.setReturnValue(i -> i.getItem() instanceof BlockItem bItem && bItem.getBlock() instanceof LadderBlock);
	}

}
