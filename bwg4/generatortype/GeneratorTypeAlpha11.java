package bwg4.generatortype;

import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import bwg4.api.BiomeList;
import bwg4.gui.GuiGeneratorSettings;
import bwg4.gui.GuiSettingsButton;
import bwg4.world.ProviderBWG4;
import bwg4.world.generators.ChunkGeneratorAlpha;
import bwg4.world.generators.ChunkGeneratorInfdev;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class GeneratorTypeAlpha11 extends GeneratorType
{
	public GeneratorTypeAlpha11(int id, int cat, String name, boolean c) 
	{
		super(id, cat, name, c);
	}
	
	@Override
	public boolean getSettings(GuiGeneratorSettings gui)
	{
		gui.settings.add(new GuiSettingsButton(new String[]{StatCollector.translateToLocal("bwg4.setting.snow") + ": " + StatCollector.translateToLocal("bwg4.setting.off"), StatCollector.translateToLocal("bwg4.setting.snow") + ": " + StatCollector.translateToLocal("bwg4.setting.on")}, new int[]{0, 1}, 20, 80, gui.width));
		return true;
	}
	
	@Override
	public WorldChunkManager getServerWorldChunkManager(ProviderBWG4 provider, World worldObj)
    {
		if(provider.trySetting(0, 1) == 0) 
		{ 
			return new WorldChunkManagerHell(BiomeList.CLASSICnormal, 0.5F); 
		}
		return new WorldChunkManagerHell(BiomeList.CLASSICsnow, 0.5F); 
    }

	@Override
	public WorldChunkManager getClientWorldChunkManager(ProviderBWG4 provider)
    {
		return new WorldChunkManagerHell(BiomeList.CLASSICnormal, 0.5F);
    }

	@Override
    public IChunkProvider getChunkGenerator(ProviderBWG4 provider, World worldObj)
    {	
		return new ChunkGeneratorInfdev(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), true);
    }

	@Override
    public boolean getRandSpawn(ProviderBWG4 provider)
    {
    	return true;
    }

	@Override
    public float getCalculateCelestialAngle(ProviderBWG4 provider, long par1, float par3)
    {
    	return 0F;
    }

	@Override
    public boolean isSurfaceWorld(ProviderBWG4 provider)
    {
    	return true;
    }

	@Override
    public Vec3 getFogColor(ProviderBWG4 provider, World worldObj, float par1, float par2)
    {
    	return null;
    }

	@Override
    public float getCloudHeight(ProviderBWG4 provider)
    {
    	return 128F;
    }

	@Override
    public int getAverageGroundLevel(ProviderBWG4 provider)
    {
    	return 64;
    }

	@Override
    public double getHorizon(ProviderBWG4 provider)
    {
    	return 64D;
    }

	@Override
    public boolean getWorldHasVoidParticles(ProviderBWG4 provider)
    {
    	return true;
    }
}
