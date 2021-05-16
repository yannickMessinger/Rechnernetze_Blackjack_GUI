package app;

import javafx.scene.layout.Pane;

public abstract  class ViewController {
	protected Pane rootView;

	public ViewController() {

	}

	public abstract void initialize();

	public Pane getRootView() {
		// TODO Auto-generated method stub
		return rootView;
	}

}