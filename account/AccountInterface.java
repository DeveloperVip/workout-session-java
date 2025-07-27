package account;

import java.util.List;

public interface AccountInterface {
// Tìm kiếm user theo username
    Account findByUsername(String username);
// Tìm kiếm user theo id
    Account findById(int id);
    Account create(Account account);
    boolean update(Account account);
    boolean delete(int id);
}