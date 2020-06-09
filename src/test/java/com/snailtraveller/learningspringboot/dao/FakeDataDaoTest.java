package com.snailtraveller.learningspringboot.dao;

import com.snailtraveller.learningspringboot.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeDataDaoTest {

    FakeDataDao fakeDataDao;
    @Before
    public void setUp()  {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    public void shouldSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);

        User user = users.get(0);

        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getEmail()).isEqualTo("joe@gmail.com");
        assertThat(user.getFirstName()).isEqualTo("joe");
        assertThat(user.getLastName()).isEqualTo("jona");
        assertThat(user.getGender()).isEqualTo(User.Gender.MALE);
        assertThat(user.getUserUid()).isNotNull();
    }

    @Test
    public void shouldSelectUserByUserUid() {
        UUID yanaUserUid = UUID.randomUUID();
        User yana = new User(yanaUserUid, "Yna", "Montana",
                User.Gender.FEMALE,23,"bu@amail.ru");
        fakeDataDao.insertUser(yanaUserUid,yana);

        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);
        Optional<User> yanaOptional = fakeDataDao.selectUserByUserUid(yanaUserUid);
        assertThat(yanaOptional.isPresent()).isTrue();
        assertThat(yanaOptional.get()).isEqualToComparingFieldByField(yana);
    }
    @Test
    public void shouldNotSelectUserByRandomUUID() {
        Optional<User> user = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void shouldUpdateUser() {
      UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
      User newJoe = new User (joeUserUid, "Yna", "Montana",
              User.Gender.FEMALE,23,"bu@amail.ru");
      fakeDataDao.updateUser(newJoe);

      Optional<User> user = fakeDataDao.selectUserByUserUid(joeUserUid);
      assertThat(user.isPresent()).isTrue();
      assertThat(user.get()).isEqualToComparingFieldByField(newJoe);

    }

    @Test
    public void deleteUserByUserUid() {
        UUID joeUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        fakeDataDao.deleteUserByUserUid(joeUserUid);

        assertThat(fakeDataDao.selectUserByUserUid(joeUserUid).isPresent()).isFalse();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();
    }

    @Test
    public void insertUser() {
        UUID newUserUid = UUID.randomUUID();
        User user = new User (newUserUid, "Yna", "Montana",
                User.Gender.FEMALE,23,"bu@amail.ru");
        fakeDataDao.insertUser(newUserUid,user);

        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(2);
        assertThat(fakeDataDao.selectUserByUserUid(newUserUid).get()).isEqualToComparingFieldByField(user);
    }
}