package com.voxtab.ariatech.voxtab.customimages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.pkmmte.view.CircularImageView;
import com.voxtab.ariatech.voxtab.utils.ScalingUtilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class CircularSmartImageView extends CircularImageView {
	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(LOADING_THREADS);

	private SmartImageTask currentTask;

	public CircularSmartImageView(Context context) {
		super(context);
	}

	public CircularSmartImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircularSmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// Helpers to set image by URL
	public void setImageUrl(String url, int width, int height,
			ScalingUtilities.ScalingLogic type) {
		setImage(new WebImage(url, width, height, type));
	}

	public void setImageUrl(String url, int width, int height,
			ScalingUtilities.ScalingLogic type,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url, width, height, type), completeListener);
	}

	public void setImageUrl(String url, int width, int height,
			ScalingUtilities.ScalingLogic type, final Integer fallbackResource) {
		setImage(new WebImage(url, width, height, type), fallbackResource);
	}

	public void setImageUrl(String url, int width, int height,
			ScalingUtilities.ScalingLogic type, final Integer fallbackResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url, width, height, type), fallbackResource,
				completeListener);
	}

	public void setImageUrl(String url, int width, int height,
			ScalingUtilities.ScalingLogic type, final Integer fallbackResource,
			final Integer loadingResource) {
		setImage(new WebImage(url, width, height, type), fallbackResource,
				loadingResource);
	}

	public void setImageUrl(String url, int width, int height,
			ScalingUtilities.ScalingLogic type, final Integer fallbackResource,
			final Integer loadingResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url, width, height, type), fallbackResource,
				loadingResource, completeListener);
	}

	// Helpers to set image by contact address book id
	public void setImageContact(long contactId) {
		setImage(new ContactImage(contactId));
	}

	public void setImageContact(long contactId, final Integer fallbackResource) {
		setImage(new ContactImage(contactId), fallbackResource);
	}

	public void setImageContact(long contactId, final Integer fallbackResource,
			final Integer loadingResource) {
		setImage(new ContactImage(contactId), fallbackResource,
				fallbackResource);
	}

	// Set image using SmartImage object
	public void setImage(final SmartImage image) {
		setImage(image, null, null, null);
	}

	public void setImage(final SmartImage image,
			final SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, null, null, completeListener);
	}

	public void setImage(final SmartImage image, final Integer fallbackResource) {
		setImage(image, fallbackResource, fallbackResource, null);
	}

	public void setImage(final SmartImage image,
			final Integer fallbackResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, fallbackResource, fallbackResource, completeListener);
	}

	public void setImage(final SmartImage image,
			final Integer fallbackResource, final Integer loadingResource) {
		setImage(image, fallbackResource, loadingResource, null);
	}

	public void setPlaceHolderImage(int resid) {
		setImageDrawable(getResources().getDrawable(resid));
	}

	public void setPlaceHolderImage(Drawable drawable) {
		setImageDrawable(drawable);
	}

	public void setImage(final SmartImage image,
			final Integer fallbackResource, final Integer loadingResource,
			final SmartImageTask.OnCompleteListener completeListener) {
		// Set a loading resource
		if (loadingResource != null) {
			setImageResource(loadingResource);
		}

		// Cancel any existing tasks for this image view
		if (currentTask != null) {
			currentTask.cancel();
			currentTask = null;
		}

		// Set up the new task
		currentTask = new SmartImageTask(getContext(), image);
		currentTask
				.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
					@Override
					public void onComplete(Bitmap bitmap) {
						if (bitmap != null) {
							setImageBitmap(bitmap);
						} else {
							// Set fallback resource
							if (fallbackResource != null) {
								setImageResource(fallbackResource);
							}
						}

						if (completeListener != null) {
							completeListener.onComplete(bitmap);
						}
					}
				});

		// Run the task in a threadpool
		threadPool.execute(currentTask);
	}

	public static void cancelAllTasks() {
		threadPool.shutdownNow();
		threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	}
}