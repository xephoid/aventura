package com.modiopera.aventura.controller;

import com.modiopera.aventura.view.text.TextBasedView;

public class MainController extends AbstractController {
	public static void main(String[] args) {
		MainController main = new MainController();
		
		main.setView(new TextBasedView());
		main.start();
	}
}
