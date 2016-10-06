package com.example.trackermvc.stops.repository;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.trackermvc.R;
import com.example.trackermvc.stops.model.MarkerDetails;
import com.example.trackermvc.stops.model.StopData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collection;

import javax.inject.Inject;

import rx.Observable;

public class MarkersTransformer {

    private final Resources mResources;

    @Inject
    public MarkersTransformer(Resources resources) {
        mResources = resources;
    }

    public Observable<MarkerDetails> transform(Collection<StopData> stops) {
        return Observable.just(stops)
                .flatMap(stopDatas -> Observable.from(stopDatas)
                        .map(stopData -> {
                                    Bitmap mDotMarkerBitmap = Bitmap.createBitmap(24, 24, Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(mDotMarkerBitmap);
                                    Drawable shape = mResources.getDrawable(R.drawable.bus_stop_marker);
                                    shape.setBounds(0, 0, mDotMarkerBitmap.getWidth(), mDotMarkerBitmap.getHeight());
                                    shape.draw(canvas);
                                    return new MarkerDetails(stopData, new MarkerOptions()
                                            .draggable(false)
                                            .position(new LatLng(stopData.getLat(), stopData.getLon()))
                                            .icon(BitmapDescriptorFactory.fromBitmap(mDotMarkerBitmap)));
                                }
                        ));
    }
}
