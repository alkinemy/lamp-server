package lamp.server.aladin.core.support.maven;



import com.google.common.collect.Lists;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.utils.StringUtils;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.impl.RepositoryConnectorProvider;
import org.eclipse.aether.internal.impl.EnhancedLocalRepositoryManagerFactory;
import org.eclipse.aether.repository.*;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.connector.ArtifactDownload;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.spi.locator.ServiceLocator;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.repository.AuthenticationBuilder;
import org.eclipse.aether.version.Version;

import java.io.File;
import java.net.URL;

public class ArtifactRepositoryClient {

	private static final String DEFAULT_REPOSITORY_TYPE = "default";

	private ServiceLocator serviceLocator;
	private DefaultRepositorySystemSession session;
	private RemoteRepository remoteRepository;

	public ArtifactRepositoryClient(File repositoryDir, String urlString, String username, String password) {
		try {
			this.serviceLocator = newServiceLocator();

			URL url = new URL(urlString);
			this.remoteRepository = buildRemoteRepository(url, username, password);
			this.session = MavenRepositorySystemUtils.newSession();

			LocalRepositoryManager localRepositoryManager = new EnhancedLocalRepositoryManagerFactory().newInstance(session, new LocalRepository(repositoryDir));
			this.session.setLocalRepositoryManager(localRepositoryManager);
		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.ARTIFACT_REPOSITORY_CONNECT_FAILED, e, urlString);
		}
	}

	public Artifact getArtifact(String groupId, String artifactId, String version) {
		if (StringUtils.isBlank(version)) {
			VersionRangeResult versionRangeResult = getVersionRangeResult(groupId, artifactId);
			Version highestVersion = versionRangeResult.getHighestVersion();
			Exceptions.throwsException(highestVersion == null, LampErrorCode.ARTIFACT_VERSION_RESOLVE_FAILED);
			version = highestVersion.toString();
		}
		return getArtifactResult(groupId, artifactId, version).getArtifact();
	}

	public ArtifactResult getArtifactResult(String groupId, String artifactId, String version) {
		String artifactCoords = groupId + ":" + artifactId + ":" + version;
		try {
			ArtifactRequest request = new ArtifactRequest();
			request.setArtifact(new DefaultArtifact(artifactCoords));
			request.setRepositories(Lists.newArrayList(remoteRepository));

			RepositorySystem repositorySystem = serviceLocator.getService(RepositorySystem.class);
			return repositorySystem.resolveArtifact(session, request);
		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.ARTIFACT_RESOLVE_FAILED, e, artifactCoords);
		}
	}

	public VersionRangeResult getVersionRangeResult(String groupId, String artifactId) {
		try {
			VersionRangeRequest request = new VersionRangeRequest();
			request.setArtifact(new DefaultArtifact(groupId + ":" + artifactId + ":[0,)"));
			request.setRepositories(Lists.newArrayList(remoteRepository));

			RepositorySystem repositorySystem = serviceLocator.getService(RepositorySystem.class);
			return repositorySystem.resolveVersionRange(session, request);
		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.ARTIFACT_VERSION_RESOLVE_FAILED, e, groupId, artifactId);
		}
	}

	public void downloadArtifact(Artifact artifact, File destFile) {
		try {
			ArtifactDownload artifactDownload = new ArtifactDownload(artifact, null, destFile, RepositoryPolicy.CHECKSUM_POLICY_FAIL);

			RepositoryConnectorProvider repositoryConnectorProvider = serviceLocator.getService(RepositoryConnectorProvider.class);
			repositoryConnectorProvider.newRepositoryConnector(session, remoteRepository).get(Lists.newArrayList(artifactDownload), null);
		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.DOWNLOAD_ARTIFACT_FAILED, e);
		}

	}

	protected RemoteRepository buildRemoteRepository(URL url, String username, String password) {
		String repositoryId = "";
		String repositoryUrl = url.toExternalForm();
		RemoteRepository.Builder repositoryBuilder = new RemoteRepository.Builder(repositoryId, DEFAULT_REPOSITORY_TYPE, repositoryUrl);

		if (StringUtils.isNoneBlank(username)) {
			AuthenticationBuilder authenticationBuilder = new AuthenticationBuilder();
			authenticationBuilder.addUsername(username);
			authenticationBuilder.addPassword(password);

			Authentication authentication = authenticationBuilder.build();
			repositoryBuilder.setAuthentication(authentication);
		}

		repositoryBuilder.setSnapshotPolicy(new RepositoryPolicy(true, RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_WARN));

		return repositoryBuilder.build();

	}

	public static DefaultServiceLocator newServiceLocator() {
		DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
		locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
		locator.addService(TransporterFactory.class, FileTransporterFactory.class);
		locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
		locator.setErrorHandler(new DefaultServiceLocator.ErrorHandler() {
			@Override
			public void serviceCreationFailed(Class<?> type, Class<?> impl, Throwable exception) {
				exception.printStackTrace();
			}
		});
		return locator;
	}

}