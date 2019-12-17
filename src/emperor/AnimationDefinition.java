package emperor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apollo.fs.util.ByteBufferUtil;

import com.alex.store.Store;
import com.alex.utils.ByteBufferUtils;

/**
 * Represents an animation's definitions.
 * @author Emperor
 */
public final class AnimationDefinition {

    public int anInt2136;
    public int anInt2137;
    public int[] anIntArray2139;
    public int anInt2140;
    public boolean aBoolean2141 = false;
    public int anInt2142;
    public int emoteItem;
    public int anInt2144 = -1;
    public int[][] handledSounds;
    public boolean[] aBooleanArray2149;
    public int[] anIntArray2151;
    public boolean aBoolean2152;
    public int[] durations;
    public int anInt2155;
    public boolean aBoolean2158;
    public boolean aBoolean2159;
    public int anInt2162;
    public int anInt2163;
    boolean newHeader;
    boolean requiresPacking = false;

    // added
    public int[] soundMinDelay;
    public int[] soundMaxDelay;
    public int[] anIntArray1362;
    public boolean effect2Sound;
    public int id;
    boolean os;

    private static final Map<Integer, AnimationDefinition> animDefs = new HashMap<>();

    public static final AnimationDefinition forId(Store store, int emoteId) {
        return forId(store, emoteId, false);
    }


