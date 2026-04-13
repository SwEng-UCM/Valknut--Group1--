package me.model;

public interface CharacterSelectionObserver {
	public void onGameStart();
	public void onSelection();
	public void onError(String msg);
	public void onQuit();
	public void onCombat();
}
