These are the Java class files for the Farm World game for CS 2340.

Main.java: this is the main class that launches the javaFX game

Player.java: this is the player object that stores:
  - name
<<<<<<< HEAD
  - Inventory

Inventory.java: this is the inventory object that stores:
  - seeds { <Seed> : <quantity> }
  - crops { <Crop> : <quantity> }
  - capacity

FarmWorldConfigurations.java: this is the configurations object that stores:
  - difficulty
  - item
  - season
  - Seed enum
    - Wheat
    - Melon
    - Pumpkin
    - Potato
  - getStartingMoney()

Farm.java: this is the farm object
  - incrementDay()
  - getMoney()
  - setMoney()
  - getDay()

Market.java: this is the market object
  -
  - getPrice()
  - getSeeds()
  - sellItem()
  - buyItem()

Marketable.java: this is an interface for marketable items
  - getPrice()

Seed.java: this is an enum of Marketable seeds
  - Melon $20
  - Potato $5
  - Pumpkin $15
  - Wheat $10
  - getPrice()  ** Override **

[Milestone]JUnit.java
  - JUnit tests for given Milestone number
=======
FarmWorldConfigurations.java: this is the configurations object that stores:
  - difficulty
  - seed
  - season
>>>>>>> Amelia
