package emperor;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import alex.cache.loaders.ObjectDefinitions;
import alex.cache.loaders.OverlayDefinition;

import com.alex.loaders.images.IndexedColorImageFile;
import com.alex.store.Store;
import com.alex.utils.Constants;

/**
 * Packs the models.
 *
 * @author Emperor
 */
public final class ModelPacker {
    private static String original498 = "D:\\RSPS_NEW\\caches\\fresh498cache/";
    private static String cache508 = "C:\\Users\\Chris\\Desktop\\rs508_cache/";
    private static String cache639 = "C:\\Users\\Chris\\Desktop\\Zaros\\Cache Editor\\639Cache/";
    private static String cache667 = "D:\\RSPS_NEW\\caches\\667Cache/";
    private static String cache718 = "D:\\RSPS_NEW\\718 Orig Cache/";
    private static String cache530 = "C:\\Users\\Chris\\Desktop\\WildScape Server 530\\data\\cache/";
    private static String cache562 = "C:\\Users\\Chris\\Desktop\\562_cache/";
    private static String cache574 = "D:\\RSPS_NEW\\caches\\574cache/";
    private static String cache614 = "C:\\Users\\Chris\\Desktop\\SpawnScape\\cache614\\runescape/";
    private static String cache647 = "D:\\RSPS_NEW\\caches\\643cache/";

    public static String mapEditor = "C:\\Users\\Chris\\Desktop\\Map editor\\cache/";
    public static String mapEditor131 = "C:\\Users\\Chris\\Desktop\\Map editor 131 OSRS\\cache/";


    public static void main(String... args) throws Throwable {
        Store to = new Store("C:\\Users\\Chris\\Desktop\\Zaros\\Zaros_Server\\data\\cache/");
        //Store to = new Store(mapEditor131);
        Store from = new Store(cache574);

		/*for(int i = 0; i < 15275; i++) {
            packAnimation(from, to, i);
			System.out.println("repacked: "+i);
		}

		to.getIndexes()[20].rewriteTable();
		to.getIndexes()[20].resetCachedFiles();
		to.getIndexes()[1].rewriteTable();
		to.getIndexes()[1].resetCachedFiles();*/

        //System.out.println(getItemDefinitionsSize(to));

        //rewriteTables(to);
        //packTextures(from, to);
       // packMaterials(from, to);
       packSprite(from, to);
        //packSpriteIndex(from, to);
        //packInterface(from, to);
        //packScript(from, to);
        //packScriptMaps(from, to);
        //packConfigs(from, to);
       // packFonts(from, to);
        //packQuickChats(from, to);
        //packExternalSprite(to);
        //packSkins(from, to);
        //packGFX(from, to);
        //packCs2Map(from, to);
        //packSprites(from, to);
        //packNpcs(from, to);
        //packVarbit(from, to);
        //packItems(to);
        //pack718Objects(from, to);
        //packUnderlays(from, to);
        //packOverlays(from, to);
        //pack718Models(from, to);
        //packDonatorIcons(to);
        //packObjectDefinitions(from, to);
        //packAnimations(from, to);
            /*	List<Integer> anims = new ArrayList<>();
				for (int i = 0; i < 50000; i++) {
					byte[] data = from.getIndexes()[16].getFile(i >>> 1998118472, i & 0xff);
					if (data == null) {
						continue;
					}
					//System.out.println("i");
					ObjectDefinitions def = new ObjectDefinitions(i);
					def.initialize(i, from);
					System.out.println(def.models);
					if (def.animationId > -1) {
						if (!anims.contains(def.animationId)) {
							anims.add(def.animationId);
						}
						System.out.println(def.getName() + " anim: " + def.animationId + ", " + Arrays.toString(def.models));
					}
				}
				System.out.println(Arrays.toString(anims.toArray()));
				//packAnimations(from, to);*/
    }

    static void rewriteTables(Store to) {
        for(int i = 0; i < to.getIndexes().length; i++) {
            to.getIndexes()[i].rewriteTable();
            to.getIndexes()[i].resetCachedFiles();
        }
    }

