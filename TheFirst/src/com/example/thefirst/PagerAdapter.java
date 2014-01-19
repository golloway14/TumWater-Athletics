/**
 *
 */
package com.example.thefirst;
 
import java.util.List;
 
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
/**
 * The <code>PagerAdapter</code> serves the fragments when paging.
 * I was able to comb through the internet and found someone who
 * had written this handy class which sets up a dummy class
 * that can essentially hold multiple XML files
 * @author Sam Golloway 
 */
public class PagerAdapter extends FragmentPagerAdapter {
 
    private List<Fragment> fragments;
    /**
     * the constuctor for the class!
     * @param fm
     * @param fragments
     */
    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     * this gets the position so we know which page we are on
     */
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
 
    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return this.fragments.size();
    }
}