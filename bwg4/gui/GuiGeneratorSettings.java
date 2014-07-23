package bwg4.gui;

import java.util.ArrayList;

import bwg4.api.BiomeManager;
import bwg4.data.DecodeGeneratorString;
import bwg4.generatortype.GeneratorType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;


public class GuiGeneratorSettings extends GuiScreen
{
	private final GuiCreateWorld createWorldGui;
	
	public GuiButton BUTTON_DONE;
	public GuiButton BUTTON_CATEGORY;
	public GuiButton BUTTON_BIOMELIST;
	public GuiButton BUTTON_WORLDSETTINGS;
	
	public int CATEGORY = 0;
	public String[] categories;
	
	public int generatorSelected = -1;
	public ArrayList<GuiGeneratorButton> generators;
	public ArrayList<GuiSettingsButton> settings;

	public String biomestring;
	
	public boolean decodebool;
	public boolean setremember;
	public int[] rememberSettings;
	
	public boolean hasSettings = false;
	public boolean emptyCategory = false;
	
	public boolean credits = false;
	public int creditsY = 0;
	public String creditsText = "";
	
	public String[] translatedDrawStrings;
	
	public GuiGeneratorSettings(GuiCreateWorld gcw, String gs)
	{
    	createWorldGui = gcw;
    	decodebool = true;
    	
    	categories = new String[4];
    	categories[0] = StatCollector.translateToLocal("bwg4.category.enhanced");
    	categories[1] = StatCollector.translateToLocal("bwg4.category.old");
    	categories[2] = StatCollector.translateToLocal("bwg4.category.survival");
    	categories[3] = StatCollector.translateToLocal("bwg4.category.fun");

    	translatedDrawStrings = new String[4];
    	translatedDrawStrings[0] = StatCollector.translateToLocal("gui.selectcategory");
    	translatedDrawStrings[1] = StatCollector.translateToLocal("gui.comingsoon");
    	translatedDrawStrings[2] = StatCollector.translateToLocal("gui.selectGenerator");
    	translatedDrawStrings[3] = StatCollector.translateToLocal("gui.generatorSettings");
	}
	
	public void setCredits(String text, int y)
	{
		credits = true;
		creditsY = y;
		creditsText = text;
	}

	public void initGui()
	{
        buttonList.add(BUTTON_DONE = new GuiButton(0, width / 2 - 155, height - 24, 150, 20, StatCollector.translateToLocal("gui.done")));
        buttonList.add(new GuiButton(1, width / 2 + 5, height - 24, 150, 20, StatCollector.translateToLocal("gui.cancel")));
        buttonList.add(new GuiButton(3, width / 2 - 155, height - 48, 310, 20, StatCollector.translateToLocal("gui.copystring")));
		
		buttonList.add(BUTTON_CATEGORY = new GuiButton(2, width / 2 - 155, 40, 150, 20, categories[CATEGORY]));
		buttonList.add(BUTTON_BIOMELIST = new GuiButton(4, width / 2 + 5, 80, 150, 20, "Biome Settings"));
		buttonList.add(BUTTON_WORLDSETTINGS = new GuiButton(5, width / 2 + 5, 100, 150, 20, "World Settings"));
		BUTTON_WORLDSETTINGS.enabled = false;
		
		if(decodebool)
		{
			decodebool = false;
			decodeString(createWorldGui.field_146334_a);
		}
		else
		{
        	switchCategory();
        	for(int i = 0; i < generators.size(); i++)
        	{
        		generators.get(i).button.enabled = true;
        		if(generators.get(i).generatorID == generatorSelected)
        		{
            		generators.get(i).button.enabled = false;
        		}
        	}
			selectGenerator();
		}
	}

	protected void actionPerformed(GuiButton button)
	{
        if (button.id == 0) //DONE
        {
        	createWorldGui.field_146334_a = createString();
        	mc.displayGuiScreen(this.createWorldGui); 
        }
        else if (button.id == 1) //CANCEL
        {
        	mc.displayGuiScreen(this.createWorldGui);
        }
        else if (button.id == 2) //CATEGORY
        {
        	CATEGORY++;
        	if(CATEGORY >= categories.length)
        	{
        		CATEGORY = 0;
        	}
        	switchCategory();
    		generatorSelected = -1;
    		selectGenerator();
        }
        else if (button.id == 3) //COPY SETTINGS
        {
        	String copy = createString();
        	System.out.println(copy);
        	setClipboardString(copy);
        }
        else if (button.id == 4) //COSTUMIZE BIOME LIST
        {
        	mc.displayGuiScreen(new GuiBiomeSettings(mc, this, biomestring, fontRendererObj));
        	
        	setremember = true;
        	rememberSettings = new int[settings.size()];
			for(int s = 0; s < settings.size(); s++)
			{
				rememberSettings[s] = settings.get(s).valuearray[settings.get(s).selected];
			}
        }
        else if (button.id >= 10 && button.id < 20)
        {
        	for(int i = 0; i < generators.size(); i++)
        	{
        		generators.get(i).button.enabled = true;
        		if(generators.get(i).button.id == button.id)
        		{
            		generators.get(i).button.enabled = false;
        			generatorSelected = generators.get(i).generatorID;
        		}
        	}
    		selectGenerator();
        }
        else if (button.id >= 20 && button.id < 30)
        {
        	for(int i = 0; i < settings.size(); i++)
        	{
        		if(settings.get(i).button.id == button.id)
        		{
        			settings.get(i).click();
        		}
        	}
        	dependencies();
        }
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		
		//title
		String title = "Better World Generation 4";
		drawString(fontRendererObj, title, (int) Math.floor(width / 2) - (int) Math.floor(fontRendererObj.getStringWidth(title) / 2), 10, 16777215);
		
		//category
		drawString(fontRendererObj, translatedDrawStrings[0], width / 2 - 155 + 1, 30, 10526880);
		
    	String catpos = "(" + (CATEGORY + 1) + "/4)";
    	drawString(fontRendererObj, catpos, width / 2 - 5 - fontRendererObj.getStringWidth(catpos), 30, 10526880);
    	
    	if(emptyCategory)
    	{
    		drawString(fontRendererObj, translatedDrawStrings[1], width / 2 - 110 + 1, 110, 10526880);
    	}
    	else	
    	{
    		drawString(fontRendererObj, translatedDrawStrings[2], width / 2 - 155 + 1, 70, 10526880);
    	}

    	if(hasSettings)
    	{
    		drawString(fontRendererObj, translatedDrawStrings[3], width / 2 + 6, 70, 10526880);
    	}
    	
    	if(credits)
    	{
    		drawString(fontRendererObj, creditsText, width / 2 + 6, creditsY, 10526880);
    	}
		
		super.drawScreen(par1, par2, par3);
	}
	
