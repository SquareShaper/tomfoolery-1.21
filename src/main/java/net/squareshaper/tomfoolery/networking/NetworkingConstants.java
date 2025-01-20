package net.squareshaper.tomfoolery.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.squareshaper.tomfoolery.Tomfoolery;

public class NetworkingConstants {
    public static final Identifier TROT_PACKET_ID = Tomfoolery.id("trot");
    public static final Identifier LUNGE_PACKET_ID = Tomfoolery.id("lunge");

    public record LungePayload(boolean Lunge) implements CustomPayload {
        public static final CustomPayload.Id<LungePayload> ID = new CustomPayload.Id<>(NetworkingConstants.LUNGE_PACKET_ID);
        public static final PacketCodec<RegistryByteBuf, LungePayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, LungePayload::Lunge, LungePayload::new);

        @Override
        public LungePayload.Id<? extends LungePayload> getId() {
            return ID;
        }
    }


    public record TrotPayload(boolean Trot) implements CustomPayload {
        public static final CustomPayload.Id<TrotPayload> ID = new CustomPayload.Id<>(NetworkingConstants.TROT_PACKET_ID);
        public static final PacketCodec<RegistryByteBuf, TrotPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, TrotPayload::Trot, TrotPayload::new);
        // should you need to send more data, add the appropriate record parameters and change your codec:
        // public static final PacketCodec<RegistryByteBuf, BlockHighlightPayload> CODEC = PacketCodec.tuple(
        //         BlockPos.PACKET_CODEC, BlockHighlightPayload::blockPos,
        //         PacketCodecs.INTEGER, BlockHighlightPayload::myInt,
        //         Uuids.PACKET_CODEC, BlockHighlightPayload::myUuid,
        //         BlockHighlightPayload::new
        // );

        @Override
        public TrotPayload.Id<? extends TrotPayload> getId() {
            return ID;
        }
    }
}
