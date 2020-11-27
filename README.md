Farm World Game Demo Video

[![Watch the video](https://img.youtube.com/vi/uFaKm_-V4ys/maxresdefault.jpg)](https://youtu.be/uFaKm_-V4ys)

These are the Java class files for the Farm World game for CS 2340.

Main.java: this is the main class that launches the javaFX game
  - harvest mechanism
  - market place mechanism
  - inventory opened
  - all UI elements created
      Methods:
        - start()
        - startGame()
        - configFarmUI()
        - openInventory()
        - configOptionsScreen()
        - configFarmScreen()
        - goToGameOverScreen()
        - setUpPlots()
        - setPlantingListeners()
        - plantAction()
        - setUpMarket()
        - updatePlots()
        - refreshBox()
        - main()
  
Player.java: this is the player object that stores:
  - String name
  - Inventory inventory
  - FarmWorldConfigurations.Difficulty difficulty
  - int balance
  - int day
  - int plotHarvestLimit
  - int plotsHarvestedToday
  - int plotWateringLimit
  - int plotsWateredToday
      Methods:
        - Player()
        - init()
        - sellItem()
        - buyItem()
        - incrementDay()
        - getRandomEvent()
        - getName()
        - getInventory()
        - getBalance()
        - getDay()
        - setName()
        - getPlotsHarvestedToday()
        - getPlotHarvestLimit()
        - incrementPlotsHarvested()
        - getPlotsWateredToday()
        - getPlotWateringLimit()
        - incrementPlotsWatered()
        - getDifficulty()
        
FarmWorldConfigurations.java: this is the configurations object that stores:
  - Difficulty difficulty (enum)
    - EASY(1.0)
    - MEDIUM(0.75)
    - HARD(0.5)
  - String seed
  - String season
      Methods:
        - FarmWorldConfigurations()
        - getDifficulty()
        - setDifficulty()
        - getSeed()
        - setSeed()
        - getSeason()
        - setSeason()
        - getStartingMoney()
        - getStartingItems()
        
Farm.java: this is the farm object
  - int money
  - int day
      Methods:
        - Farm()
        - incrementDay()
        - setMoney()
        - getMoney()
        - getDay()

Inventory.java: this is the inventory object
  - Map<Item, Integer> items
  - int capacity
  - int size
      Methods:
        - Inventory()
        - remove()
        - add()
        - getItemMap()
        - getCapacity()
        - getSize()
        
Item.java: this is the item enum (implements Marketable)
  - MELON
  - POTATO
  - PUMPKIN
  - WHEAT
  - MELON_PESTICIDES
  - POTATO_PESTICIDES
  - PUMPIN_PESTICIDES
  - WHEAT_PESTICIDES
  - PESTICIDE
  - FERTILIZER
  - TRACTOR
  - IRRIGATION
  - PLOTS
  - int basePrice
  - int startingQuantity
      Methods:
        - Item()
        - getDisplayName()
        - toConcat()
        - getPrice()
        - getQuantity()
        - setPrice()
        
Market.java: this is the market object
  - Map<Item, Integer> prices
      Methods:
         - Market()
         - getItemMap()
         
Marketable.java: this is the Marketable interface
      Methods:
        - getPrice()
        - getQuantity()
        
Maturity.java: this is the maturity enum
  - EMPTY
  - SEED
  - SPROUT
  - IMMATURE
  - MATURE
  - DEAD
  - String order
      Methods:
        - Maturity()
        - getOrder()
        
MediaControler.java: this is the media controller class
  - MediaPlayer introPlayer
  - MediaPlayer buttonPlayer
  - MediaPlayer plantPlayer
  - MediaPlayer gameOverPlayer
  - MediaPlayer winPlayer
      Methods:
        - playIntro()
        - playClick()
        - playHarvest()
        - playPlant()
        - playGameOver()
        - playWin()
        - stopIntro()
        - stopGameOver()
        
Plot.java: this is the plot object
  - Item plant
  - Maturity maturity
  - Button button
  - int size
  - boolean hasPesticides
  - int minWater
  - int maxWater
  - int waterLevel
  - int minFertilizerLevel
  - int maxFertilizerLevel
  - int fertilizerLevel
      Methods:
        - Plot()
        - grow()
        - getButton()
        - getPlant()
        - setPlant()
        - getMaturity()
        - harvest()
        - plantSeed()
        - setTooltip()
        - waterPlot()
        - waterUp()
        - waterDown()
        - getWaterLevel()
        - waterLevelCheck()
        - kill()
        - applyPesticides()
        - removePesticides()
        - getPesticides()
        - getFertilizerLevel()
        - addFertilizer()
        - decrementFertilizerLevel()
        
