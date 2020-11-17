package quote.project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.SQLDelete;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import quote.project.exceptions.IllegalNameException;
import quote.project.exceptions.NegativeArgumentException;

@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of = {"name"})
@Entity
@SQLDelete(sql = "UPDATE quote SET state = 't' WHERE name = ?")
public class Quote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	@Id
	private String name;
	private int price;
	@ManyToMany
	@Singular
	private List<Item> items;
	private boolean state;
	
	public Quote(String name, int price, List<Item> items, boolean state) {	
		validateName(name);
		validatePrice(price);
		this.name = name;
		this.price = price;
		this.state = false;
		this.items = items;
	}
	
	public Quote(String name, int price, boolean state) {	
		validateName(name);
		validatePrice(price);
		this.name = name;
		this.price = price;
		this.state = false;
		this.items = new ArrayList<>();
	}
	
	private void validateName(String name) {
		if (name.isEmpty()) {
			throw new IllegalNameException();
		}
	}
	
	private void validatePrice(int price) {
		if (price < 0) {			
			throw new NegativeArgumentException();
		}
	}

	public void setName(String name) {
		validateName(name);
		this.name = name;
	}

	public void setPrice(int price) {
		validatePrice(price);
		this.price = price;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
}
