package freela.works;

import static spark.Spark.*;

public class Spark {

	public Spark() {

	}

	public static void main(String[] args) {

		get("/hello", (request, response) -> {
			return "Hello World!";
		});

	}

}
