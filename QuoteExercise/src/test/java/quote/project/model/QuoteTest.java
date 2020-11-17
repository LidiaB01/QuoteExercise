package quote.project.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import quote.project.exceptions.IllegalNameException;
import quote.project.exceptions.NegativeArgumentException;

public class QuoteTest {
	Item item1;
	Item item2;
	Quote quote;
	List<Item> items = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		item1 = new Item(1, "item1");
		item2 = new Item(2, "item2");
		items.add(item1);
		items.add(item2);
	}

	@Test
	public void testQuoteStringIntListOfItemBoolean() {
//		when:
		quote = new Quote("quote1", 30, items, false);
		
//		then:
		assertEquals("quote1", quote.getName());
		assertEquals(30, quote.getPrice());		
	}
		
	@Test
	public void testQuoteStringIntListOfItemBoolean_StringIllegalValue() {
		try {
			quote = new Quote("", 30, items, false);
			fail("name is empty");
		} catch (IllegalNameException e) {
			
		}		
	}
	
	@Test
	public void testQuoteStringIntListOfItemBoolean_IntIllegalValue() {
		try {
			quote = new Quote("quote1", -30, items, false);
			fail("argument is negative");
		} catch (NegativeArgumentException e) {
			
		}		
	}

	@Test
	public void testSetPrice() {
		quote = new Quote("quote1", 30, items, false);
		assertEquals(quote, new Quote("quote1", 30, items, false));
		assertThatThrownBy(() -> quote.setPrice(-30)).isInstanceOf(NegativeArgumentException.class);
	}
	
	@Test
	public void testSetName() {
		quote = new Quote("quote1", 30, items, false);
		assertThatThrownBy(() -> quote.setName("")).isInstanceOf(IllegalNameException.class);
	}
}
