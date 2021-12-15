package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.converter.LernaFLConverter;
import ai.lerna.flapi.entity.converter.LernaMLConverter;
import ai.lerna.flapi.entity.converter.LernaPrivacyConverter;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lerna_ml")
public class LernaML {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    
    @Column(name = "appID")
    private long app_id;
    
    @Column(name = "privacy")
    @Convert(converter = LernaPrivacyConverter.class)
    private LernaPrivacyParameters privacy = new LernaPrivacyParameters();

    @Column(name = "ML")
    @Convert(converter = LernaMLConverter.class)
    private LernaMLParameters ml = new LernaMLParameters();

    @Column(name = "FL")
    @Convert(converter = LernaFLConverter.class)
    private LernaFLParameters fl = new LernaFLParameters();

    @Column(name = "accuracy")
    private BigDecimal accuracy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public long getAppId() {
        return app_id;
    }

    public void setAppId(long task_id) {
        this.app_id = task_id;
    }

    public LernaPrivacyParameters getPrivacy() {
        return privacy;
    }

    public void setPrivacy(LernaPrivacyParameters privacy) {
        this.privacy = privacy;
    }

    public LernaMLParameters getML() {
        return ml;
    }

    public void setPrivacy(LernaMLParameters ml) {
        this.ml = ml;
    }

    public LernaFLParameters getFL() {
        return fl;
    }

    public void setFL(LernaFLParameters fl) {
        this.fl = fl;
    }

    public BigDecimal getAccuracy() {
        return accuracy;
    }

    public void setEps(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }
}
