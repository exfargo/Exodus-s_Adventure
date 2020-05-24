package other;

import character.Boss;
import character.Bottle;
import character.Npcs;
import character.Player;
import saves.Load;
import saves.Save;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    public static void start() {
        Scanner input = new Scanner(System.in);
        Random r = new Random();
        Player Harry = new Player();
        Level map1 = new Level();
        Boss boss1 = null;
        Bottle col1 = new Bottle(Npcs.getSlaves());
        int previousCharacterPosition = -1;
        int returnBossLeftTime = 2;
        String command;

        //<editor-fold desc="Save Loading">
        System.out.println("Do you want to load a save ?");
        command = input.nextLine();
        while (command.toLowerCase().contains("y")) {
            try {
                Load.printFiles();
                System.out.println("What save you want to load ?");
                String name = input.nextLine();
                Harry = Load.loadCh(name);
                map1 = Load.loadMap(name);
                col1 = Load.loadBottle(name);
                System.out.println("Do you want to reload ?");
                if (!input.nextLine().toLowerCase().contains("y")) {
                    command = "n";
                }
            } catch (Exception e) {
                System.out.println("Something went wrong");
                System.out.println("Do you want to try again ?");
                command = input.nextLine();
            }
        }
        System.out.println("\nWelcome to Exodus's Adventure");
        //</editor-fold>

        int strengthTurnsRemaining = 0;
        int vitalityTurnsRemaining = 0;

        while (true) {
            if (strengthTurnsRemaining > 0)
                strengthTurnsRemaining--;
            if (strengthTurnsRemaining == 0)
                Objects.requireNonNull(Harry).exitMultiplierD();
            if (vitalityTurnsRemaining > 0)
                vitalityTurnsRemaining--;
            if (vitalityTurnsRemaining == 0)
                Objects.requireNonNull(Harry).exitMultiplierH();
            System.out.println();
            assert map1 != null;
            if (map1.getPosition() != Boss.getPosition()) {
                if (map1.getTile().equals("Town")) {
                    if (previousCharacterPosition != map1.getPosition()) {
                        System.out.println("You arrived in a Town");
                        System.out.println("You got healed");
                        Harry.changeHealth(99);
                        previousCharacterPosition = map1.getPosition();
                    }
                } else {
                    if (previousCharacterPosition != map1.getPosition()) {
                        System.out.println("You are in a " + map1.getTile());
                        System.out.println("You see " + map1.getEntity());
                        previousCharacterPosition = map1.getPosition();
                    }
                    if (map1.getTile().equals("Dungeon")) {
                        Harry.changeHealth(15);
                    }
                }
                System.out.println("What will you do ?\n");
                command = input.nextLine().toLowerCase();
                if (Boss.getIsSummoned()) {
                    returnBossLeftTime--;
                }
            } else {
                System.out.println("You are in a boss fight");
            }

            if (Objects.requireNonNull(command).equals("boss") && !map1.getTile().equals("Town") && Harry.getItem("Doom_Coin") >= 1 || map1.getPosition() == Boss.getPosition()) {
                if (!Boss.getIsSummoned()) {
                    System.out.println("Are You sure?");
                    String control = input.nextLine().toUpperCase().substring(0, 1);
                    if (control.equals("Y")) {
                        System.out.println("What difficulty ? 1/2/3+");
                        boss1 = new Boss(Integer.parseInt(input.nextLine()), map1.getPosition());
                        Harry.changeInv("Doom_Coin", -1);
                        System.out.println(boss1.getName() + " has awoken!");
                    }
                }

                boolean dead = false;
                int y = 1;
                while (!dead) {
                    y += 1;
                    if (y % 2 == 0) {
                        if (strengthTurnsRemaining > 0)
                            strengthTurnsRemaining--;
                        if (strengthTurnsRemaining <= 0)
                            Harry.exitMultiplierD();
                        if (vitalityTurnsRemaining > 0)
                            vitalityTurnsRemaining--;
                        if (vitalityTurnsRemaining <= 0)
                            Harry.exitMultiplierH();
                        System.out.println("What will you do ?");
                        strengthTurnsRemaining++;
                        command = input.nextLine().toLowerCase();
                        if ("attack".contains(command) || command.contains("kill")) {
                            Objects.requireNonNull(boss1).obtainDamage(Harry.getDamage());
                            System.out.println("You attacked " + boss1.getName() + ". Now he has " + boss1.getHealth() + " Hp.");
                        } else if (command.contains("pot")) {
                            Harry.showPotions();
                            command = input.nextLine().toLowerCase();
                            if (command.contains("health")) {
                                if (Harry.getItem("Strong_Health_Potion") > 0) {
                                    Harry.changeHealth(70);
                                    Harry.changeInv("Strong_Health_Potion", -1);
                                } else if (Harry.getItem("Health_Potion") > 0) {
                                    Harry.changeHealth(50);
                                    Harry.changeInv("Health_Potion", -1);
                                    System.out.println("You healed 70 HP");
                                } else {
                                    Harry.changeHealth(10);
                                    System.out.println("You healed 20 HP");
                                }
                            } else if (command.contains("streng")) {
                                if (Harry.getItem("Strength_Potion") > 0) {
                                    Harry.doubleDamage();
                                    Harry.changeInv("Strength_Potion", -1);
                                    strengthTurnsRemaining = 4;
                                } else {
                                    System.out.println("You don't have any Strength Potions.");
                                }
                            } else if (command.contains("vitality")) {
                                if (Harry.getItem("Vitality_Potion") > 0) {
                                    Harry.doubleHealth();
                                    Harry.changeInv("Vitality_Potion", -1);
                                    vitalityTurnsRemaining = 4;
                                } else {
                                    System.out.println("You don't have any Vitality Potions.");
                                }
                            }
                            y--;
                        } else if ("info".equals(command)) {
                            Harry.showStats();
                            Npcs.getScore();
                            y--;
                        } else if ("enemy".equals(command)) {
                            System.out.println(Objects.requireNonNull(boss1).getName());
                            System.out.println("HP : " + boss1.getHealth());
                            System.out.println("Armor : " + boss1.getArmor());
                            System.out.println("Damage : " + boss1.getDamage());
                            System.out.println("Ability : " + boss1.getAbilityDamage());
                            y--;
                        } else if (command.contains("move") || command.contains("go") || command.contains("discover")) {
                            if (!command.contains("b")) {
                                if (!map1.getEntity().contains("Door")) {
                                    map1.move("forward");
                                } else {
                                    System.out.println("There's a door, first you have to unlock it.");
                                }
                            } else map1.move("back");
                            break;
                        } else if (command.equals("help")) {
                            System.out.println("You may use these commands : attack, heal, move, enemy, info");
                            y--;
                        } else {
                            System.out.println("You can't to this here");
                            y--;
                        }
                    } else {
                        if (Objects.requireNonNull(boss1).getSkillCooldown() > 0) {
                            boss1.skillCooldown();
                            Harry.obtainDamage(boss1.getDamage());
                            System.out.println(boss1.getName() + " attacked you. Now you have " + Harry.getHealth() + " Hp");
                        } else {
                            Harry.obtainDamage(boss1.getAbilityDamage());
                            System.out.println(boss1.getName() + " attacked you with special ability. Now you have " + Harry.getHealth() + " Hp");
                            boss1.resetSkillCooldown();
                        }
                    }
                    if (Objects.requireNonNull(boss1).getHealth() <= 0) {
                        System.out.println("You have defeated " + boss1.getName());
                        Harry.dropBoss();
                        if (boss1.getName().contains("Chingischan")) {
                            Harry.changeInv("Exodus's Soul", 1);
                            System.out.println("You recieved Exodus's Soul");
                        }
                        Boss.setPosition(-1);
                        Boss.setIsSummoned(false);
                        dead = true;
                    } else if (Harry.getHealth() <= 0) {
                        System.out.println("You Lost");
                        Harry.showInv();
                        map1.printAll();
                        System.exit(0);
                    }
                }
                if (returnBossLeftTime == 0) {
                    Boss.setPosition(-1);
                    System.out.println("You didn't manage to return!");
                    Harry.changeHealth(-(Harry.getHealth() / 2));
                    Harry.changeBestDamage();
                    System.out.println("You lost 50% of you current HP\nand your damage has been decreased by 20%.");
                }
            } else if (command.contains("move") || command.contains("go") || command.contains("discover")) {
                if (command.contains("b")) {
                    if (map1.getPosition() != 0) {
                        map1.move("back");
                    } else {
                        System.out.println("You can't go further back");
                    }
                } else {
                    if (map1.getEntity().contains("Door")) {
                        System.out.println("You cannot go trough the door. You have to open it first.");
                    } else
                        map1.move("forward");
                }
                if (!map1.defeatAt() && !map1.getTile().equals("Town")) {
                    Npcs en1 = new Npcs(map1.getDifficultyMap(), map1.getTile());
                    if (!en1.getName().equals("Merchant") && !en1.getName().contains("Slave")) {
                        if (previousCharacterPosition != map1.getPosition()) {
                            System.out.println("You are in a " + map1.getTile());
                            System.out.println("You see " + map1.getEntity());
                            previousCharacterPosition = map1.getPosition();
                        }
                        System.out.println("And there is " + en1.getFullName());
                        System.out.println("What will you do?");
                        boolean dead = false;
                        int i = r.nextInt(2);
                        boolean auto = false;
                        while (!dead) {
                            if (i % 2 == 0) {
                                if (strengthTurnsRemaining > 0)
                                    strengthTurnsRemaining--;
                                if (strengthTurnsRemaining <= 0)
                                    Harry.exitMultiplierH();
                                if (vitalityTurnsRemaining > 0)
                                    vitalityTurnsRemaining--;
                                if (vitalityTurnsRemaining <= 0)
                                    Harry.exitMultiplierD();
                                if (!auto) {
                                    command = input.nextLine();
                                    if (command.contains("au")) {
                                        auto = true;
                                    } else if (command.contains("att")) {
                                        en1.receiveDamage(Harry.getDamage());
                                        System.out.println("You attacked. Enemy has " + en1.getHealth() + " Hp");
                                    } else if (command.contains("pot")) {
                                        Harry.showPotions();
                                        command = input.nextLine().toLowerCase();
                                        if (command.contains("health")) {
                                            if (Harry.getItem("Strong_Health_Potion") > 0) {
                                                Harry.changeHealth(70);
                                                Harry.changeInv("Strong_Health_Potion", -1);
                                            } else if (Harry.getItem("Health_Potion") > 0) {
                                                Harry.changeHealth(50);
                                                Harry.changeInv("Health_Potion", -1);
                                                System.out.println("You healed 70 HP");
                                            } else {
                                                Harry.changeHealth(10);
                                                System.out.println("You healed 20 HP");
                                            }
                                        } else if (command.contains("streng")) {
                                            if (Harry.getItem("Strength_Potion") > 0) {
                                                Harry.doubleDamage();
                                                Harry.changeInv("Strength_Potion", -1);
                                                strengthTurnsRemaining = 4;
                                            } else {
                                                System.out.println("You don't have any Strength Potions.");
                                            }
                                        } else if (command.contains("vitality")) {
                                            if (Harry.getItem("Vitality_Potion") > 0) {
                                                Harry.doubleHealth();
                                                Harry.changeInv("Vitality_Potion", -1);
                                                vitalityTurnsRemaining = 4;
                                            } else {
                                                System.out.println("You don't have any Vitality Potions.");
                                            }
                                        }
                                    } else if (command.contains("fl")) {
                                        if (r.nextInt(10) < 3) {
                                            System.out.println("You didn't manage to flee from " + en1.getFullName());
                                        } else {
                                            map1.move("back");
                                            break;
                                        }
                                    } else if (command.contains("inf")) {
                                        Harry.showStats();
                                        i++;
                                    } else if (command.contains("help")) {
                                        System.out.println("You can use these commands : auto, attack, heal, flee, info, enemy");
                                        i++;
                                    } else if (command.contains("en")) {
                                        en1.getStats();
                                        i++;
                                    } else {
                                        System.out.println("You are in a fight, you can't do that now.");
                                        i++;
                                    }
                                } else {
                                    if (Harry.getHealth() > 30) {
                                        en1.receiveDamage(Harry.getDamage());
                                        System.out.println("You attacked. Enemy has " + en1.getHealth() + " Hp");
                                    } else {
                                        if (Harry.getItem("Strong_Health_Potion") > 0) {
                                            Harry.changeHealth(70);
                                            Harry.changeInv("Strong_Health_Potion", -1);
                                        } else if (Harry.getItem("Health_Potion") > 0) {
                                            Harry.changeHealth(50);
                                            Harry.changeInv("Health_Potion", -1);
                                            System.out.println("You healed 50 HP");
                                        } else {
                                            Harry.changeHealth(10);
                                            System.out.println("You healed 10 HP");
                                        }
                                    }
                                }
                            } else {
                                if (r.nextInt(50) < 1) {
                                    System.out.println("\n" + en1.getFullName() + " fled, it got scared.");
                                    break;
                                } else {
                                    Harry.obtainDamage(en1.getDamageValue());
                                    System.out.println(en1.getName() + " attacked you. Now you have " + Harry.getHealth() + " Hp");
                                }
                            }
                            i++;
                            if (en1.getHealth() <= 0) {
                                System.out.println("You have defeated " + en1.getFullName());
                                Harry.dropEnemy();
                                map1.enemyDefeat();
                                dead = true;
                            } else if (Harry.getHealth() <= 0) {
                                System.out.println("You Lost");
                                Harry.showInv();
                                map1.printAll();
                                System.exit(0);
                                dead = true;
                            }
                        }
                    } else if (en1.getName().equals("Merchant")) {
                        Shop list = new Shop("Merchant");
                        System.out.println("Lucky, You encountered a traveling merchant");
                        System.out.println("He sells lot of staff cheaper than in town.");
                        System.out.println("You have " + Harry.getQiqra() + " Qiqra!");
                        list.printList();
                        boolean exit = false;
                        while (!exit) {
                            boolean succesfull = false;
                            int i = 0;
                            String b = input.nextLine();
                            if (b.toLowerCase().equals("close") || b.toLowerCase().equals("exit") || b.toLowerCase().equals("cancel")) {
                                break;
                            }
                            try {
                                Harry.buyItem(b, list.quantity(b), list.price(b));
                                succesfull = true;
                            } catch (Exception e) {
                                while (!succesfull) {
                                    i++;
                                    try {
                                        if (i == 1) {
                                            Harry.buyItem(b.concat("_Damage"), list.quantity(b.concat("_Damage")), list.price(b.concat("_Damage")));
                                            exit = true;
                                            succesfull = true;
                                        } else if (i == 2) {
                                            Harry.buyItem(b.concat("_Armor"), list.quantity(b.concat("_Armor")), list.price(b.concat("_Armor")));
                                            exit = true;
                                            succesfull = true;
                                        } else if (i == 3) {
                                            Harry.buyItem(b.concat("_Dmg%"), list.quantity(b.concat("_Dmg%")), list.price(b.concat("_Dmg%")));
                                            exit = true;
                                            succesfull = true;
                                        } else if (i == 4) {
                                            Harry.buyItem(b.concat("_Arm%"), list.quantity(b.concat("_Arm%")), list.price(b.concat("_Amr%")));
                                            exit = true;
                                            succesfull = true;
                                        } else {
                                            System.out.println("Shop does not contain this item.");
                                            System.out.println("What will you buy");
                                            succesfull = true;
                                        }
                                    } catch (Exception q) {
                                        // empty catch block - i just need it to not crash, i dont care
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("You see a Slave!\nYou may capture one of those\nand sell them in next city.");
                        System.out.println("Do you want to capture him ?");
                        if (input.nextLine().toLowerCase().contains("y")) {
                            if (en1.getHealth() <= 2) {
                                System.out.println("This one is very weak,\nand so it takes only while to\nbind him.");
                                Harry.setInv(en1.getName(), en1.getHealth() * en1.getDamageValue());
                                Objects.requireNonNull(col1).addToCollection(en1.getName().substring(0, en1.getName().indexOf(" Slave")), Harry);
                            } else if (en1.getHealth() <= 6) {
                                boolean exit = false;
                                System.out.println("He wants to fight back.");
                                while (!exit) {
                                    System.out.println("Do you want to hit him ?");
                                    if (input.nextLine().toLowerCase().contains("y")) {
                                        if (en1.getDamageValue() - r.nextInt(2) <= 5) {
                                            System.out.println("You hit him very hard\ntherefore he falls unconscious.");
                                            Harry.setInv(en1.getName(), en1.getHealth() * en1.getDamageValue());
                                            Objects.requireNonNull(col1).addToCollection(en1.getName().substring(0, en1.getName().indexOf(" Slave")), Harry);
                                            exit = true;
                                        } else {
                                            System.out.println("He dodges your attack.");
                                            Harry.changeHealth(-2);
                                            System.out.println("He hits you for 2 damage.");
                                        }
                                    } else {
                                        System.out.println("He runs away, you lost your slave!");
                                        exit = true;
                                    }
                                }
                            } else {
                                System.out.println("He was too fast,\nyou know black meat!");
                            }
                        } else {
                            System.out.println("He runs away like a chicken!");
                        }
                    }
                }
            } else if (command.contains("map")) {
                if (command.contains("all")) {
                    map1.printAll();
                } else {
                    map1.printNear();
                }
            } else if (command.equals("loot") && !map1.getTile().equals("Town")) {
                if (map1.getEntity().equals("nothing")) {
                    System.out.println("You already looted this place.\n");
                } else if (map1.getEntity().equals("Door")) {
                    System.out.println("You cannot loot doors, you can use key to open the door.");
                } else {
                    Harry.loot(map1.getEntity());
                    map1.lootTile();
                }
            } else if ((command.contains("slave") && command.contains("sell")) || command.contains("black")) {
                System.out.println("You go to a black market.");
                Harry.sellSlaves();
            } else if (command.contains("bot") || command.contains("col")) {
                Objects.requireNonNull(col1).printCollection();
            } else if (command.equals("open") || (command.contains("use") && command.contains("key"))) {
                if (Harry.getInv().containsKey("Key")) {
                    System.out.println("You open the door.");
                    System.out.println("You see golden dungeon chest.");
                    System.out.println("You open it, and you receive : ");
                    Harry.dropChest();
                    map1.nullEntity();
                }
            } else if (command.equals("inventory") || command.equals("inv")) {
                Harry.showInv();
            } else if (command.contains("stat")) {
                Harry.showStats();
                Npcs.getScore();
            } else if (command.contains("pot")) {
                Harry.showPotions();
                command = input.nextLine().toLowerCase();
                if (command.contains("heal")) {
                    if (Harry.getItem("Strong_Health_Potion") > 0) {
                        Harry.changeHealth(70);
                        Harry.changeInv("Strong_Health_Potion", -1);
                    } else if (Harry.getItem("Health_Potion") > 0) {
                        Harry.changeHealth(50);
                        Harry.changeInv("Health_Potion", -1);
                        System.out.println("You healed 70 HP");
                    } else {
                        Harry.changeHealth(10);
                        System.out.println("You healed 20 HP");
                    }
                } else if (command.contains("str")) {
                    if (Harry.getItem("Strength_Potion") > 0) {
                        Harry.doubleDamage();
                        Harry.changeInv("Strength_Potion", -1);
                        strengthTurnsRemaining = 4;
                    } else {
                        System.out.println("You don't have any Strength Potions.");
                    }
                } else if (command.contains("vit")) {
                    if (Harry.getItem("Vitality_Potion") > 0) {
                        Harry.doubleHealth();
                        Harry.changeInv("Vitality_Potion", -1);
                        vitalityTurnsRemaining = 4;
                    } else {
                        System.out.println("You don't have any Vitality Potions.");
                    }
                } else if (command.contains("tra")) {
                    if (Harry.getItem("Translocation_Potion") > 0) {
                        System.out.println("Enter ID of tile where you want to warp.");
                        int tile = Integer.parseInt(input.nextLine());
                        if (map1.warpTo(tile)) {
                            Harry.changeInv("Translocation_Potion", -1);
                        }
                    } else {
                        System.out.println("You don't have any Translocation Potions");
                    }
                }
            } else if (command.equals("shop")) {
                Shop list = new Shop("Shop");
                System.out.println("You have " + Harry.getQiqra() + " Qiqra!");
                list.printList();
                boolean succesfull = false;
                int i = 0;

                String b = input.nextLine();
                try {
                    Harry.buyItem(b, list.quantity(b), list.price(b));
                    succesfull = true;
                } catch (Exception e) {
                    while (!succesfull) {
                        i++;
                        try {
                            if (i == 1) {
                                Harry.buyItem(b.concat("_Damage"), list.quantity(b.concat("_Damage")), list.price(b.concat("_Damage")));
                                succesfull = true;
                            } else if (i == 2) {
                                Harry.buyItem(b.concat("_Armor"), list.quantity(b.concat("_Armor")), list.price(b.concat("_Armor")));
                                succesfull = true;
                            } else if (i == 3) {
                                Harry.buyItem(b.concat("_Dmg%"), list.quantity(b.concat("_Dmg%")), list.price(b.concat("_Dmg%")));
                                succesfull = true;
                            } else if (i == 4) {
                                Harry.buyItem(b.concat("_Arm%"), list.quantity(b.concat("_Arm%")), list.price(b.concat("_Amr%")));
                                succesfull = true;
                            } else {
                                System.out.println("Shop does not contain this item.");
                                succesfull = true;
                            }
                        } catch (Exception q) {
                            // empty catch block - i just need it to not crash, i dont care
                        }
                    }
                }
                System.out.println();
            } else if (command.equals("store") && map1.getTile().equals("Town")) {
                Shop list = new Shop("Store");
                System.out.println("You have " + Harry.getQiqra() + " Qiqra!");
                list.printList();
                boolean succesfull = false;
                int i = 0;

                String b = input.nextLine();
                try {
                    Harry.buyItem(b, list.quantity(b), list.price(b));
                    succesfull = true;
                } catch (Exception e) {
                    while (!succesfull) {
                        i++;
                        try {
                            if (i == 1) {
                                Harry.buyItem(b.concat("_Damage"), list.quantity(b.concat("_Damage")), list.price(b.concat("_Damage")));
                                succesfull = true;
                            } else if (i == 2) {
                                Harry.buyItem(b.concat("_Armor"), list.quantity(b.concat("_Armor")), list.price(b.concat("_Armor")));
                                succesfull = true;
                            } else if (i == 3) {
                                Harry.buyItem(b.concat("_Dmg%"), list.quantity(b.concat("_Dmg%")), list.price(b.concat("_Dmg%")));
                                succesfull = true;
                            } else if (i == 4) {
                                Harry.buyItem(b.concat("_Arm%"), list.quantity(b.concat("_Arm%")), list.price(b.concat("_Amr%")));
                                succesfull = true;
                            } else {
                                System.out.println("Shop does not contain this item.");
                                succesfull = true;
                            }
                        } catch (Exception q) {
                            // empty catch block - i just need it to not crash, i dont care
                        }
                    }
                }
            } else if (map1.getTile().equals("Town") && command.contains("craft")) {
                Craft c1 = new Craft();
                c1.list(Harry.getItem());
                System.out.println("What do you want to craft ?");
                command = input.nextLine().replaceAll(" ", "_");
                try {
                    Harry.craftItem(Objects.requireNonNull(c1.getIngredients(command)), c1.getResult(command));
                } catch (Exception e) {
                    System.out.println("Item not recognised");
                }

            } else if (command.contains("sell")) {
                if (command.contains("ing")) {
                    Harry.sellAllIngredients();
                } else if (command.contains("all")) {
                    Harry.sellAll();
                } else {
                    Harry.showInv();
                    System.out.println("\nWhat item you wish to sell?");
                    String item = input.nextLine().replaceAll(" ", "_");
                    System.out.print("Amount : ");
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(input.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Unknown amount.");
                    }
                    Harry.sellItem(item, amount, 0);
                }
            } else if (command.equals("help")) {
                if (map1.getTile().equals("Town")) {
                    System.out.println("You may use these commands : store, shop, craft, sell/(ingredients), move, map/(all), sell slaves, inventory, stats, help");
                } else {
                    System.out.println("You may use these commands : move, shop, map/(all), inventory, bottle, loot, stats, help");
                }
            } else if (command.equals("exit") || command.equals("leave") || command.equals("kys") || command.equals("die")) {
                System.out.println("You left the game !");
                System.exit(0);
            } else if (command.equals("save")) {
                System.out.println("choose name");
                String name = input.nextLine();
                Save.saveCh(Harry, name);
                Save.saveMap(map1, name);
                Save.saveBottle(col1, name);
            } else {
                if ("boss".equals(command)) {
                    System.out.println("You cannot summon the boss right now.");
                } else {
                    System.out.println("'" + command + "'" + " is not recognised as an internal or external command.");
                }
            }
        }

    }
}
