package com.abhiram.abhilogin.api.protocollib.listener;

import com.abhiram.abhilogin.Main;
import com.abhiram.abhilogin.api.protocollib.PremiumLogin;
import com.abhiram.abhilogin.util.PremiumLoginEncryption;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.server.TemporaryPlayerFactory;
import com.comphenix.protocol.reflect.FieldUtils;
import com.comphenix.protocol.reflect.FuzzyReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.github.games647.craftapi.model.auth.Verification;
import com.github.games647.craftapi.resolver.MojangResolver;
import org.bukkit.entity.Player;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

import static com.comphenix.protocol.PacketType.Login.Client.START;
import static com.comphenix.protocol.PacketType.Login.Server.DISCONNECT;
import static com.comphenix.protocol.PacketType.Login.Server.ENCRYPTION_BEGIN;

public class PacketListener extends PacketAdapter {
    private static MojangResolver mojangResolver = new MojangResolver();
    private final KeyPair keyPair = PremiumLoginEncryption.generateKeyPair();
    private final SecureRandom random = new SecureRandom();

    public PacketListener()
    {
        super(params()
                .plugin(Main.getInstance())
                .types(START, ENCRYPTION_BEGIN)
                .optionAsync());
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        Player sender = packetEvent.getPlayer();
        PacketType packetType = packetEvent.getPacketType();

        if (packetType == START)
        {
            try {
                onLogin(packetEvent, sender);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else
        {
            try {
                onEncryptionBegin(packetEvent, sender);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    private void onLogin(PacketEvent packetEvent, Player player) throws InvocationTargetException {
        PremiumLogin.login(player,keyPair.getPublic(),random);
    }

    private void onEncryptionBegin(PacketEvent packetEvent, Player sender) throws IOException {
        byte[] sharedSecret = packetEvent.getPacket().getByteArrays().read(0);

        PrivateKey privateKey = keyPair.getPrivate();

        SecretKey loginKey = null;
        try
        {
            loginKey = PremiumLoginEncryption.decryptSharedKey(privateKey, sharedSecret);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        String serverId = PremiumLoginEncryption.getServerIdHashString("", loginKey, keyPair.getPublic());

        Optional<Verification> response = mojangResolver.hasJoined(sender.getName(),serverId,sender.getAddress().getAddress());

        if(response.isPresent())
        {
            Verification verification = response.get();
            Main.getInstance().getLogger().info("Player Has primium account!");

            setPremiumUUID(sender.getUniqueId(),sender);
            receiveFakeStartPacket(sender.getName(),sender);
        }
    }

    private void receiveFakeStartPacket(String username,Player player) {
        //see StartPacketListener for packet information
        PacketContainer startPacket = new PacketContainer(START);

        //uuid is ignored by the packet definition
        WrappedGameProfile fakeProfile = new WrappedGameProfile(UUID.randomUUID(), username);
        startPacket.getGameProfiles().write(0, fakeProfile);
        try {
            //we don't want to handle our own packets so ignore filters
            ProtocolLibrary.getProtocolManager().recieveClientPacket(player, startPacket, false);
        } catch (InvocationTargetException | IllegalAccessException ex) {
            //cancel the event in order to prevent the server receiving an invalid packet
            kickPlayer("IDK",player);
        }
    }


    private void kickPlayer(String reason,Player player) {
        PacketContainer kickPacket = new PacketContainer(DISCONNECT);
        kickPacket.getChatComponents().write(0, WrappedChatComponent.fromText(reason));
        try {
            //send kick packet at login state
            //the normal event.getPlayer.kickPlayer(String) method does only work at play state
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, kickPacket);
            //tell the server that we want to close the connection
            player.kickPlayer("Disconnect");
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    private void setPremiumUUID(UUID premiumUUID,Player player) {
        if (plugin.getConfig().getBoolean("premiumUuid") && premiumUUID != null) {
            try {
                Object networkManager = getNetworkManager(player);
                //https://github.com/bergerkiller/CraftSource/blob/master/net.minecraft.server/NetworkManager.java#L69
                FieldUtils.writeField(networkManager, "spoofedUUID", premiumUUID, true);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    private Object getNetworkManager(Player player) throws IllegalAccessException, ClassNotFoundException {
        Object injectorContainer = TemporaryPlayerFactory.getInjectorFromPlayer(player);

        //ChannelInjector
        Class<?> injectorClass = Class.forName("com.comphenix.protocol.injector.netty.Injector");
        Object rawInjector = FuzzyReflection.getFieldValue(injectorContainer, injectorClass, true);
        return FieldUtils.readField(rawInjector, "networkManager", true);
    }
}