    static void packItems(Store to) {
        //System.out.println(getItemDefinitionsSize(from));
        for (int id = 0; id < 20794; id++) {
            int archiveTo = id >>> 8;
            int file = id & 0xFF;
            byte[] bs = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros\\647Items/" + id + ".dat");
            ;
            if (bs == null)
                continue;
            boolean succesful = to.getIndexes()[19].putFile(archiveTo, file, bs);
            if (!succesful)
                System.out.println("failed: " + id);
            else {
                System.out.println("Packed: " + id);
            }
        }
        to.getIndexes()[19].rewriteTable();
        to.getIndexes()[19].resetCachedFiles();
    }

    public static final int getItemDefinitionsSize(Store store) {
        int lastArchiveId = store.getIndexes()[19].getLastArchiveId();
        return lastArchiveId
                * 256
                + store.getIndexes()[19]
                .getValidFilesCount(lastArchiveId);
    }

    static void packNpcs(Store from, Store to) {
        System.out.println(getItemDefinitionsSize(from));//57265
        for (int id = 15462; id < 15463; id++) {
            int archiveTo = id >>> 8;
            int file = id & 0xFF;
            byte[] bs = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros\\718npcs/" + id + ".dat");
            ;
            if (bs == null)
                continue;
            boolean succesful = to.getIndexes()[18].putFile(archiveTo, file, bs);
            if (!succesful)
                System.out.println("failed: " + id);
            else {
                System.out.println("Packed: " + id);
            }
        }
        to.getIndexes()[18].rewriteTable();
        to.getIndexes()[18].resetCachedFiles();
    }

    static void packObjects(Store from, Store to) {
        System.out.println("Start");
        for (int id = 0; id < 72692; id++) {
            byte[] bs = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros/718objects/" + id + ".dat");
            ;
            if (bs == null) {
                continue;
            }
            boolean success = to.getIndexes()[16].putFile(id >>> 8, id & 0xff, bs);
            System.out.println("Packed obj definition " + id + " - success=" + success);
        }
        to.getIndexes()[16].rewriteTable();
        to.getIndexes()[16].resetCachedFiles();
    }

    static void pack718Objects(Store from, Store to) {
        System.out.println("Start");
        for (int id = 0; id < 72692; id++) {
            byte[] bs = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros/718objects/" + id + ".dat");
            ;
            if (bs == null) {
                continue;
            }
            boolean success = to.getIndexes()[26].putFile(id >>> 8, id & 0xff, bs);
            System.out.println("Packed obj definition " + id + " - success=" + success);
        }
        to.getIndexes()[26].rewriteTable();
        to.getIndexes()[26].resetCachedFiles();
    }

    static void packObjectDefinitions(Store from, Store to) {
        int[] defs = new int[]{5461};//5099, 5100, 5094, 5096, 5098, 5097, 5110, 5111};//5088, 5089, 5090 };
        //System.out.println(from.getIndexes()[16].getLastFileId(223));//57265
        for (int id = 0; id < 57265; id++) {
            int archiveTo = id >>> 8;
            //int archiveFrom = id >>> -1035933944;
            int file = id & 0xFF;
            byte[] bs = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros\\Data/639objects/" + id + ".dat");
            ;
            if (bs == null)
                continue;
            boolean succesful = to.getIndexes()[2].putFile(6, id, bs);
            if (!succesful)
                System.out.println("failed: " + id);
            else {
                System.out.println("Packed: " + id);
            }
        }
        to.getIndexes()[2].rewriteTable();
        to.getIndexes()[2].resetCachedFiles();
    }

    public static final byte[] ReadFile(String s) {
        try {
            byte abyte0[];
            File file = new File(s);
            int i = (int) file.length();
            abyte0 = new byte[i];
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
            datainputstream.readFully(abyte0, 0, i);
            datainputstream.close();
            return abyte0;
        } catch (Exception e) {
            System.out.println("Exception: " + new StringBuilder().append("Read Error: ").append(s).toString());
            return null;
        }
    }

    static void editObjectDefinitions(int itemId, Store store, int opcode, Object value) {
        int archive = itemId >>> 1998118472;
        int file = itemId & 0xFF;
        byte[] bs = store.getIndexes()[16].getFile(archive, file);
        ByteBuffer buffer = ByteBuffer.allocate(bs.length + 128);
        for (int i = 0; i < bs.length - 1; i++) {
            buffer.put(bs[i]);
        }
        buffer.put((byte) opcode);
        if (value instanceof Byte) {
            buffer.put((Byte) value);
        } else if (value instanceof Short) {
            buffer.putShort((Short) value);
        } else if (value instanceof Integer) {
            buffer.putInt((Integer) value);
        } else if (value instanceof Long) {
            buffer.putLong((Long) value);
        } else if (value instanceof String) {
            buffer.put(((String) value).getBytes()).put((byte) 0);
        } else if (value instanceof Boolean) {
            buffer.put((byte) ((Boolean) value ? 1 : 0));
        }
        bs = new byte[buffer.remaining()];
        buffer.get(bs);
        store.getIndexes()[16].putFile(archive, file, bs);
    }

