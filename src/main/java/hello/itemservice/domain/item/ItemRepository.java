package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// 컴포넌트 스캔이 내장이 되어있다.
@Repository
public class ItemRepository {

    // 멀티 쓰레드 환경에서 여러개가 동시에 store에 접근하게 되면 HashMap 쓰면 안되고 ConcurrentHashMap을 써야한다.
    // 싱글톤으로 생성되기 때문에
    private static final Map<Long, Item> store = new HashMap<>(); // static

    // 얘도 Long해서 쓰면 안된다. 값이 꼬일 수 있기 때문에 일단 지금은 간단한 프로젝트이니 쓰자.
    private static Long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }


    // 프로젝트 규모가 커지면 ItemParameterDto만드는게 깔끔하다.
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