    public static final AnimationDefinition forId(Store store, int emoteId, boolean os) {
        try {
            AnimationDefinition defs = animDefs.get(emoteId);
            if (defs != null) {
                return defs;
            }
            byte[] data = null;
            if (os) {
                data = store.getIndexes()[2].getFile(12, emoteId);
            } else {
                data = store.getIndexes()[20].getFile(emoteId >>> 7, emoteId & 0x7f);
            }
            defs = new AnimationDefinition();
            defs.os = os;
            defs.id = emoteId;
            if (data != null) {
                defs.readValueLoop(ByteBuffer.wrap(data));
            }
            defs.method2394();
            animDefs.put(emoteId, defs);
            return defs;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    private void readValueLoop(ByteBuffer buffer) {
        for (;;) {
            int opcode = buffer.get() & 0xFF;
            if (opcode == 0) {
                break;
            }
            readValues(buffer, opcode);
        }
    }

    /**
     * Gets the duration of this animation in milliseconds.
     * @return The duration.
     */
    public int getDuration() {
        if (durations == null) {
            return 0;
        }
        int duration = 0;
        for (int i : durations) {
            if (i > 100) {
                continue;
            }
            duration += i * 20;
        }
        return duration;
    }

    /**
     * Gets the duration of this animation in (600ms) ticks.
     * @return The duration in ticks.
     */
    public int getDurationTicks() {
        int ticks = getDuration() / 600;
        return ticks < 1 ? 1 : ticks;
    }

    public byte[] pack() {
        ByteBuffer buffer = ByteBuffer.allocate(888888);
        if (durations != null) {
            buffer.put((byte) 1);
            buffer.putShort((short) durations.length);
            for (int duration : durations) {
                buffer.putShort((short) duration);
            }
            for (int frame : anIntArray2139) {
                buffer.putShort((short) (frame & 0xFFFF));
            }
            for (int frame : anIntArray2139) {
                buffer.putShort((short) (frame >> 16));
            }
        }
        if (anInt2163 != -1) {
            buffer.put((byte) 2);
            buffer.putShort((short) anInt2163);
        }
        if (aBooleanArray2149 != null) {
            buffer.put((byte) 3);
            int pos = buffer.position();
            buffer.put((byte) 0);
            for (int i = 0; i < aBooleanArray2149.length; i++) {
                if (aBooleanArray2149[i]) {
                    buffer.put((byte) i);
                }
            }
            buffer.put(pos, (byte) (buffer.position() - (1 + pos)));
        }
        if (aBoolean2152) {
            buffer.put((byte) 4);
        }
        if (anInt2142 != 5) {
            buffer.put((byte) 5);
            buffer.put((byte) anInt2142);
        }
        if (anInt2144 != -1) {
            buffer.put((byte) 6);
            buffer.putShort((short) anInt2144);
        }
        if (emoteItem != -1) {
            buffer.put((byte) 7);
            buffer.putShort((short) emoteItem);
        }
        if (anInt2136 != 99) {
            buffer.put((byte) 8);
            buffer.put((byte) anInt2136);
        }
        if (anInt2140 != -1) {
            buffer.put((byte) 9);
            buffer.put((byte) anInt2140);
        }
        if (anInt2162 != -1) {
            buffer.put((byte) 10);
            buffer.put((byte) anInt2162);
        }
        if (anInt2155 != 2) {
            buffer.put((byte) 11);
            buffer.put((byte) anInt2155);
        }
        if (anIntArray2151 != null) {
            buffer.put((byte) 12);
            buffer.put((byte) anIntArray2151.length);
            for (int i : anIntArray2151) {
                buffer.putShort((short) (i & 0xFFFF));
            }
            for (int i : anIntArray2151) {
                buffer.putShort((short) ((i >> 16) & 0xFFFF));
            }
        }
        if (handledSounds != null) {
            buffer.put((byte) 13);
            buffer.putShort((byte) handledSounds.length);
            for (int[] sounds : handledSounds) {
                if (sounds == null) {
                    buffer.put((byte) 0);
                    continue;
                }
                buffer.put((byte) sounds.length);
                ByteBufferUtils.putTriByte(sounds[0], buffer);
                for (int j = 1; j < sounds.length; j++) {
                    buffer.putShort((short) sounds[j]);
                }
            }
        }
        if (aBoolean2141) {
            buffer.put((byte) 14);
        }
        buffer.put((byte) 0);
        buffer.flip();
        byte[] bs = new byte[buffer.remaining()];
        buffer.get(bs);
        return bs;
    }

    private void readValues(ByteBuffer buffer, int opcode) {
        if (opcode == 1) {
            int length = buffer.getShort() & 0xFFFF;
            durations = new int[length];
            for (int i = 0; i < length; i++) {
                durations[i] = buffer.getShort() & 0xFFFF;
            }
            anIntArray2139 = new int[length];
            for (int i = 0; i < length; i++) {
                anIntArray2139[i] = buffer.getShort() & 0xFFFF;
            }
            for (int i = 0; i < length; i++) {
                anIntArray2139[i] = (((buffer.getShort() & 0xFFFF) << 16) + anIntArray2139[i]);
            }
        } else if (opcode != 2) {
            if (opcode != 3) {
                if (opcode == 4)
                    aBoolean2152 = true;
                else if (opcode == 5)
                    anInt2142 = buffer.get() & 0xFF;
                else if (opcode != 6) {
                    if (opcode == 7)
                        emoteItem = buffer.getShort() & 0xFFFF;
                    else if ((opcode ^ 0xffffffff) != -9) {
                        if (opcode != 9) {
                            if (opcode != 10) {
                                if (opcode == 11)
                                    anInt2155 = buffer.get() & 0xFF;
                                else if (opcode == 12) {
                                    int i = buffer.get() & 0xFF;
                                    anIntArray2151 = new int[i];
                                    for (int i_19_ = 0; ((i_19_ ^ 0xffffffff) > (i ^ 0xffffffff)); i_19_++)
                                        anIntArray2151[i_19_] = buffer.getShort() & 0xFFFF;
                                    for (int i_20_ = 0; i > i_20_; i_20_++)
                                        anIntArray2151[i_20_] = ((buffer.getShort() & 0xFFFF << 16) + anIntArray2151[i_20_]);
                                } else if (opcode == 13) {
                                    // opcode 13
                                    if (os) {
                                        int length = buffer.get() & 0xFF;
                                        handledSounds = new int[1][length];
                                        for (int i = 0; i < length; i++) {
                                            handledSounds[0][i] = ByteBufferUtil.readUnsignedTriByte(buffer);
                                        }
                                    } else {
                                        int i = buffer.getShort() & 0xFFFF;
                                        handledSounds = new int[i][];
                                        for (int i_21_ = 0; i_21_ < i; i_21_++) {
                                            int i_22_ = buffer.get() & 0xFF;
                                            if ((i_22_ ^ 0xffffffff) < -1) {
                                                handledSounds[i_21_] = new int[i_22_];
                                                handledSounds[i_21_][0] = ByteBufferUtil.readUnsignedTriByte(buffer);
                                                for (int i_23_ = 1; ((i_22_ ^ 0xffffffff) < (i_23_ ^ 0xffffffff)); i_23_++) {
                                                    handledSounds[i_21_][i_23_] = buffer.getShort() & 0xFFFF;
                                                }
                                            }
                                        }
                                    }
                                } else if (opcode == 14) {
                                    aBoolean2141 = true;
                                } else if (opcode == 15) {

                                } else if (opcode == 16) {

                                } else if (opcode == 18) {

                                } else if (opcode == 19) {
                                    buffer.get();
                                    buffer.get();
                                } else if (opcode == 20) {
                                    buffer.get();
                                    buffer.getShort();
                                    buffer.getShort();
                                }



                                else {
                                    System.out.println("Unhandled animation opcode " + opcode);
                                }
                            } else
                                anInt2162 = buffer.get() & 0xFF;
                        } else
                            anInt2140 = buffer.get() & 0xFF;
                    } else
                        anInt2136 = buffer.get() & 0xFF;
                } else
                    anInt2144 = buffer.getShort() & 0xFFFF;
            } else {
                aBooleanArray2149 = new boolean[256];
                int length = buffer.get() & 0xFF;
                for (int i = 0; i < length; i++) {
                    aBooleanArray2149[buffer.get() & 0xFF] = true;
                }
            }
        } else
            anInt2163 = buffer.getShort() & 0xFFFF;
    }

    public void method2394() {
//		if (anInt2140 == -1) {
//			if (aBooleanArray2149 == null)
//				anInt2140 = 0;
//			else
//				anInt2140 = 2;
//		}
//		if (anInt2162 == -1) {
//			if (aBooleanArray2149 == null)
//				anInt2162 = 0;
//			else
//				anInt2162 = 2;
//		}
    }

    public AnimationDefinition() {
        anInt2136 = 99;
        emoteItem = -1;
        anInt2140 = -1;
        aBoolean2152 = false;
        anInt2142 = 5;
        aBoolean2159 = false;
        anInt2163 = -1;
        anInt2155 = 2;
        aBoolean2158 = false;
        anInt2162 = -1;
    }


    public void debug() throws Throwable {
        for (Field f : getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(f.getModifiers())) {
                if (f.getType().isArray()) {
                    Object object = f.get(this);
                    if (object != null) {
                        int length = Array.getLength(object);
                        System.out.print(f.getName() + ", [");
                        for (int i = 0; i < length; i++) {
                            System.out.print(Array.get(object, i) + (i < (length - 1) ? ", " : "]"));
                        }
                        System.out.println();
                        continue;
                    }
                }
                System.out.println(f.getName() + ", " + f.get(this));
            }
        }
        for (Field f : getClass().getSuperclass().getDeclaredFields()) {
            if (!Modifier.isStatic(f.getModifiers())) {
                if (f.getType().isArray()) {
                    Object object = f.get(this);
                    if (object != null) {
                        int length = Array.getLength(object);
                        System.out.print(f.getName() + ", [");
                        for (int i = 0; i < length; i++) {
                            System.out.print(Array.get(object, i) + (i < (length - 1) ? ", " : "]"));
                        }
                        System.out.println();
                        continue;
                    }
                }
                System.out.println(f.getName() + ", " + f.get(this));
            }
        }
    }
}