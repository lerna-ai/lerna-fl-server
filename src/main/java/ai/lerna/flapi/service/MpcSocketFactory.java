package ai.lerna.flapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MpcSocketFactory {

	@Value("${app.config.mpc.keyStore}")
	String keystorePath;

	@Value("${app.config.mpc.keyStorePassword}")
	String keystorePassword;

	@Value("${app.config.mpc.trustStore}")
	String truststorePath;

	@Value("${app.config.mpc.trustStorePassword}")
	String truststorePassword;

	public SSLSocketFactory getSocketFactory() {

		try {
			SSLContext context = SSLContext.getInstance("TLS");

			KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance("SunX509");
			KeyStore keyStore = KeyStore.getInstance("JKS");
			char[] keyStorePassword = keystorePassword.toCharArray();
			keyStore.load(new FileInputStream(keystorePath), keyStorePassword);
			keyMgrFactory.init(keyStore, keyStorePassword);

			TrustManagerFactory trustStrFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			KeyStore trustStore = KeyStore.getInstance("JKS");
			char[] trustStorePassword = truststorePassword.toCharArray();
			trustStore.load(new FileInputStream(truststorePath), trustStorePassword);
			trustStrFactory.init(trustStore);

			context.init(keyMgrFactory.getKeyManagers(), trustStrFactory.getTrustManagers(), null);
			return context.getSocketFactory();

		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to create a MPC server socket factory: {0}", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
