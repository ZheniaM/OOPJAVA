package Labyrinth.controller;

import java.util.HashMap;
import Labyrinth.App;
import Labyrinth.Maps;
import Labyrinth.Plane;
import Labyrinth.Player;

public class Session {
	static private HashMap<String, App> hashMap = new HashMap<String, App>();

	static public App getApp(String id) {
		if (hashMap.get(id) != null) {
			return hashMap.get(id);
		}

		Plane[] maps = new Plane[Maps.length];
		for (int i = 0; i < Maps.length; i++) {
			Plane map = new Plane(Maps.maps[i], Maps.starts[i]);
			maps[i] = map;
		}
		Player p = new Player(maps[0].getStart(), 0);
		App app = new App(maps, p);
		hashMap.put(id, app);
		return app;
	}
}
