package quote.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import quote.project.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
