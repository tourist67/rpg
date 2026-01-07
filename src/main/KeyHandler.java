package main;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	Gamepanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed, rPressed, tPressed;

	public KeyHandler(Gamepanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// Title state controls
		if (gp.gameState == gp.titleState) {
			if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
				gp.ui.titleMenuSelection--;
				if (gp.ui.titleMenuSelection < 0) {
					gp.ui.titleMenuSelection = 1;
				}
				gp.playSoundEffect(5); // Menu sound
			}
			if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
				gp.ui.titleMenuSelection++;
				if (gp.ui.titleMenuSelection > 1) {
					gp.ui.titleMenuSelection = 0;
				}
				gp.playSoundEffect(5); // Menu sound
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.titleMenuSelection == 0) {
					// Play
					gp.gameState = gp.playState;
				} else if (gp.ui.titleMenuSelection == 1) {
					// Quit
					System.exit(0);
				}
			}
			return;
		}

		// Pause state controls
		if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
			if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
				gp.ui.pauseMenuSelection--;
				if (gp.ui.pauseMenuSelection < 0) {
					gp.ui.pauseMenuSelection = 2;
				}
				gp.playSoundEffect(5); // Menu sound
			}
			if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
				gp.ui.pauseMenuSelection++;
				if (gp.ui.pauseMenuSelection > 2) {
					gp.ui.pauseMenuSelection = 0;
				}
				gp.playSoundEffect(5); // Menu sound
			}
			// Left/Right to adjust volume when Music is selected
			if (gp.ui.pauseMenuSelection == 0) {
				if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
					gp.ui.musicVolume--;
					if (gp.ui.musicVolume < 0) {
						gp.ui.musicVolume = 0;
					}
					gp.setMusicVolume(gp.ui.musicVolume);
				}
				if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
					gp.ui.musicVolume++;
					if (gp.ui.musicVolume > 5) {
						gp.ui.musicVolume = 5;
					}
					gp.setMusicVolume(gp.ui.musicVolume);
				}
			}
			// Enter to select options
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.pauseMenuSelection == 1) {
					// Return to Menu
					gp.gameState = gp.titleState;
					gp.ui.pauseMenuSelection = 0;
				} else if (gp.ui.pauseMenuSelection == 2) {
					// Back to Game
					gp.gameState = gp.playState;
					gp.ui.pauseMenuSelection = 0;
				}
			}
			return; // Don't process other keys in pause state
		}

		if (code == KeyEvent.VK_ESCAPE) {
			if (gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			}
		}

		// Exit dialogue state when WASD is pressed (only if dialogue sequence is complete)
		if (gp.gameState == gp.dialogueState) {
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_A || 
				code == KeyEvent.VK_S || code == KeyEvent.VK_D) {
				// Only allow exit if dialogue sequence is complete
				if (gp.ui.dialogueSequenceComplete && gp.ui.dialogueFinished) {
					gp.gameState = gp.playState;
					gp.ui.resetDialogue(); // Reset typing effect when exiting dialogue
					gp.stopDialogueMusic(); // Stop dialogue music
				} else if (gp.ui.dialogueFinished && gp.ui.currentNPC != null) {
					// Advance to next dialogue in sequence
					gp.ui.currentNPC.speak();
				}
			}
		}

		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_E) {
			ePressed = true;
		}
		if (code == KeyEvent.VK_R) {
			rPressed = true;
		}
		if (code == KeyEvent.VK_T) {
			tPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_E) {
			ePressed = false;
		}
		if (code == KeyEvent.VK_R) {
			rPressed = false;
		}
		if (code == KeyEvent.VK_T) {
			tPressed = false;
		}
	}
}
