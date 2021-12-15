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

    @Column(name = "userID")
    private long user_id;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "noMLTasks")
    private int no_ml_tasks;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public long getUserId() {
        return user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public int getNoMLTasks() {
        return no_ml_tasks;
    }

    public void setNoMLTasks(int no_ml_tasks) {
        this.no_ml_tasks = no_ml_tasks;
    }
    
}
