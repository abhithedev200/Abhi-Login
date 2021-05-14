package com.abhiram.abhilogin.api.protocollib;

import com.abhiram.abhilogin.util.PremiumLoginEncryption;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.security.PublicKey;
import java.util.Random;

import static com.comphenix.protocol.PacketType.Login.Server.ENCRYPTION_BEGIN;

public class PremiumLogin {

    private static byte[] verifyToken;


    public static void login(Player p,PublicKey publicKey,Random random) throws InvocationTargetException {
        verifyToken = PremiumLoginEncryption.generateVerifyToken(random);

        PacketContainer newpacket = new PacketContainer(ENCRYPTION_BEGIN);

        String server_id = "";

        newpacket.getStrings().write(0, server_id);

        newpacket.getSpecificModifier(PublicKey.class).write(0, publicKey);

        newpacket.getByteArrays().write(0, verifyToken);

        ProtocolLibrary.getProtocolManager().sendServerPacket(p, newpacket);
    }
}
