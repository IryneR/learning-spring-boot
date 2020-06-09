package com.snailtraveller.learningspringboot.service;

import com.google.common.collect.ImmutableList;
import com.snailtraveller.learningspringboot.dao.FakeDataDao;
import com.snailtraveller.learningspringboot.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

@Mock
private FakeDataDao fakeDataDao;
private UserService userService;

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);

    }

    @Test
    public void shouldGetAllUsers() {
        UUID yanaUserUid = UUID.randomUUID();
        User yana = new User(yanaUserUid, "Yna", "Montana",
                User.Gender.FEMALE,23,"bu@amail.ru");
        ImmutableList<User> users = new ImmutableList.Builder<User>()
                .add(yana)
                .build();
        given(fakeDataDao.selectAllUsers()).willReturn(users);
        List<User> allUsers =  userService.getAllUsers();

        assertThat(allUsers).hasSize(1);
        User user = allUsers.get(0);

        assertUserFields(user, "bu@amail.ru", "Yna");
    }

    @Test
    public void shouldGetUser() {
        UUID yanaUid = UUID.randomUUID();
        User yana = new User(yanaUid, "Yana", "Montana",
                User.Gender.FEMALE,23,"bu@gmail.com");
        given(fakeDataDao.selectUserByUserUid(yanaUid)).willReturn(Optional.of(yana));

        Optional<User> userOptional = userService.getUser(yanaUid);
        assertThat(userOptional.isPresent()).isTrue();

        User user = userOptional.get();
        assertUserFields(user, "bu@gmail.com", "Yana");
    }

    @Test
    public void shouldUpdateUser() {
        UUID yanaUid = UUID.randomUUID();
        User yana = new User(yanaUid, "Yana", "Montana",
                User.Gender.FEMALE,23,"bu@gmail.com");
        given(fakeDataDao.selectUserByUserUid(yanaUid)).willReturn(Optional.of(yana));
        given(fakeDataDao.updateUser(yana)).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateResult = userService.updateUser(yana);

        verify(fakeDataDao).selectUserByUserUid(yanaUid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();
        assertUserFields(user, "bu@gmail.com", "Yana");

        assertThat(updateResult).isEqualTo(1);
    }

    @Test
    public void shouldRemoveUser() {
        UUID yanaUid = UUID.randomUUID();
        User yana = new User(yanaUid, "Yana", "Montana",
                User.Gender.FEMALE,23,"bu@gmail.com");
        given(fakeDataDao.selectUserByUserUid(yanaUid)).willReturn(Optional.of(yana));
        given(fakeDataDao.deleteUserByUserUid(yanaUid)).willReturn(1);

        int removeResult = userService.removeUser(yanaUid);

        verify(fakeDataDao).selectUserByUserUid(yanaUid);
        verify(fakeDataDao).deleteUserByUserUid(yanaUid);

        assertThat(removeResult).isEqualTo(1);
    }

    @Test
    public void shouldInsertUser() {

        User yana = new User(null, "Yana", "Montana",
                User.Gender.FEMALE,23,"bu@gmail.com");
        given(fakeDataDao.insertUser(any(UUID.class), eq(yana))).willReturn(1);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.insertUser(yana);

        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());
        User user = captor.getValue();

        assertUserFields(user, "bu@gmail.com", "Yana");
        assertThat(insertResult).isEqualTo(1);
    }

    private void assertUserFields(User user, String s, String name) {
        assertThat(user.getAge()).isEqualTo(23);
        assertThat(user.getEmail()).isEqualTo(s);
        assertThat(user.getFirstName()).isEqualTo(name);
        assertThat(user.getLastName()).isEqualTo("Montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getUserUid()).isInstanceOf(UUID.class);
    }
}