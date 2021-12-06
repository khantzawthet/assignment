package test;

public class City {

	private String name;
	private int id;
	private boolean visited;

	public City(String name, int id, boolean visited) {
		this.name = name;
		this.id = id;
		this.visited = visited;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", id=" + id + ", visited=" + visited + "]";
	}
}