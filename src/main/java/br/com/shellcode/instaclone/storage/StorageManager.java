package br.com.shellcode.instaclone.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class StorageManager {
	public static final String DEFAULT_BUCKET = "projetocen2.appspot.com";
	private static boolean SERVE_USING_BLOBSTORE_API = false;
	private static final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
			.initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());
	private static final int BUFFER_SIZE = 2 * 1024 * 1024;

	public static void saveStream(InputStream is, String bucket, String path) {
		GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
		GcsFilename fileName = new GcsFilename(bucket, path);
		GcsOutputChannel outputChannel;
		try {
			outputChannel = gcsService.createOrReplace(fileName, instance);
			copy(is, Channels.newOutputStream(outputChannel));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readStream(OutputStream os, String bucket, String path) {
		readStream(os, bucket, path, null);
	}

	public static void readStream(OutputStream os, String bucket, String path, HttpServletResponse resp) {
		GcsFilename fileName = new GcsFilename(bucket, path);
		try {
			if (resp != null && SERVE_USING_BLOBSTORE_API) {
				BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
				BlobKey blobKey = blobstoreService
						.createGsBlobKey("/gs/" + fileName.getBucketName() + "/" + fileName.getObjectName());
				blobstoreService.serve(blobKey, resp);
			} else {
				GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
				copy(Channels.newInputStream(readChannel), os);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copy(InputStream input, OutputStream output) throws IOException {
		try {
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
		} finally {
			input.close();
			output.close();
		}
	}
}
