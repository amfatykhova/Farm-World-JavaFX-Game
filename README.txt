These are the Java class files for the Farm World game for CS 2340.

Main.java: this is the main class that launches the javaFX game

Player.java: this is the player object that stores:
  - name
  - Inventory

Inventory.java: this is the inventory object that stores:
  - Dictionary { crops : seeds }
  - capacity

FarmWorldConfigurations.java: this is the configurations object that stores:
  - difficulty
  - seed
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
  - getPrice()
  - getSeeds()
  - sell()
  - buy()

[Milestone]JUnit.java
  - JUnit tests for given Milestone number
