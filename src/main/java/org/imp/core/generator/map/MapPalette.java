// 
// Decompiled by Procyon v0.5.36
// 

package org.imp.core.generator.map;

import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.awt.Color;

public final class MapPalette
{
    private static final ThreadLocal<int[]> rgbBuffer;
    public static final Color[] colors;
    private static final Map<Integer, Byte> colorToIndexMap;
    
    public static void prepareColors() {
        for (int i = 4; i < MapPalette.colors.length; ++i) {
            Color color = MapPalette.colors[i];
            byte index = (byte)((i < 128) ? i : (-129 + (i - 127)));
            MapPalette.colorToIndexMap.put(color.getRGB(), index);
        }
    }
    
    private static Color c(final int r, final int g, final int b) {
        return new Color(r, g, b);
    }
    
    private static double getDistance(final Color c1, final Color c2) {
        double rmean = (c1.getRed() + c2.getRed()) / 2.0;
        double r = c1.getRed() - c2.getRed();
        double g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        double weightR = 2.0 + rmean / 256.0;
        double weightG = 4.0;
        double weightB = 2.0 + (255.0 - rmean) / 256.0;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }
    
    @Deprecated
    public static int[] imageToBytes(final BufferedImage image) {
        int[] result = MapPalette.rgbBuffer.get();
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), result, 0, image.getWidth());
        for (int i = 0; i < result.length; ++i) {
            result[i] = tryFastMatchColor(result[i]);
        }
        return result;
    }
    
    public static byte tryFastMatchColor(final int rgb) {
        Byte color = MapPalette.colorToIndexMap.get(rgb);
        if (color != null) {
            return color;
        }
        return matchColor(new Color(rgb, true));
    }
    
    @Deprecated
    public static byte matchColor(final Color color) {
        if (color.getAlpha() < 128) {
            return 0;
        }
        int index = 0;
        double best = -1.0;
        for (int i = 4; i < MapPalette.colors.length; ++i) {
            final double distance = getDistance(color, MapPalette.colors[i]);
            if (distance < best || best == -1.0) {
                best = distance;
                index = i;
            }
        }
        return (byte)((index < 128) ? index : (-129 + (index - 127)));
    }
    
    static {
        rgbBuffer = ThreadLocal.withInitial(() -> new int[16384]);
        colors = new Color[] { c(0, 0, 0), c(0, 0, 0), c(0, 0, 0), c(0, 0, 0), c(89, 125, 39), c(109, 153, 48), c(127, 178, 56), c(67, 94, 29), c(174, 164, 115), c(213, 201, 140), c(247, 233, 163), c(130, 123, 86), c(140, 140, 140), c(171, 171, 171), c(199, 199, 199), c(105, 105, 105), c(180, 0, 0), c(220, 0, 0), c(255, 0, 0), c(135, 0, 0), c(112, 112, 180), c(138, 138, 220), c(160, 160, 255), c(84, 84, 135), c(117, 117, 117), c(144, 144, 144), c(167, 167, 167), c(88, 88, 88), c(0, 87, 0), c(0, 106, 0), c(0, 124, 0), c(0, 65, 0), c(180, 180, 180), c(220, 220, 220), c(255, 255, 255), c(135, 135, 135), c(115, 118, 129), c(141, 144, 158), c(164, 168, 184), c(86, 88, 97), c(106, 76, 54), c(130, 94, 66), c(151, 109, 77), c(79, 57, 40), c(79, 79, 79), c(96, 96, 96), c(112, 112, 112), c(59, 59, 59), c(45, 45, 180), c(55, 55, 220), c(64, 64, 255), c(33, 33, 135), c(100, 84, 50), c(123, 102, 62), c(143, 119, 72), c(75, 63, 38), c(180, 177, 172), c(220, 217, 211), c(255, 252, 245), c(135, 133, 129), c(152, 89, 36), c(186, 109, 44), c(216, 127, 51), c(114, 67, 27), c(125, 53, 152), c(153, 65, 186), c(178, 76, 216), c(94, 40, 114), c(72, 108, 152), c(88, 132, 186), c(102, 153, 216), c(54, 81, 114), c(161, 161, 36), c(197, 197, 44), c(229, 229, 51), c(121, 121, 27), c(89, 144, 17), c(109, 176, 21), c(127, 204, 25), c(67, 108, 13), c(170, 89, 116), c(208, 109, 142), c(242, 127, 165), c(128, 67, 87), c(53, 53, 53), c(65, 65, 65), c(76, 76, 76), c(40, 40, 40), c(108, 108, 108), c(132, 132, 132), c(153, 153, 153), c(81, 81, 81), c(53, 89, 108), c(65, 109, 132), c(76, 127, 153), c(40, 67, 81), c(89, 44, 125), c(109, 54, 153), c(127, 63, 178), c(67, 33, 94), c(36, 53, 125), c(44, 65, 153), c(51, 76, 178), c(27, 40, 94), c(72, 53, 36), c(88, 65, 44), c(102, 76, 51), c(54, 40, 27), c(72, 89, 36), c(88, 109, 44), c(102, 127, 51), c(54, 67, 27), c(108, 36, 36), c(132, 44, 44), c(153, 51, 51), c(81, 27, 27), c(17, 17, 17), c(21, 21, 21), c(25, 25, 25), c(13, 13, 13), c(176, 168, 54), c(215, 205, 66), c(250, 238, 77), c(132, 126, 40), c(64, 154, 150), c(79, 188, 183), c(92, 219, 213), c(48, 115, 112), c(52, 90, 180), c(63, 110, 220), c(74, 128, 255), c(39, 67, 135), c(0, 153, 40), c(0, 187, 50), c(0, 217, 58), c(0, 114, 30), c(91, 60, 34), c(111, 74, 42), c(129, 86, 49), c(68, 45, 25), c(79, 1, 0), c(96, 1, 0), c(112, 2, 0), c(59, 1, 0) };
        colorToIndexMap = new HashMap<Integer, Byte>();
    }
}
