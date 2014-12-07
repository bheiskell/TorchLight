package org.xdxa.torchlight;

import static org.junit.Assert.assertEquals;

import org.bukkit.Material;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class LightConfigurationTest {

    @Test
    public void testSerialization() {
        final LightConfiguration input = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                Material.TORCH,
                Material.GOLD_HELMET,
                Material.GOLD_BOOTS);

        final LightConfiguration output = LightConfiguration.deserialize(input.serialize());

        assertEquals(input.getLightBlock(), output.getLightBlock());
        assertEquals(input.getLightMeta(), output.getLightMeta());
        assertEquals(input.getItem(), output.getItem());
        assertEquals(input.getHelmet(), output.getHelmet());
        assertEquals(input.getBoot(), output.getBoot());
    }

    @Test
    public void testGetLightBlock() {
        final LightConfiguration lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                null,
                null,
                null);

        assertEquals(Material.GLOWSTONE, lightConfiguration.getLightBlock());
    }

    @Test(expected = NullPointerException.class)
    public void testGetLightBlockException() {
        final LightConfiguration lightConfiguration = new LightConfiguration(
                null,
                null,
                null,
                null,
                null);

        assertEquals(Material.GLOWSTONE, lightConfiguration.getLightBlock());
    }

    @Test
    public void testGetLightMeta() {
        LightConfiguration lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                (byte)1,
                null,
                null,
                null);

        assertEquals(Byte.valueOf((byte)1), lightConfiguration.getLightMeta());

        lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                null,
                null,
                null);

        assertEquals(Byte.valueOf((byte)0), lightConfiguration.getLightMeta());
    }

    @Test
    public void testGetItem() {
        LightConfiguration lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                null,
                null,
                null);

        assertEquals(null, lightConfiguration.getItem());

        lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                Material.TORCH,
                null,
                null);

        assertEquals(Material.TORCH, lightConfiguration.getItem());
    }

    @Test
    public void testGetHelmet() {
        LightConfiguration lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                null,
                null,
                null);

        assertEquals(null, lightConfiguration.getHelmet());

        lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                null,
                Material.GOLD_HELMET,
                null);

        assertEquals(Material.GOLD_HELMET, lightConfiguration.getHelmet());
    }

    @Test
    public void testGetBoot() {
        LightConfiguration lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                null,
                null,
                null);

        assertEquals(null, lightConfiguration.getBoot());

        lightConfiguration = new LightConfiguration(
                Material.GLOWSTONE,
                null,
                null,
                null,
                Material.GOLD_BOOTS);

        assertEquals(Material.GOLD_BOOTS, lightConfiguration.getBoot());
    }

}
