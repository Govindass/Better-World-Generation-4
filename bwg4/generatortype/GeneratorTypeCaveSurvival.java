package bwg4.generatortype;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import bwg4.api.BiomeList;
import bwg4.world.ProviderBWG4;
import bwg4.world.ProviderBWG4Hell;
import bwg4.world.generators.ChunkGeneratorCaveSurv;
import bwg4.world.generators.ChunkGeneratorSurvivalNether;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class GeneratorTypeCaveSurvival extends GeneratorType
{
	public GeneratorTypeCaveSurvival(int id, int cat, String name, boolean c) 
	{
		super(id, cat, name, c, true, true);
	}
	
	@Override
	public WorldChunkManager getServerWorldChunkManager(ProviderBWG4 provider, World worldObj)
    {
		provider.hasNoSky = true;
		return new WorldChunkManagerHell(BiomeList.COMMONnormal1, 0.5F);
    }

	@Override
	public WorldChunkManager getClientWorldChunkManager(ProviderBWG4 provider)
    {
		provider.hasNoSky = true;
		return new WorldChunkManagerHell(BiomeList.COMMONnormal1, 0.5F);
    }

	@Override
    public IChunkProvider getChunkGenerator(ProviderBWG4 provider, World worldObj)
    {	
    	return new ChunkGeneratorCaveSurv(worldObj, worldObj.getSeed());
    }

	@Override
    public boolean getRandSpawn(ProviderBWG4 provider)
    {
    	return true;
    }

	@Override
    public float getCalculateCelestialAngle(ProviderBWG4 provider, long par1, float par3)
    {
    	return 0.8f;
    }

	@Override
    public boolean isSurfaceWorld(ProviderBWG4 provider)
    {
    	return false;
    }

	@Override
    public Vec3 getFogColor(ProviderBWG4 provider, World worldObj, float par1, float par2)
    {
    	return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
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
	
	@Override
    public WorldChunkManager getHellChunkManager(ProviderBWG4Hell provider)
    {
    	return new WorldChunkManagerHell(BiomeList.COMMONnether, 0.0F);
    }
    
	@Override
    public IChunkProvider getHellChunkProvider(ProviderBWG4Hell provider)
    {
		return new ChunkGeneratorSurvivalNether(provider.worldObj, provider.worldObj.getSeed());
    }
}
