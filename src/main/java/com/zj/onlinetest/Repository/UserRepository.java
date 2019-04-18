package com.zj.onlinetest.Repository;

import com.zj.onlinetest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zj
 * @Date: 2019/4/17 10:08
 * @Description:
 */

public interface UserRepository extends JpaRepository<User,String>{

    User findOneByUsername(String username);

    User findOneById(String id);

}
