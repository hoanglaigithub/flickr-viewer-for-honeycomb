/**
 * 
 */
package com.charles.actions;

import com.aetrion.flickr.photos.PhotoList;
import com.charles.FlickrViewerApplication;
import com.charles.R;
import com.charles.dataprovider.PeoplePublicPhotosDataProvider;
import com.charles.event.IPhotoListReadyListener;
import com.charles.task.AsyncPhotoListTask;
import com.charles.ui.PhotoListFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Represents the action to show all the public photos of a given user.
 * 
 * @author charles
 */
public class ShowPeoplePhotosAction extends ActivityAwareAction {

	private String mUserId;
	private PeoplePublicPhotosDataProvider mDataProvider;
	private IPhotoListReadyListener mPhotosReadyListener = new IPhotoListReadyListener() {

		@Override
		public void onPhotoListReady(PhotoList list) {
			PhotoListFragment fragment = new PhotoListFragment(list,
					mDataProvider);
			FragmentManager fm = mActivity.getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.replace(R.id.main_area, fragment);
			ft.addToBackStack(null);
			ft.commitAllowingStateLoss();
		}

	};

	/**
	 * @param resId
	 */
	public ShowPeoplePhotosAction(Activity context, String userId) {
		super(context);
		this.mUserId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.charles.actions.IAction#execute()
	 */
	@Override
	public void execute() {
		FlickrViewerApplication app = (FlickrViewerApplication) mActivity
				.getApplication();

		if (mUserId != null && mUserId.equals(app.getUserId())) {
			mActivity.onBackPressed();
			return;
		}

		String token = app.getFlickrToken();
		if (mUserId == null) {
			mUserId = app.getUserId();
		}
		mDataProvider = new PeoplePublicPhotosDataProvider(mUserId, token);
		AsyncPhotoListTask task = new AsyncPhotoListTask(mActivity,
				mDataProvider, mPhotosReadyListener);
		task.execute();
	}

}