package com.snailtraveller.learningspringboot;

import com.snailtraveller.learningspringboot.clientproxy.UserResourceV1;
import com.snailtraveller.learningspringboot.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LearningSpringBootApplicationTests {

	@Autowired
	private UserResourceV1 userResourceV1;

/*	@Test
	public void itShouldFetchAllUsers() {
		List<User> users =	userResourceV1.fetchUsers(null);
		assertThat(users).hasSize(1);

		User joe = new User(null, "joe","jona", User.Gender.MALE,22,"joe@gmail.com");
		assertThat(users.get(0)).isEqualToIgnoringGivenFields(joe,"userUid");
		assertThat(users.get(0).getUserUid()).isInstanceOf(UUID.class);
		assertThat(users.get(0).getUserUid()).isNotNull();
	}*/
@Test
	public void shouldInsertUser() throws Exception{
	//Given
	UUID userUid = UUID.randomUUID();
	User user = new User(userUid, "joe","jona",
			User.Gender.MALE,22,"joe@gmail.com");
	//When
	userResourceV1.insertNewUser(user);
	User joe = userResourceV1.fetchUser(userUid);
	//Then
	assertThat(joe).isEqualToComparingFieldByField(user);
}

	@Test
	public void shouldDeleteUser() throws Exception{
		//Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "joe","jona",
				User.Gender.MALE,22,"joe@gmail.com");
		//When
		userResourceV1.insertNewUser(user);
		User joe = userResourceV1.fetchUser(userUid);
		//Then
		assertThat(joe).isEqualToComparingFieldByField(user);
		//When
		userResourceV1.deleteUser(userUid);
		//Then
		assertThatThrownBy(() -> userResourceV1.fetchUser(userUid))
				.isInstanceOf(NotFoundException.class);
	}

	@Test
	public void shouldUpdateUser() throws Exception{
		//Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "joe","jona",
				User.Gender.MALE,22,"joe@gmail.com");
		//When
		userResourceV1.insertNewUser(user);

		User updateUser = new User(userUid, "Alex","jona",
				User.Gender.MALE,55,"joe@gmail.com");
		userResourceV1.updateUser(updateUser);

		//Then
		User fetchedUser = userResourceV1.fetchUser(userUid);
		assertThat(fetchedUser).isEqualToComparingFieldByField(updateUser);
	}

	@Test
	public void shouldFetchUserByGender() throws Exception{
		//Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "joe","jona",
				User.Gender.MALE,22,"joe@gmail.com");
		//When
		userResourceV1.insertNewUser(user);
		List<User> females = userResourceV1.fetchUsers(User.Gender.FEMALE.name());

		assertThat(females).extracting("userUid").doesNotContain(user.getUserUid());
		assertThat(females).extracting("firstName").doesNotContain(user.getLastName());
		assertThat(females).extracting("lastName").doesNotContain(user.getFirstName());
		assertThat(females).extracting("gender").doesNotContain(user.getGender());
		assertThat(females).extracting("age").doesNotContain(user.getAge());
		assertThat(females).extracting("email").doesNotContain(user.getEmail());

		List<User> males = userResourceV1.fetchUsers(User.Gender.MALE.name());

		assertThat(females).extracting("userUid").contains(user.getUserUid());
		assertThat(females).extracting("firstName").contains(user.getLastName());
		assertThat(females).extracting("lastName").contains(user.getFirstName());
		assertThat(females).extracting("gender").contains(user.getGender());
		assertThat(females).extracting("age").contains(user.getAge());
		assertThat(females).extracting("email").contains(user.getEmail());
	}
}
