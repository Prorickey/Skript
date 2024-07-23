package org.skriptlang.skript.bukkit.displays.item;

import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

@Name("Item Display Item")
@Description("Returns or changes the <a href='classes.html#itemstack'>itemstack</a> of <a href='classes.html#display'>item displays</a>.")
@Examples("set the display item of the last spawned item display to a diamond sword")
@RequiredPlugins("Spigot 1.19.4+")
@Since("INSERT VERSION")
public class ExprItemOfItemDisplay extends SimplePropertyExpression<Display, ItemStack> {

	static {
		if (Skript.isRunningMinecraft(1, 19, 4))
			registerDefault(ExprItemOfItemDisplay.class, ItemStack.class, "display item[stack]", "displays");
	}

	@Override
	@Nullable
	public ItemStack convert(Display display) {
		if (!(display instanceof ItemDisplay))
			return null;
		return ((ItemDisplay) display).getItemStack();
	}

	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch (mode) {
			case ADD:
			case RESET:
			case REMOVE:
			case REMOVE_ALL:
				break;
			case DELETE:
				return CollectionUtils.array();
			case SET:
				return CollectionUtils.array(ItemStack.class);
		}
		return null;
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		ItemStack item = mode == ChangeMode.DELETE ? null : (ItemStack) delta[0];
		for (Display display : getExpr().getArray(event)) {
			if (display instanceof ItemDisplay)
				((ItemDisplay) display).setItemStack(item);
		}
	}

	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	protected String getPropertyName() {
		return "display item";
	}

}
