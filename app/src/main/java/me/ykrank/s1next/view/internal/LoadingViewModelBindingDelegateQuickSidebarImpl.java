package me.ykrank.s1next.view.internal;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ykrank.androidtools.ui.internal.LoadingViewModelBindingDelegate;
import com.github.ykrank.androidtools.ui.vm.LoadingViewModel;

import me.ykrank.s1next.databinding.FragmentBaseWithQuickSideBarBinding;

public final class LoadingViewModelBindingDelegateQuickSidebarImpl
        implements LoadingViewModelBindingDelegate {

    private final FragmentBaseWithQuickSideBarBinding binding;

    public LoadingViewModelBindingDelegateQuickSidebarImpl(
            FragmentBaseWithQuickSideBarBinding binding) {
        this.binding = binding;
    }

    @Override
    public View getRootView() {
        return binding.getRoot();
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return binding.swipeRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }

    @Override
    public void setLoadingViewModel(LoadingViewModel loadingViewModel) {
        binding.setLoadingViewModel(loadingViewModel);
    }
}
