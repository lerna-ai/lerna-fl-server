package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.LernaApplication;
import ai.lerna.flapi.api.dto.WebDashboard;
import ai.lerna.flapi.entity.LernaPrediction;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface WebManager {

	List<LernaPrediction> getInference(String token) throws Exception;

	List<LernaApplication> getApplications(long userId, boolean includeML);

	List<Map<String, BigInteger>> getActiveDevices(long userId);

	WebDashboard getDashboardData(long userId);
}
