package com.jobo.jetpack_mvvm_demo.ui.transform;

import static com.jobo.jetpack_mvvm_demo.ui.transform.TransformerStyle.*;

import androidx.viewpager2.widget.ViewPager2;

import com.zhpan.bannerview.transform.ScaleInTransformer;

public class PageTransformerFactory {

  public static ViewPager2.PageTransformer createPageTransformer(int transformerStyle) {
    ViewPager2.PageTransformer transformer = null;
    switch (transformerStyle) {
      case DEPTH:
        transformer = new DepthPageTransformer();
        break;
      case ROTATE:
        transformer = new RotateTransformer();
        break;
      case ROTATE_UP:
        transformer = new RotateUpTransformer();
        break;
      case DEPTH_SCALE:
        transformer = new DepthScaleTransformer();
        break;
      case ACCORDION:
        transformer = new AccordionTransformer();
        break;
      case SCALE_IN:
        transformer = new ScaleInTransformer(ScaleInTransformer.DEFAULT_MIN_SCALE);
        break;
    }
    return transformer;
  }
}
