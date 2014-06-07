package freela.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import db.DB;
import db.Product;
import db.Productcategory;
import db.User;
import freela.util.Sql.Delete;
import freela.util.Sql.Insert;
import freela.util.Sql.Select;
import freela.util.Sql.Update;
import freela.works.Category;
import static freela.util.Sql.*;

public class TestSql {

	public void back() {

		int ret = new Insert("test").add("test", "blblba").run();
		assertTrue(ret > 0);

		ret = new Insert("test").add("test", "pppp").prepare().run();
		assertTrue(ret > 0);

		ret = new Update("test").add("test", "update").where("id", ret)
				.prepare().run();
		assertTrue(ret > 0);
		ret = new Update("test").add("test", "updatez").where("id", 3)
				.prepare().run();
		assertTrue(ret > 0);

		ret = new Update("test").add("test", "updatev").where("id", 3).run();
		assertTrue(ret > 0);

		ret = new Delete("test").where("id", ret).run();

		List<Map<String, String>> table = ((Select) new Select().from("test")
				.where("id>", "7").prepare()).getTable();

		List<Category> type = new Select().from("category").where("id>", "1")
				.prepare().getType(Category.class);

		new ASCIITable().printTable(type);

		ret = type.size();
		assertTrue(ret > 0);
		Select sql = new Select().from("product").innerJoin("user")
				.on("p.userid", "u.id");
		String string = sql.get();

		List<Map<String, String>> tablex = sql.getTable();

		System.out.println(tablex);

		assertEquals(
				"select * from `product` as p inner join user as u on p.userid=u.id",
				string);
		String id = "141";
		Select pros = new Select().from(DB.product).innerJoin(DB.user)
				.on(Product.userid, User.id).leftJoin(DB.productcategory)
				.on(Product.id, Productcategory.productId)
				.where(Product.id, id);

		System.out.println(pros.get());
		table = pros.getTable();
		System.out.println(table.get(0).size());
		new ASCIITable().printTable(table, true);

		Update update = new Update(DB.product).add(Product.quantity, "xxx")
				.where(Product.id, id).prepare();

		System.out.println(update.get() + "  " + update.params());
		int run = update.run();
		assertTrue(run > 0);

		Delete delete = new Delete(DB.test).where(db.Test.id, "20");
		System.out.println(delete.get());
	}

	@SuppressWarnings({ "deprecation" })
	@Test
	public void test() throws IOException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://localhost:8080/cartee/rest/bosluk");
		
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
		}
	}

	private void bla(String name) {
		// TODO Auto-generated method stub

	}

	private void bla(Product pname) {
		// TODO Auto-generated method stub

	}

	private void bla(Class<?> class1) {
		// TODO Auto-generated method stub

	}
}
