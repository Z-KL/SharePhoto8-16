package com.ibbhub.albumdemo;

import com.ibbhub.album.AlbumPreviewFragment;

public class MyPreviewFragment extends AlbumPreviewFragment {
    @Override
    public void onPageChanged(int position) {
        super.onPageChanged(position);
        if (position<mData.size()){
            String title = (position+1)+"/"+mData.size();
            ((MyPreviewActivity)getActivity()).setSubtitle(title);
        }
    }
}
