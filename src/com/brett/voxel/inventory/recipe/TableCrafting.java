package com.brett.voxel.inventory.recipe;

import java.util.List;

import com.brett.DisplayManager;
import com.brett.voxel.inventory.Inventory;
import com.brett.voxel.inventory.Slot;
import com.brett.voxel.inventory.SlotChange;
import com.brett.voxel.tools.LevelLoader;
import com.brett.voxel.world.items.Item;
import com.brett.voxel.world.items.ItemStack;

/**
*
* @author brett
* @date May 14, 2020
*/

public class TableCrafting extends Inventory implements SlotChange {
	
	private static final long serialVersionUID = -7977649778493785209L;

	public TableCrafting() {
		super((int)LevelLoader.seed);
		float sizeX = 48*3;
		float sizeY = 48*3;
		float x = DisplayManager.WIDTH/2 - sizeX/2;
		float y = DisplayManager.HEIGHT/2 - sizeY/2 - 120;
		
		// add slots for a 3x recipe.
		this.addSlot(new Slot(x-48*(3/2), y-48*(3/2), 48, 48).setSc(this));
		this.addSlot(new Slot(x, y-48*(3/2), 48, 48).setSc(this));
		this.addSlot(new Slot(x+48*(3/2), y-48*(3/2), 48, 48).setSc(this));
		
		this.addSlot(new Slot(x-48*(3/2), y, 48, 48).setSc(this));
		this.addSlot(new Slot(x, y, 48, 48).setSc(this));
		this.addSlot(new Slot(x+48*(3/2), y, 48, 48).setSc(this));
		
		this.addSlot(new Slot(x-48*(3/2), y+48*(3/2), 48, 48).setSc(this));
		this.addSlot(new Slot(x, y+48*(3/2), 48, 48).setSc(this));
		this.addSlot(new Slot(x+48*(3/2), y+48*(3/2), 48, 48).setSc(this));
		
		this.addSlot(new Slot("o", x+48*3, y, 48, 48).setSc(this));
	}

	@Override
	public void onChange(Slot s) {
		List<Slot> slots = this.getSlots();
		int[] ids = new int[slots.size()];
		for (int i = 0; i < slots.size(); i++) {
			ids[i] = slots.get(i).getItemID();
		}
		// build the recipe as a shaped recipe.
		// (includes all items)
		StringBuilder bild = new StringBuilder();
		for (int i = 0; i < (slots.size()-1); i+=3) {
			bild.append(ids[i]);
			bild.append(",");
			bild.append(ids[i+1]);
			bild.append(",");
			bild.append(ids[i+2]);
			bild.append(";");
		}
		int amount = 0;
		int id = 0;
		if (bild.toString().length() > 0) {
			String recp = bild.toString();
			if (recp.toCharArray()[recp.length()-1] == ';')
				recp = recp.substring(0, recp.length()-1);
			// gets the recipe from the shaped recipe.
			long undecoded = CraftingManager.getRecipe(recp);
			// decode it if it exists.
			if (undecoded != 0) {
				// get out the amount by doing the inverse of ^
				amount = (int) (undecoded >> 32);
				// this makes sure that we don't have any pesky bits from the amount
				long temp = undecoded << 32;
				// convert to id
				id = (int) (temp >> 32);
			} else {
				bild = new StringBuilder();
				/**
				 * this is the crazy stuff I was talking about in player crafting. 
				 * the idea here is that if there is spaces in things then they get added. 
				 */
				if (ids[0] != 0) {
					bild.append(ids[0]);
					if (ids[1] != 0 || ids[2] != 0) {
						bild.append(",");
						if (ids[1] == 0 && ids[2] != 0)
							bild.append("0,");
					}else
						bild.append(";");
				}
				if (ids[1] != 0) {
					bild.append(ids[1]);
					if (ids[2] != 0)
						bild.append(",");
					else
						bild.append(";");
				}
				if (ids[2] != 0) {
					bild.append(ids[2]);
					bild.append(";");
				}
				if ((ids[0] != 0 || ids[1] != 0 || ids[2] != 0) && 
						ids[3] == 0 && ids[4] == 0 && ids[5] == 0 && 
						(ids[6] != 0 || ids[7] != 0 || ids[8] != 0)) {
					bild.append("0,0,0;");
				}
				if (ids[3] != 0) {
					bild.append(ids[3]);
					if (ids[4] != 0 || ids[5] != 0) {
						bild.append(",");
						if (ids[4] == 0 && ids[5] != 0)
							bild.append("0,");
					}else
						bild.append(";");
				}
				if (ids[4] != 0) {
					bild.append(ids[4]);
					if (ids[5] != 0)
						bild.append(",");
					else
						bild.append(";");
				}
				if (ids[5] != 0) {
					bild.append(ids[5]);
					bild.append(";");
				}
				if (ids[6] != 0) {
					bild.append(ids[6]);
					if (ids[7] != 0 || ids[8] != 0) {
						bild.append(",");
						if (ids[7] == 0 && ids[8] != 0)
							bild.append("0,");
					}else
						bild.append(";");
				}
				if (ids[7] != 0) {
					bild.append(ids[7]);
					if (ids[8] != 0)
						bild.append(",");
					else
						bild.append(";");
				}
				if (ids[8] != 0) {
					bild.append(ids[8]);
					bild.append(";");
				}
				//System.out.println(bild.toString());
				if (bild.toString().length() > 0) {
					// check if this recipe exists.
					recp = bild.toString();
					if (recp.toCharArray()[recp.length()-1] == ';')
						recp = recp.substring(0, recp.length()-1);
					undecoded = CraftingManager.getRecipe(recp);
					if (undecoded != 0) {
						// get out the amount by doing the inverse of ^
						amount = (int) (undecoded >> 32);
						// this makes sure that we don't have any pesky bits from the amount
						long temp = undecoded << 32;
						// convert to id
						id = (int) (temp >> 32);
					}
				}
			}
		}
		if (s.getName() == "o") {
			// remove item in the slots
			for (int i = 0; i < slots.size()-1; i++) {
				slots.get(i).removeItems(1);
			}
		}
		// same thing as player crafting
		// add items to output if there is items
		if (id != 0 && amount != 0) {
			int lowest = Integer.MAX_VALUE-1;
			int numZero = 0;
			// makes sure there is items in the crafting table
			for (int i = 0; i < slots.size()-1; i++) {
				if (slots.get(i).getItemsAmount() < lowest && slots.get(i).getItemsAmount() > 0) {
					lowest = slots.get(i).getItemsAmount();
				}
				if (slots.get(i).getItemsAmount() == 0) {
					numZero++;
				}
			}
			if (numZero == (slots.size()-1))
				lowest = 0;
			// add if items exist
			if (lowest > 0) {
				slots.get(slots.size()-1).setItemStack(new ItemStack(Item.items.get((short)id), amount));
				slots.get(slots.size()-1).updateText();
			}
		} else {
			slots.get(slots.size()-1).setItemStack(null);
			slots.get(slots.size()-1).updateText();
		}
	}
	
}
