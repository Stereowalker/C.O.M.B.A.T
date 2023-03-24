package com.stereowalker.combat.util;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.stereowalker.old.combat.config.RpgClientConfig;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.Mth;

public class RandomWorldNameGenerator {
	
	public static String randomLocation(Random rand){
		List<String> words = Lists.newArrayList(
				"wood", "forest", "copse", "bush", "trees", "stand",
				"swamp", "marsh", "wetland", "fen", "bog", "moor", "heath", "fells", "morass"
				);
		
		return words.get(rand.nextInt(words.size()));
	}
	
	public static String randomAdjective(Random rand){
		List<String> words = Lists.newArrayList(
				"adorable", "agreeable", "amused", "annoying", "ashamed", "awful",
				"better", "bloody", "blushing", "brave", "busy", 
				"cautious", "clean", "cloudy", "combative", "condemned", "Courageous", "Courageous"
				);
		
		return words.get(rand.nextInt(words.size()));
	}
	
	public static String randomNoun(Random rand){
		List<String> words = Lists.newArrayList(
				"Ability", "Absurdity", "Accidents",
				"Acne", "Acorns", "Adamantite",
				"Adoration", "Adulthood", "Advantage",
				"Adventure",
				"Agony",
				"Alarm",
				"Alcohol",
				"Ale",
				"Allergies",
				"Amazement",
				"Angels",
				"Anger",
				"Angry Gamers",
				"Anguish",
				"Animal Carcasses",
				"Annoyance",
				"Anvils",
				"Anxiety",
				"Apples",
				"Apricots",
				"Argon",
				"Arrows",
				"Arsenic",
				"Arson",
				"Arthritis",
				"Asbestos",
				"Ash",
				"Assassins",
				"Assault",
				"Atrophy",
				"Awareness",
				"Awe",
				"Bacon",
				"Bad Decisions",
				"Bad Jokes",
				"Bad Luck",
				"Bad Omens",
				"Balance",
				"Bamboo",
				"Bananas",
				"Bandits",
				"Bankruptcy",
				"Bark",
				"Bats",
				"Beauty",
				"Beenades",
				"Bees",
				"Beggars",
				"Beheadings",
				"Belief",
				"Betrayers",
				"Birds",
				"Birthdays",
				"Bitterness",
				"Bladders",
				"Blasphemy",
				"Blindness",
				"Blinkroot",
				"Blocks",
				"Bloodletting",
				"Bloodshed",
				"Blood",
				"Blossoms",
				"Body",
				"Bones",
				"Bone",
				"Boomers",
				"Boots",
				"Boredom",
				"Boulders",
				"Brains",
				"Branches",
				"Bravery",
				"Bribery",
				"Bridges",
				"Brilliance",
				"Broken Bones",
				"Broken Dreams",
				"Broken Glass",
				"Bronies",
				"Bubbles",
				"Buckets",
				"Bugs",
				"Bums",
				"Bunnies",
				"Burglars",
				"Burning Hair",
				"Burnt Flesh",
				"Burnt Offerings",
				"Butchery",
				"Butterflies",
				"Cacti",
				"Calmness",
				"Candy",
				"Care",
				"Carnage",
				"Carrion",
				"Casualty",
				"Cats",
				"Cattails",
				"Caves",
				"Celebration",
				"Chainsaws",
				"Chaos",
				"Charity",
				"Cheats",
				"Cherries",
				"Chests",
				"Childhood",
				"Children",
				"Chlorophyte",
				"Cilantro",
				"Clarity",
				"Clay",
				"Clentamination",
				"Cleverness",
				"Clouds",
				"Cobalt",
				"Coconuts",
				"Coffee",
				"Coins",
				"Coldness",
				"Comfort",
				"Compassion",
				"Compost",
				"Confidence",
				"Confinement",
				"Confusion",
				"Contentment",
				"Convicts",
				"Copper",
				"Corpses",
				"Corruption",
				"Courage",
				"Covfefe",
				"Creation",
				"Creatures",
				"Creepers",
				"Crests",
				"Crime",
				"Criminals",
				"Crimtane",
				"Crippling Depression",
				"Crooks",
				"Crows",
				"Crystals",
				"Cthulhu",
				"Curiosity",
				"Cursed Flames",
				"Dabbing",
				"Daisies",
				"Dank Memes",
				"Darkness",
				"Darts",
				"Daughters",
				"Dawn",
				"Daybloom",
				"Dead Bodies",
				"Deadbeats",
				"Deathweed",
				"Death",
				"Debauchery",
				"Debt",
				"Decapitation",
				"Decaying Meat",
				"Deceit",
				"Deception",
				"Dedication",
				"Defeat",
				"Defecation",
				"Degradation",
				"Delay",
				"Delusion",
				"Dementia",
				"Demilogic",
				"Demonite",
				"Demons",
				"Derangement",
				"Despair",
				"Desperation",
				"Destruction",
				"Dijon Mustard",
				"Dirt",
				"Disappointment",
				"Disgust",
				"Dishonesty",
				"Dismay",
				"Distortion",
				"Distribution",
				"Divorce",
				"Doom",
				"Dragonfruit",
				"Dragons",
				"Dread",
				"Dreams",
				"Drills",
				"Drums",
				"Ducks",
				"Dusk",
				"Dust",
				"Duty",
				"Dysphoria",
				"Ears",
				"Education",
				"Eggs",
				"Elderberries",
				"Elegance",
				"Envy",
				"Evasion",
				"Evil",
				"Exile",
				"Exploits",
				"Explosives",
				"Extortion",
				"Eyes",
				"Fable",
				"Face Monsters",
				"Failures",
				"Fairies",
				"Faith",
				"Falling Stars",
				"False Idols",
				"False Imprisonment",
				"Falsehood",
				"Fame",
				"Famine",
				"Fantasy",
				"Fascination",
				"Fatality",
				"Fear",
				"Feathers",
				"Feces",
				"Felons",
				"Ferns",
				"Fiction",
				"Fiends",
				"Fingers",
				"Fireblossom",
				"Fireflies",
				"Fish",
				"Flails",
				"Flatulence",
				"Flatus",
				"Flesh",
				"Floof",
				"Flowers",
				"Flying Fish",
				"Food Poisoning",
				"Forgery",
				"Forks",
				"Fraud",
				"Freaks",
				"Freckles",
				"Freedom",
				"Friendship",
				"Fright",
				"Frogs",
				"Frost",
				"Fruit",
				"Gangsters",
				"Garbage",
				"Garlic Bread",
				"Gears",
				"Gel",
				"Gen-Xers",
				"Gen-Yers",
				"Generation",
				"Ghosts",
				"Giggles",
				"Gingers",
				"Girls",
				"Glass",
				"Gloom",
				"Gluttony",
				"Goals",
				"Goblins",
				"Goldfish",
				"Gold",
				"Gossip",
				"Grain",
				"Grandfathers",
				"Grandmothers",
				"Granite",
				"Grapefruit",
				"Grapes",
				"Grasshoppers",
				"Grass",
				"Graves",
				"Greed",
				"Grief",
				"Guitars",
				"Guts",
				"Hair",
				"Hamburgers",
				"Hammers",
				"Hands",
				"Happiness",
				"Happy Endings",
				"Hardship",
				"Harpies",
				"Hate",
				/*Hatred
				Heartache
				Hearts
				Heart
				Heels
				Hellstone
				Herbs
				Heresy
				Homicide
				Honey
				Hoodlums
				Hooks
				Hooligans
				Hopelessness
				Hornets
				Hornswoggle
				Horns
				Horrors
				Horror
				Houses
				Humiliation
				Hurt
				Hysteria
				I Didn't Inhale
				Ice
				Ichor
				Illness
				Indictments
				Indigestion
				Indignity
				Infancy
				Infections
				Inflammation
				Inflation
				Injury
				Insanity
				Insects
				Intelligence
				Intestines
				Invasions
				Iron
				Irritation
				Isolation
				Ivy
				Jaws
				Jealousy
				Jellyfish
				Joy
				Justice
				Karens
				Kidneys
				Kindness
				Kittens
				Knives
				Krypton
				Lamps
				Laughter
				Lava
				Lawsuits
				Lawyers
				Lead
				Leaves
				Legend
				Leggings
				Legs
				Lemons
				Leprosy
				Letdown
				Lethargy
				Liberty
				Lies
				Life
				Lightning Bugs
				Lilies
				Lilith
				Lilypad
				Lips
				Litigation
				Livers
				Loathing
				Lombago
				Loneliness
				Lore
				Loss
				Love
				Luck
				Luggage
				Luminite
				Lungs
				Luxury
				Madness
				Maggots
				Mana
				Mangos
				Mania
				Manslaughter
				Marble
				Markets
				Mastication
				Maturity
				Medicine
				Melancholy
				Memes
				Mercy
				Merica
				Meteorite
				Mice
				Midnight Ramen
				Midnight
				Millennials
				Mimics
				Mirrors
				Misery
				Misfortune
				Missing Limbs
				Models
				Money
				Monotony
				Moonglow
				Moonlight
				Morons
				Mortality
				Moss
				Mourning
				Mouths
				Movement
				Muckers
				Mud
				Muggers
				Murderers
				Murder
				Mushrooms
				Music
				Mystery
				Mythril
				Myth
				Nausea
				Necromancers
				Necromancy
				Nightcrawlers
				Nightmares
				Night
				No Remorse
				Nocram
				Nostalgia
				Nudity
				Obscurity
				Obsidian
				Odor
				Oopsie Daisy
				Ooze
				Open Wounds
				Opportunity
				Options
				Oranges
				Organs
				Orichalcum
				Outlaws
				Owls
				Pad Thai
				Pain
				Palladium
				Panhandlers
				Panic
				Parasites
				Parties
				Party Time
				Patience
				Peace
				Penguins
				Perjury
				Perspiration
				Pickaxes
				Pickpockets
				Pineapples
				Pinky
				Piranhas
				Piranha
				Pirates
				Pixies
				Plantero
				Plants
				Platinum
				Pleasure
				Plums
				Politicians
				Ponies
				Potions
				Poverty
				Power
				Pride
				Prisms
				Privacy
				Prophecy
				Psychology
				Public Speaking
				Puppies
				Rain
				Ramen
				Rats
				Reality
				Regret
				Regurgitation
				Relaxation
				Relief
				Remorse
				Repugnance
				Rich Mahogany
				Riches
				Rocks
				Rope
				Roses
				Rotten Fruit
				Rotting Flesh
				Rumours
				Sacrifice
				Sacrilege
				Sadness
				Salesmen
				Sandstone
				Sand
				Sanity
				Saplings
				Sap
				Sashimi
				Satisfaction
				Scandal
				Scorpions
				Seasons
				Seaweed
				Seclusion
				Secrecy
				Secrets
				Seeds
				Self-control
				Self-disgust
				Self-loathing
				Services
				Severed Heads
				Shade
				Shadows
				Shattered Hope
				Shivers
				Shiverthorn
				Shock
				Shrimp
				Silliness
				Silt
				Silver
				Sin
				Skeletons
				Skill
				Skin
				Skulls
				Sleep
				Slime
				Sloths
				Sloth
				Smiles
				Smoke
				Snails
				Snatchers
				Snow
				Solicitation
				Sorrow
				Spaghetti
				Sparkles
				Spears
				Speed
				Spicy Ramen
				Spikes
				Spirits
				Splinters
				Sponges
				Sprinkles
				Spurs
				Squid
				Squirrels
				Stagnant Water
				Starfruit
				Starvation
				Statues
				Stone
				Stonks
				Strength
				Strictness
				Stumps
				Suffering
				Sunflowers
				Superstition
				Surprise
				Swagger
				Swindlers
				Swords
				Talent
				Tan Suits
				Taxes
				Teddy's Bread
				Teeth
				Terror
				the Ancients
				the Angler
				the Apple
				the Archer
				the Aunt
				the Axe
				the Baby
				the Balloon
				the Ball
				the Bat
				the Beast
				the Betrayed
				the Blender
				the Blood Moon
				the Bow
				the Bride
				the Brony
				the Bubble
				the Bunny
				the Cactus
				the Cloud
				the Coma
				the Corruptor
				the Crab
				the Dance
				the Dark
				the Dead
				the Devourer
				the Drax
				the Ducks
				the Eclipse
				the Fairy
				the Father
				the Flu
				the Foot
				the Frozen
				the Gift
				the Ginger
				the Goblin*/
				"the Golem",
				"the Greatest Generation",
				"the Groom",
				"the Guest",
				"the Hammer",
				"the Hammush",
				"the Head",
				"the Heavens",
				"the Hipster",
				"the Hobo",
				"the Homeless",
				"the King",
				"the Law",
				"the Library",
				"the Lihzahrd",
				"the Lilith",
				"the Lizard King",
				"the Lost Generation",
				"the Mirror",
				"the Monster",
				"the Moon",
				"the Mother",
				"the Mummy",
				"the Mushroom",
				"the Narc",
				"the Needy",
				"the Nude",
				"the Old One",
				"the Pandemic",
				"the Pickaxe",
				"the Picksaw",
				"the Pigron",
				"the Po Boy",
				"the Porcelain God",
				"the Prism",
				"the Prophecy",
				"the Pwnhammer",
				"the Queen",
				"the Ramen",
				"the Right",
				"the Scholar",
				"the Shark",
				"the Sickle",
				"the Sky",
				"the Snap",
				"the Snitch",
				"the Spelunker",
				"the Staff",
				"the Stars",
				"the Stench",
				"the Stooge",
				"the Sun",
				"the Sword",
				"the Tooth",
				"The Torch God",
				"the Tortoise",
				"the Tree",
				"the Trend",
				"the Undead",
				"the Unicorn",
				"the Union",
				"the Unknown",
				"the Varmint",
				"the Waraxe",
				"the Yoyo",
				"Thieves",
				"Thorns",
				"Thunder",
				"Tingling",
				"Tin",
				"Tiredness",
				"Titanium",
				"Tombstones",
				"Torches",
				"Torment",
				"Torn Muscles",
				"Torture",
				"Traitors",
				"Tramps",
				"Tranquility",
				"Traps",
				"Trash",
				"Treasure",
				"Trees",
				"Trends",
				"Trouble",
				"Truffles",
				"Trunks",
				"Trust",
				"Tungsten",
				"Twigs",
				"Twilight",
				"Twisted Ankles",
				"Umbrellas",
				"Unjust Prices",
				"Upchuck",
				"Vagabonds",
				"Vampires",
				"Vanity",
				"Venom",
				"Victims",
				"Victory",
				"Villains",
				"Vines",
				"Violets",
				"Vomit",
				"Vultures",
				"Wands",
				"Wariness",
				"Warmth",
				"Wasps",
				"Waterleaf",
				"Weakness",
				"Wealth",
				"Webs",
				"Weeds",
				"Werewolves",
				"Whoopsies",
				"Wings",
				"Wires",
				"Wisdom",
				"Woe",
				"Worms",
				"Worries",
				"Wrath",
				"Wrenches",
				"Wyverns",
				"Xenon",
				"Yoyos",
				"Zombies",
				"Zoomers"
				);
		
		return words.get(rand.nextInt(words.size()));
	}
	
