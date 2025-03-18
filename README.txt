Game name: Waritics
Type: RPG
Authors: Sergey Nersesyan, Mher Mkrtchyan, Armen Balagyozyan
Email: armen_balagyozyan@edu.aua.am

Class Characters -> some characters will be inharited from this class 
	int health
	double damage
	double attackSpeed
	class(enum profession(doctor, lawyer, policeman, villain))
	void? special ability()
	enum level(common, rare, epic)
	Arraylist equipment



Class Equipment -> Class weapon 
			double damage
			enum rarity
			enum type
			
		-> Class armor
			int armorPoints
			enum rarity

		-> Class special abilities
			void ability()
			enum type

ClassPlayer -> Class Inventory
	Map equipment
	Arraylist boosters
	int money

Class GameProcess
	void gameState()
	void playerState()
	

