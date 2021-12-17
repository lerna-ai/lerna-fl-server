package ai.lerna.flapi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lerna_app")
public class LernaApp {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "token")
	private String token;

	@Column(name = "no_ml_tasks")
	private int numberMlTasks;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getNumberMlTasks() {
		return numberMlTasks;
	}

	public void setNumberMlTasks(int numberMlTasks) {
		this.numberMlTasks = numberMlTasks;
	}
}
