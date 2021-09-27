//Hello?
/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 */
package client.modules.miscellaneous;

import client.gui.impl.setting.Setting;
import client.modules.Module;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class FakePlayer
extends Module {
    private static FakePlayer INSTANCE = new FakePlayer();
    public Setting<Boolean> copyInv = this.register(new Setting<Boolean>("Copy Inventory", true));
    public Setting<Boolean> moving = this.register(new Setting<Boolean>("Moving", false));
    public Setting<Integer> motion = this.register(new Setting<Object>("Motion", Integer.valueOf(2), Integer.valueOf(-5), Integer.valueOf(5), v -> this.moving.getCurrentState()));
    private final int entityId = -420;

    public FakePlayer() {
        super("FakePlayer", "Spawns a FakePlayer for testing.", Module.Category.MISC);
        this.setInstance();
    }

    public static FakePlayer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakePlayer();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        if (FakePlayer.fullNullCheck()) {
            return;
        }
        FakePlayer.mc.world.removeEntityFromWorld(-420);
    }

    @Override
    public void onUpdate() {
        if (this.moving.getCurrentState().booleanValue()) {
            if (FakePlayer.fullNullCheck()) {
                return;
            }
            GameProfile profile = new GameProfile(UUID.fromString("12cbdfad-33b7-4c07-aeac-01766e609482"), "FakePlayer");
            EntityOtherPlayerMP player = new EntityOtherPlayerMP((World)FakePlayer.mc.world, profile);
            player.setLocationAndAngles(FakePlayer.mc.player.posX + player.motionX + (double)this.motion.getCurrentState().intValue(), FakePlayer.mc.player.posY + player.motionY, FakePlayer.mc.player.posZ + player.motionZ + (double)this.motion.getCurrentState().intValue(), 90.0f, 90.0f);
            player.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
            if (this.copyInv.getCurrentState().booleanValue()) {
                player.inventory.copyInventory(FakePlayer.mc.player.inventory);
            }
            FakePlayer.mc.world.addEntityToWorld(-420, (Entity)player);
            return;
        }
    }

    @Override
    public void onEnable() {
        if (!this.moving.getCurrentState().booleanValue()) {
            if (FakePlayer.fullNullCheck()) {
                return;
            }
            GameProfile profile = new GameProfile(UUID.fromString("12cbdfad-33b7-4c07-aeac-01766e609482"), "FakePlayer");
            EntityOtherPlayerMP player = new EntityOtherPlayerMP((World)FakePlayer.mc.world, profile);
            player.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
            player.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
            if (this.copyInv.getCurrentState().booleanValue()) {
                player.inventory.copyInventory(FakePlayer.mc.player.inventory);
            }
            FakePlayer.mc.world.addEntityToWorld(-420, (Entity)player);
            return;
        }
    }
}