	public void switchCategory()
	{
		credits = false;
		hasSettings = false;
		emptyCategory = false;
		BUTTON_CATEGORY.displayString = categories[CATEGORY];

		if(generators != null)
		{
			for(int i = 0; i < generators.size(); i++)
			{
				buttonList.remove(generators.get(i).button);
			}
		}
		
		generators = new ArrayList<GuiGeneratorButton>();
		int count = 0;
		for(int g = 0; g < GeneratorType.generatortypes.length; g++)
		{
			if(GeneratorType.generatortypes[g] != null)
			{
				if(GeneratorType.generatortypes[g].GetCategory() == CATEGORY && GeneratorType.generatortypes[g].CanBeCreated())
				{
					generators.add(new GuiGeneratorButton(StatCollector.translateToLocal("bwg4." + GeneratorType.generatortypes[g].GetName()), g, count + 10, 80 + (20 * count), width));
					buttonList.add(generators.get(generators.size() - 1).button);
					count++;
				}
			}
		}
		
		if(count == 0)
		{
			emptyCategory = true;
		}
	}
	
	public void dependencies()
	{
		for(int i = 0; i < settings.size(); i++)
		{
    		if(settings.get(i).dependencie > -1)
    		{
    			settings.get(i).button.visible = false;
    			for(int p = 0; p < settings.get(i).depvalues.length; p++)
    			{
    				if(settings.get(settings.get(i).dependencie - 20).selected == settings.get(i).depvalues[p])
    				{
    					settings.get(i).button.visible = true;
    				}
    			}
    		}
		}
	}
	
	public void selectGenerator()
	{
		credits = false;
		
		if(generatorSelected > -1)
		{
			BUTTON_DONE.enabled = true;
		}
		else
		{
			BUTTON_DONE.enabled = false;
		}
		
		if(settings != null)
		{
			for(int i = 0; i < settings.size(); i++)
			{
				buttonList.remove(settings.get(i).button);
			}
		}
		settings = new ArrayList<GuiSettingsButton>();

		BUTTON_BIOMELIST.visible = false;
		BUTTON_WORLDSETTINGS.visible = false;
		
		if(generatorSelected > -1)
		{
			hasSettings = GeneratorType.generatortypes[generatorSelected].getSettings(this);
		}

		for(int s = 0; s < settings.size(); s++)
		{
			buttonList.add(settings.get(s).button);
		}
		
		dependencies();
		
		if(setremember)
		{
			for(int rs = 0; rs < settings.size(); rs++)
			{
				settings.get(rs).setOldValue(rememberSettings[rs]);
			}
			setremember = false;
		}
	}
	
	public void decodeString(String decodestring)
	{
		String[] genstring = decodestring.split("#");
		String[] gensettings = new String[0];
		if(genstring.length > 1 && genstring[1].length() > 0)
		{
			gensettings = genstring[1].split("&");
		}
		else
		{
			gensettings = new String[0];
		}
		
		biomestring = genstring.length > 2 ? genstring[2] : BiomeManager.getDefaultString();
		
		int n = DecodeGeneratorString.getGeneratorIDFromName(genstring[0]);
		if(n > -1)
		{
			CATEGORY = GeneratorType.generatortypes[n].GetCategory();
			switchCategory();
			generatorSelected = n;
			
        	for(int i = 0; i < generators.size(); i++)
        	{
        		generators.get(i).button.enabled = true;
        		if(generators.get(i).generatorID == generatorSelected)
        		{
            		generators.get(i).button.enabled = false;
        		}
        	}
			selectGenerator();
			
			for(int i = 0; i < settings.size(); i++)
			{
				if(i < gensettings.length)
				{
					settings.get(i).setOldValue(Integer.parseInt(gensettings[i]));
				}
			}
		}
		else
		{
			switchCategory();
			generatorSelected = -1;
			selectGenerator();
		}
	}
	
	public String createString()
	{
		if(generatorSelected > -1 && generatorSelected < GeneratorType.generatortypes.length)
		{
			String genstring = GeneratorType.generatortypes[generatorSelected].GetName() + "#";
			for(int s = 0; s < settings.size(); s++)
			{
				genstring += s == 0 ? "" : "&";
				genstring += settings.get(s).valuearray[settings.get(s).selected];
			}
			
			if(generatorSelected == GeneratorType.DEFAULT.GetID())
			{
				genstring += "#" + biomestring;
			}
			
			return genstring;
		}
		else
		{
			return GeneratorType.DEFAULT.GetName() + "##" + BiomeManager.getDefaultString();
		}
	}
}