	public static String capitalized(String input) {
		String newString = input.charAt(0)+"";
		newString = newString.toUpperCase();
		int i = 0;
		for (char c : input.toCharArray()) {
			if (i > 0) {
				newString+=c;
			}
			i++;
		}
		return newString;
	}
	
	public static String generateRandomWorldName(Random rand, float originalChance) {
		originalChance = Mth.clamp(originalChance, 0, 1);
		float inverse = 1 - originalChance;
		if (!RpgClientConfig.randomWorldNames || rand.nextFloat() < originalChance) {
			return I18n.get("selectWorld.newWorld");
		} else if (rand.nextFloat() < inverse*(1.0f/6.0f)){
			return "The "+capitalized(randomAdjective(rand))+" "+capitalized(randomLocation(rand))+" of "+randomNoun(rand);
		} else if (rand.nextFloat() < inverse*(2.0f/6.0f)){
			return capitalized(randomAdjective(rand))+" "+capitalized(randomLocation(rand))+" of "+randomNoun(rand);
		} else if (rand.nextFloat() < inverse*(3.0f/6.0f)){
			return "The "+capitalized(randomAdjective(rand))+" "+capitalized(randomLocation(rand));
		} else if (rand.nextFloat() < inverse*(4.0f/6.0f)){
			return capitalized(randomAdjective(rand))+" "+capitalized(randomLocation(rand));
		} else if (rand.nextFloat() < inverse*(5.0f/6.0f)){
			return "The "+capitalized(randomLocation(rand))+" of "+randomNoun(rand);
		} else {
			return capitalized(randomLocation(rand))+" of "+randomNoun(rand);
		}
	}
}