    static void packSprites(Store from, Store to) {
        for (int i = 1244; i < 1466; i++) {
            IndexedColorImageFile f = null;
            try {
                f = new IndexedColorImageFile(from, i, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            to.getIndexes()[8].putFile(i, 0, f.encodeFile());
        }
        to.getIndexes()[8].rewriteTable();
        to.getIndexes()[8].resetCachedFiles();
    }

    //4140 world
    //4141 worldhover
    //4142 music
    //4143 musichover

    static void packExternalSprite(Store to) {
        int id = 3100;
        IndexedColorImageFile f = null;
        try {
            f = new IndexedColorImageFile(ImageIO.read(new File("C:\\Users\\Chris\\Desktop\\Zaros\\loginscreen/world.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = f.encodeFile();
        to.getIndexes()[8].putFile(id, 0, data);
        to.getIndexes()[8].rewriteTable();
        to.getIndexes()[8].resetCachedFiles();
    }

    static void packDonatorIcons(Store to) {
        int id = 423;
        File[] files = new File("donator_icons").listFiles();
        IndexedColorImageFile f = null;
        int index = 0;
        for (File file : files) {
            try {
                f = new IndexedColorImageFile(to, id, 0);
                BufferedImage icon = ImageIO.read(file);
                if (index == 0) {
                    f.replaceImage(icon, 3);
                    System.out.println("Replaced icon - " + 3);
                } else {
                    System.out.println("Added icon: " + f.addImage(icon, 0, 1) + ".");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            to.getIndexes()[8].putFile(id, 0, f.encodeFile());
            index++;
        }
    }

    public static void packInterface(Store from, Store to) {
        for (int id = 149; id <= 149; id++) {
            boolean packInterface = to.getIndexes()[3].putArchive(id, from);
            System.out.println("Packed interface: " + packInterface);
        }
    }

    private static int[] avoid = new int[]{298, 317, 318, 498, 499, 500, 4139, 4142, 4143, 4144};

    //468 473 497
    public static void packSprite(Store from, Store to) {
        for (int i = 470; i <= 470; i++) {
            boolean skip = false;
            for (int k : avoid)
                if (i == k)
                    skip = true;
            if (skip)
                continue;
            boolean packInterface = to.getIndexes()[8].putArchive(i, from);
            System.out.println("Packed sprite: " + packInterface);
        }
    }

    public static void packSpriteIndex(Store from, Store to) {
        boolean packInterface = to.getIndexes()[8].packIndex(from);
        System.out.println("Packed sprite: " + packInterface);
    }

    private static int[] scripts = new int[]{1364, 181, 183, 178, 179, 185, 84, 193, 90, 85, 72, 89, 1337, 1741, 42, 1301, 31, 37, 805, 807, 736};

    public static void packScript(Store from, Store to) {
        for (int i = 0; i <= 10000; i++) {
            boolean packScript = to.getIndexes()[12].putArchive(i, from);
            System.out.println("Packed script: " + packScript);
        }
    }

    public static void packScriptMaps(Store from, Store to) {
//        for(int i = 0; i < 2600; i++) {
//            byte[] data = from.getIndexes()[17].getFile(i >>> 8, i & 0xff);
//            if(data == null)
//                continue;
//            boolean packScript = to.getIndexes()[17].putFile(i >>> 8, i & 0xff, data);
//            System.out.println("Packed scriptMap: " + packScript);
//        }
        boolean b = to.getIndexes()[17].packIndex(from);
        System.out.println(b);
        // to.getIndexes()[17].resetCachedFiles();
    }

    static void packConfigs(Store from, Store to) {
        boolean packStructs = to.getIndexes()[2].putArchive(26, from);
        System.out.println("Packed structs: " + packStructs);
        boolean packParams = to.getIndexes()[2].putArchive(11, from);
        System.out.println("Packed params: "+packParams);
        boolean packVarp = to.getIndexes()[2].putArchive(16, from);
        System.out.println("Packed varp: "+packVarp);
    }

    static void packAnimations(Store from, Store to) {
        boolean packSEq = to.getIndexes()[20].packIndex(from);
        System.out.println("Packed skins: " + packSEq);
    }

    static void packFonts(Store from, Store to) {
        boolean packSEq = to.getIndexes()[13].packIndex(from);
        System.out.println("Packed skins: " + packSEq);
    }

    static void packQuickChats(Store from, Store to) {
        boolean pack24 = to.getIndexes()[24].packIndex(from);
        System.out.println("Packed skins: " + pack24);
        boolean pack25 = to.getIndexes()[25].packIndex(from);
        System.out.println("Packed skins: " + pack25);
    }

    static void packMaterials(Store from, Store to) {
        boolean pack26 = to.getIndexes()[26].packIndex(from);
        System.out.println("Packed materials: " + pack26);
    }

    public static void packSkins(Store from, Store to) {
        /*for(int x = 0; x <= from.getIndexes()[0].getLastArchiveId(); x++) {
            if(to.getIndexes()[0].archiveExists(x)) {
                continue;
            }
            to.getIndexes()[0].putArchive(x, from);
            System.out.println(x);
        }
        for(int x = 0; x <= from.getIndexes()[1].getLastArchiveId(); x++) {
            if(to.getIndexes()[1].archiveExists(x)) {
                continue;
            }
            to.getIndexes()[1].putArchive(x, from);
            System.out.println(x);
        }
        for(int x = 0; x <= from.getIndexes()[20].getLastArchiveId(); x++) {
            if(to.getIndexes()[20].archiveExists(x) && to.getIndexes()[20].getLastFileId(x) == from.getIndexes()[20].getLastFileId(x)) {
                continue;
            }
            for(int y = 0; y <= from.getIndexes()[20].getLastFileId(x); y++) {
                to.getIndexes()[20].putFile(x, y, from.getIndexes()[20].getFile(x, y));
            }
            System.out.println(x);
        }
        to.getIndexes()[20].rewriteTable();
        to.getIndexes()[20].resetCachedFiles();*/

        boolean packFrame = to.getIndexes()[0].packIndex(from);
        System.out.println("Packed frames: " + packFrame);
        boolean packSkin = to.getIndexes()[1].packIndex(from);
        System.out.println("Packed skins: " + packSkin);
        boolean packSeq = to.getIndexes()[20].packIndex(from);
        System.out.println("Packed seq: " + packSeq);
        /*boolean packFrames = to.getIndexes()[0].putArchive(2363, from);
        System.out.println("Packed skins: "+packFrames);
        boolean packSkins1 = to.getIndexes()[1].putArchive(2123, from);
        System.out.println("Packed skins1: "+packSkins1);
        boolean packSeq = to.getIndexes()[20].putFile(9867 >>> 7, 9867 & 0x7f, from.getIndexes()[20].getFile(9867 >>> 7, 9867 & 0x7f));
        System.out.println("Packed seq: "+packSeq);
        to.getIndexes()[20].rewriteTable();
        to.getIndexes()[20].resetCachedFiles();*/
        //SKINS WORK!
        /*int newId = 0;
        int fromId = 0;
        for (int a = 0; a <= from.getIndexes()[fromId].getLastArchiveId(); a++) {
            for(int)
            byte[] data = from.getIndexes()[fromId].getFile(a);
            if (data == null)
                continue;
            if (!selfStore.put(a, data, data.getBuffer().length))
                System.err.println("Couldn't not put folder - " + a + ".");
        }
        if (!Application.getCache().getInformationStore().put(newId, fs, fs.getBuffer().length)) {
            System.err.println("Couldn't update fs - " + newId);
        }
        newId = 1;
        fromId = 1;
        for (int a = 0; a <= from.getIndexes()[fromId].getLastArchiveId(); a++) {
            ByteBuffer data = targetStore.get(a);
            if (data == null)
                continue;
            if (!selfStore.put(a, data, data.getBuffer().length))
                System.err.println("Couldn't not put folder - " + a + ".");
        }
        if (!Application.getCache().getInformationStore().put(newId, fs, fs.getBuffer().length)) {
            System.err.println("Couldn't update fs - " + newId);
        }*/
        System.out.println("Skeletons & Skins packed successfully.");
    }

//	static void packAnimations(Store from, Store to) {
//		boolean packseq = false;
//		if(packseq) {
//			for (int i = 0; i < 15147; i++) {
//				byte[] a = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros/639anims/" + i + ".dat");
//				;
//				if (a == null) {
//					continue;
//				}
//				//			i = 10222;//from.getIndexes()[20].getLastArchiveId() + 1;
//				System.out.println("Packed animation " + i + " - " + to.getIndexes()[20].putFile(i >>> 7, i & 0x7F, a));
//			}
//			to.getIndexes()[20].rewriteTable();
//			to.getIndexes()[20].resetCachedFiles();
//		}
//
//		for(int i = 0; i <= from.getIndexes()[0].getLastArchiveId(); i++) {
//			for(int y = 0; y <= from.getIndexes()[0].getLastFileId(i); y++) {
//				byte[] data = from.getIndexes()[0].getFile(i, y);
//				if (data == null) {
//					continue;
//				}
//				System.out.println("Packed frame " + i + ", " + y + " - " + to.getIndexes()[0].putFile(i, y, data));
//			}
//		}
//
//		to.getIndexes()[0].rewriteTable();
//		to.getIndexes()[0].resetCachedFiles();
//
//		for(int i = 0; i <= from.getIndexes()[1].getLastArchiveId(); i++) {
//			for(int y = 0; y <= from.getIndexes()[1].getLastFileId(i); y++) {
//				byte[] data = from.getIndexes()[1].getFile(i, y);
//				if (data == null) {
//					continue;
//				}
//				System.out.println("Packed skin " + i + ", " + y + " - " + to.getIndexes()[1].putFile(i, y, data));
//			}
//		}
//
//		to.getIndexes()[1].rewriteTable();
//		to.getIndexes()[1].resetCachedFiles();
//
//
//		//to.getIndexes()[0].packIndex(from);
//		//to.getIndexes()[0].rewriteTable();
//		//to.getIndexes()[0].resetCachedFiles();
//
//		/*for(int frame = 0; frame < 3781; frame++) {
//			int archiveTo = frame >>> 8;
//			//int archiveFrom = id >>> -1035933944;
//			int file = frame & 0xFF;
//			byte[] bs = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros\\667frames/" + frame +".dat");;
//			if(bs == null)
//				continue;
//			boolean succesful = to.getIndexes()[0].putFile(archiveTo, file, bs);
//			if(!succesful)
//				System.out.println("failed: "+frame);
//			else {
//				System.out.println("Packed: "+frame);
//			}
//		}*/
//
//		/*for(int archive = 0; archive < 3106; archive++) {
//			byte[] a = from.getIndexes()[1].getFile(archive);
//			if (a == null) {
//				continue;
//			}
//			System.out.println("Packed skin " + archive + " - " + to.getIndexes()[1].putFile(archive, 0, a));
//		}
//		to.getIndexes()[1].rewriteTable();
//		to.getIndexes()[1].resetCachedFiles();*/
//
//	}

    public static int packAnimation(Store from, Store to, int animation) {
        AnimationDefinition def = AnimationDefinition.forId(from, animation, false);
        if (def == null) {
            return animation;
        }
        byte[] data = from.getIndexes()[20].getFile(animation >>> 7, animation & 0x7F);// >>> 7, animation & 0x7F);
        if (data == null) {
            System.out.println("No data for animation " + animation);
            return animation;
        }
        packAnimationFrames(from, to, def); //Has to be done before def.requiresPacking is called!
        System.out.println("Packed animation " + animation);
        to.getIndexes()[20].putFile(animation >>> 7, animation & 0x7F, (def.requiresPacking || def.os) ? def.pack() : data, -1);
        return animation;
    }

    static int[] skinReference = new int[Short.MAX_VALUE];
    static int[] frameReference = new int[Short.MAX_VALUE];
    static int skinOffset = 0;
    static int frameOffset = 0;

    private static void packAnimationFrames(Store from, Store to, AnimationDefinition def) {
        if (def.anIntArray2139 == null) {
            return;
        }
        boolean reset = false;
        for (int i = 0; i < def.anIntArray2139.length; i++) {
            int skin = def.anIntArray2139[i] >> 16;
            if (skinReference[skin] > 0) {
                if (skinReference[skin] != skin) {
                    def.anIntArray2139[i] -= skin << 16;
                    def.anIntArray2139[i] += skinReference[skin] << 16;
                    def.requiresPacking = true;
                }
                continue;
            }
            if (!from.getIndexes()[0].archiveExists(skin)) {
                skinReference[skin] = skin;
                System.err.println("No data found for skin " + skin + " (anim: " + def.id + ")!");
                continue;
            }
            int[] fileIds = from.getIndexes()[0].getTable().getArchives()[skin].getValidFileIds();
            boolean pack = !to.getIndexes()[0].archiveExists(skin);
            int newSkin = skin;
            if (!pack && (pack = !Arrays.equals(fileIds, to.getIndexes()[0].getTable().getArchives()[skin].getValidFileIds()))) {
                if (skinOffset == 0) {
                    skinOffset = to.getIndexes()[0].getValidArchivesCount();
                }
                newSkin = skinOffset++;
            }
            if (pack) {
                if (frameOffset == 0) {
                    frameOffset = to.getIndexes()[1].getValidArchivesCount();
                }
                for (int j = 0; j < fileIds.length; j++) {
                    byte[] bs = from.getIndexes()[0].getFile(skin, fileIds[j]);
                    int frameId = (255 & bs[0]) << 8 | bs[1] & 255;
                    int newId = frameId;
                    if (frameReference[frameId] != 0) {
                        newId = frameReference[frameId];
                    } else {
                        boolean pack1 = to.getIndexes()[1].getFile(frameId, 0) == null;
                        byte[] bs1 = from.getIndexes()[1].getFile(frameId, 0);
                        if (!pack1 && (pack1 = !Arrays.equals(bs1, to.getIndexes()[1].getFile(frameId, 0)))) {
                            newId = frameOffset++;
                        }
                        if (pack1) {
                            frameReference[frameId] = newId;
                            to.getIndexes()[1].putFile(newId, 0, bs1);
                            System.out.println("Packed new frame " + frameId + " => " + newId);
                        }
                    }
                    if (newId != frameId) {
                        bs[0] = (byte) (newId >> 8);
                        bs[1] = (byte) (newId & 0xFF);
                    }
                    to.getIndexes()[0].putFile(newSkin, fileIds[j], Constants.GZIP_COMPRESSION, bs, null, false, false, -1, -1);
                    reset = true;
                }
            }
            skinReference[skin] = newSkin;
            def.anIntArray2139[i] -= skin << 16;
            def.anIntArray2139[i] += skinReference[skin] << 16;
            def.requiresPacking = pack;
        }
        if (reset) {
            to.getIndexes()[0].rewriteTable();
            to.getIndexes()[0].resetCachedFiles();
        }
    }

    static void packTextures(Store from, Store to) {
        boolean pack9 = to.getIndexes()[9].packIndex(from);
        System.out.println("Packed textures: " + pack9);
    }

    static void packVarbit(Store from, Store to) {
        for (int i = 0; i < varbitSize(from); i++) {
            byte[] bs = from.getIndexes()[22].getFile(i >>> 10, 0x3ff & i);
            if (bs == null || bs.length < 1) {
                System.out.println("Missing varbit id " + i);
                continue;
            }
            System.out.println("Packing varbit id " + i + ": " + to.getIndexes()[22].putFile(i >>> 10, 0x3ff & i, bs));//+ (i < 200 ? Arrays.toString(bs) : null));//to.getIndexes()[6].putFile(0, i, bs));
        }
        to.getIndexes()[22].rewriteTable();
        to.getIndexes()[22].resetCachedFiles();
    }

    public static final int varbitSize(Store store) {
        int lastArchiveId = store.getIndexes()[22].getLastArchiveId();
        return lastArchiveId * 1024 + store.getIndexes()[22].getValidFilesCount(lastArchiveId);
    }

    static void packGFX(Store from, Store to) {
        for (int i = 0; i < 3100; i++) {
            byte[] bs = from.getIndexes()[21].getFile(i >>> 8, 0xff & i);
            if (bs == null || bs.length < 1) {
                System.out.println("Missing gfx id " + i);
                continue;
            }
            System.out.println("Packing gfx id " + i + ": " + to.getIndexes()[21].putFile(i >>> 8, 0xff & i, bs));
        }
        to.getIndexes()[21].rewriteTable();
        to.getIndexes()[21].resetCachedFiles();
    }

    /**
     * Packs the overlays.
     *
     * @param from The cache to get the data from.
     * @param to   The cache to store the data.
     */
    static void packOverlays(Store from, Store to) {
        System.out.println("Start");
        //		int changeOverlay = 135;
        //		int newOverlay = 135;
        //		System.out.println("Success = " + to.getIndexes()[2].putFile(4, changeOverlay, from.getIndexes()[2].getFile(4, newOverlay)));
        for (int id = 0; id < from.getIndexes()[2].getValidFilesCount(4); id++) {
            byte[] bs = from.getIndexes()[2].getFile(4, id);
            if (bs == null || bs.length < 1) {
                continue;
            }
            boolean success = to.getIndexes()[2].putFile(4, id, from.getIndexes()[2].getFile(4, id));
            System.out.println("Packed overlay definition " + id + " - success=" + success);
        }
        to.getIndexes()[2].rewriteTable();
        to.getIndexes()[2].resetCachedFiles();
    }

    static void packUnderlays(Store from, Store to) {
        System.out.println("Start");
        //		int changeOverlay = 135;
        //		int newOverlay = 135;
        //		System.out.println("Success = " + to.getIndexes()[2].putFile(4, changeOverlay, from.getIndexes()[2].getFile(4, newOverlay)));
        for (int id = 0; id < from.getIndexes()[2].getValidFilesCount(1); id++) {
            byte[] bs = from.getIndexes()[2].getFile(1, id);
            if (bs == null || bs.length < 1) {
                continue;
            }
            boolean success = to.getIndexes()[2].putFile(1, id, from.getIndexes()[2].getFile(1, id));
            System.out.println("Packed overlay definition " + id + " - success=" + success);
        }
        to.getIndexes()[2].rewriteTable();
        to.getIndexes()[2].resetCachedFiles();
    }

    private int[] niggerModels = {16919, 16920, 16921, 16922};

    static void packModels(Store from, Store to) {
        //System.out.println(from.getIndexes()[7].getLastArchiveId());
        //System.out.println(to.getIndexes()[7].getValidArchivesCount());
        //for (int model = 0; model < from.getIndexes()[7].getLastArchiveId() - 1; model++) {
        for (int model = 65330; model < 75000; model++) {
            byte[] a = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros\\Data\\718models/" + model + ".dat");
            ;
            if (a == null) {
                continue;
            }
            //System.out.println(Arrays.toString(a) + "");
            to.getIndexes()[7].putFile(model, 0, a);
            System.out.println(model);
        }
        to.getIndexes()[7].rewriteTable();
        to.getIndexes()[7].resetCachedFiles();
    }

    static void pack718Models(Store from, Store to) {
        //System.out.println(from.getIndexes()[7].getLastArchiveId());
        //System.out.println(to.getIndexes()[7].getValidArchivesCount());
        //for (int model = 0; model < from.getIndexes()[7].getLastArchiveId() - 1; model++) {
        for (int model = 0; model < 75000; model++) {
            byte[] a = ReadFile("C:\\Users\\Chris\\Desktop\\Zaros\\Data\\718models/" + model + ".dat");
            ;
            if (a == null) {
                continue;
            }
            //System.out.println(Arrays.toString(a) + "");
            to.getIndexes()[27].putFile(model, 0, a);
            System.out.println(model);
        }
        to.getIndexes()[27].rewriteTable();
        to.getIndexes()[27].resetCachedFiles();
    }

    static void packCs2Map(Store from, Store to) {
        to.getIndexes()[17].putFile(2, 168, from.getIndexes()[17].getFile(2, 168));
        to.getIndexes()[17].rewriteTable();
        to.getIndexes()[17].resetCachedFiles();
    }

    static void packMusic(Store from, Store to) throws Throwable {
        for (int i = 0; i < to.getIndexes()[6].getValidArchivesCount(); i++) {
            byte[] bs = to.getIndexes()[6].getFile(i);
            if (bs == null || bs.length < 1) {
                continue;
            }
            System.out.println("Packing music id " + i + ": ");// + to.getIndexes()[6].putArchive(i, from));//.putArchive(2, , from));
        }
    }
}