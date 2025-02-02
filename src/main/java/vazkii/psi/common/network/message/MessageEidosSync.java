/*
 * This class is distributed as part of the Psi Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 *
 * Psi is Open Source and distributed under the
 * Psi License: https://psi.vazkii.net/license.php
 */
package vazkii.psi.common.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import vazkii.psi.common.Psi;
import vazkii.psi.common.core.handler.PlayerDataHandler;

import java.util.function.Supplier;

public class MessageEidosSync {

	private final int reversionTime;

	public MessageEidosSync(int reversionTime) {
		this.reversionTime = reversionTime;
	}

	public MessageEidosSync(FriendlyByteBuf buf) {
		this.reversionTime = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(reversionTime);
	}

	public boolean receive(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			Player player = Psi.proxy.getClientPlayer();
			if(player != null) {
				PlayerDataHandler.PlayerData data = PlayerDataHandler.get(player);
				data.eidosReversionTime = reversionTime;
				data.isReverting = true;
			}
		});

		return true;
	}

}
