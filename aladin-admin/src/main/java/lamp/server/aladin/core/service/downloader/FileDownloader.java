package lamp.server.aladin.core.service.downloader;

import lamp.server.aladin.core.DownloadableFile;

import java.io.File;

public interface FileDownloader {

	File download(DownloadableFile downloadableFile);

}
