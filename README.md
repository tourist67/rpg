# 🌾 Whiskerfield

A 2D farming game built entirely in Java with zero external libraries.

---

## 📖 About

Whiskerfield is a simple farming simulation where you till the land, plant seeds, harvest crops, and sell your produce to make money. Explore your farm, chat with NPCs, and build your farming fortune!

---

## 📸 Screenshots
![Gameplay Screenshot 1](src/res/preview/prev3.png)

![Gameplay Screenshot 2](src/res/preview/prev1.png)

![Gameplay Screenshot 3](src/res/preview/prev2.png)

---

## 🚀 How to Run

### Prerequisites
- Java 17 or higher (JDK)

### Running the Game

1. Clone the repository:
   ```bash
   git clone https://github.com/ToxicSheep/whiskerfield.git
   cd whiskerfield
   ```

2. Compile the source files:
   ```bash
   javac -d bin src/**/*.java
   ```

3. Run the game:
   ```bash
   java -cp bin:src main.Main
   ```
   > On Windows, use `bin;src` instead of `bin:src`

---

## 🛠️ Tech Details

- **Language:** Java 21
- **Graphics:** Java AWT/Swing (no game engines or external libraries)
- **Architecture:** 
  - Tile-based world rendering with screen optimization
  - Entity system for player and NPCs
  - Collision detection
- **Resolution:** 768x576 (48px tiles, 16x12 grid)
- **Target FPS:** 60

---

## 🔮 Future Plans

- [ ] **Shop System** — Spend your hard earned money on upgrades
- [ ] **Fences & Decorations** — Customize your farm layout
- [ ] **More Crops** — Variety of plants with different growth times and values
- [ ] **Save/Load** — Persistent game progress

---

## 🎮 Controls

| Key | Action |
|-----|--------|
| WASD | Move |
| E | Till soil |
| R | Plant seeds |
| T | Harvest crops |
| ESC | Pause menu(use arrow keys and enter to navigate) |

---

## 🎨 Sprite Credits

This game primarily uses art assets from Cup Nooble, with additional assets credited to their respective creators.
