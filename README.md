<img width="1024" height="1024" alt="logo" src="https://github.com/user-attachments/assets/621f0d38-4cf7-4860-9dcf-4b1969d5d38a" />

"Let's become Legends by defeating Myths!"

# Description

Valknut is a turn-based combat game inspired in Norse mythology. In this game, our heroes are searching for an enemyworthy enough to send them to Valhalla! 
This is the repository our team is using to develope the game. Feel free to browse around!

# Project Structure
.
└── src/
    └── me/
        ├── control/
        │   └── Controller.java
        ├── factories/
        ├── model/
        │   ├── items/
        │   │   ├── AgilityItem.java
        │   │   ├── ClevernessItem.java
        │   │   ├── Complement.java
        │   │   ├── DamageItem.java
        │   │   ├── Healing.java
        │   │   ├── Inventory.java
        │   │   ├── item.java
        │   │   ├── ItemComplement.java
        │   │   └── ResistanceItem.java
        │   ├── Attribute.java
        │   ├── Character.java
        │   ├── Combat.java
        │   ├── CombatOption.java
        │   ├── Element.java
        │   ├── Enemy.java
        │   ├── EnemyBuilder.java
        │   ├── Hero.java
        │   └── HeroBuilder.java
        ├── view/
        │   ├── CombatView.java
        │   ├── ConsoleColors.java
        │   ├── ConsoleIO.java
        │   ├── MenuView.java
        │   ├── Messages.java
        │   ├── Story.java
        │   └── StoryView.java
        └── Main.java
