package com.sanshengshui.server.dao.sql.user;

import com.sanshengshui.server.common.data.security.Authority;
import com.sanshengshui.server.dao.model.sql.UserEntity;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author james mu
 * @date 19-2-20 下午2:53
 * @description
 */
@SqlDao
public interface UserRepository extends CrudRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.tenantId = :tenantId " +
            "AND u.customerId = :customerId AND u.authority = :authority " +
            "AND LOWER(u.searchText) LIKE LOWER(CONCAT(:searchText, '%'))" +
            "AND u.id > :idOffset ORDER BY u.id")
    List<UserEntity> findUsersByAuthority(@Param("tenantId") String tenantId,
                                          @Param("customerId") String customerId,
                                          @Param("idOffset") String idOffset,
                                          @Param("searchText") String searchText,
                                          @Param("authority") Authority authority,
                                          Pageable pageable);
}
