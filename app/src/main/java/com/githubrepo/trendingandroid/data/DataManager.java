package com.githubrepo.trendingandroid.data;

import android.content.Context;

import com.githubrepo.trendingandroid.data.local.DatabaseHelper;
import com.githubrepo.trendingandroid.data.local.LanguageHelper;
import com.githubrepo.trendingandroid.data.local.PreferencesHelper;
import com.githubrepo.trendingandroid.data.model.AccessTokenResp;
import com.githubrepo.trendingandroid.data.model.Repo;
import com.githubrepo.trendingandroid.data.model.RepoContent;
import com.githubrepo.trendingandroid.data.model.User;
import com.githubrepo.trendingandroid.data.model.WrapList;
import com.githubrepo.trendingandroid.data.remote.GithubApi;
import com.githubrepo.trendingandroid.data.remote.SimpleApi;
import com.githubrepo.trendingandroid.injection.ApplicationContext;
import com.githubrepo.trendingandroid.rxbus.RxBus;
import com.githubrepo.trendingandroid.util.EncodingUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

@Singleton
public class DataManager {
    final GithubApi githubApi;
    final SimpleApi simpleApi;
    final RxBus rxBus;
    final PreferencesHelper preferencesHelper;
    final DatabaseHelper databaseHelper;
    final LanguageHelper languageHelper;
    final Context context;

    @Inject
    public DataManager(@ApplicationContext Context context, final GithubApi githubApi, final SimpleApi simpleApi, final RxBus rxBus,
                       final PreferencesHelper preferencesHelper, final DatabaseHelper databaseHelper, final LanguageHelper languageHelper) {
        this.githubApi = githubApi;
        this.simpleApi = simpleApi;
        this.rxBus = rxBus;
        this.preferencesHelper = preferencesHelper;
        this.databaseHelper = databaseHelper;
        this.languageHelper = languageHelper;
        this.context = context;
    }

    public Observable<User> getAccessToken(String code) {
        Observable<User> userObservable = githubApi.getOAuthToken(GithubApi.CLIENT_ID, GithubApi.CLIENT_SECRET, code).
                flatMap(new Func1<AccessTokenResp, Observable<User>>() {
                    @Override
                    public Observable<User> call(AccessTokenResp accessTokenResp) {
                        preferencesHelper.putAccessToken(accessTokenResp.getAccessToken());
                        return githubApi.getUserInfo();
                    }
                }).doOnNext(new Action1<User>() {
            @Override
            public void call(User user) {
                Timber.d("### save user %s", user.getLogin());
                handleSaveUser(user);
            }
        });
        return userObservable;
    }

    public Observable<List<Repo>> getTrending(final String language, final String since) {
        return githubApi.getTrendingRepo(language, since);
    }

    public Observable<WrapList<Repo>> getRepos(final String query, final int page) {
        return githubApi.getRepos(query, page);
    }

    public Observable<Response<Void>> starRepo(final String owner, final String repo) {
        return githubApi.starRepo(owner, repo);
    }


    public Observable<WrapList<User>> getUsers(final String query, final int page) {
        return githubApi.getUsers(query, page);
    }

    public Observable<String> getReadme(final String owner, final String repo, final String cssFile) {
        return githubApi.getReadme(owner, repo).flatMap(new Func1<RepoContent, Observable<String>>() {
            @Override
            public Observable<String> call(RepoContent repoContent) {
                String markdown = null;
                try {
                    markdown = new String(EncodingUtil.fromBase64(repoContent.getContent()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Timber.e(e, "### UnsupportedEncodingException");
                }
                return simpleApi.markdown(markdown);
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.just(loadMarkdownToHtml(s, cssFile));
            }
        });
    }


    private void handleSaveUser(final User user) {
        preferencesHelper.putUserLogin(user.getLogin());
        preferencesHelper.putUserEmail(user.getEmail());
        preferencesHelper.putUserAvatar(user.getAvatar_url());
    }

    public RxBus getRxBus() {
        return rxBus;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public Context getContext() {
        return context;
    }

    public LanguageHelper getLanguageHelper() {
        return languageHelper;
    }

    public void clearWebCache() {
        preferencesHelper.clear();

        if (null != context) {
            File appDir = new File(context.getCacheDir().getParent());
            if (appDir.exists()) {
                for (String dir : appDir.list()) {
                    if (dir.toLowerCase().contains("webview")) {
                        deleteDir(new File(appDir, dir));
                    }
                }
            }
        }
    }

    /**
     * may use helper
     *
     * @param dir
     * @return
     */
    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir != null && dir.delete();
    }

    private String loadMarkdownToHtml(final String txt, final String cssFile) {
        String html;
        if (null != cssFile) {
            html = "<link rel='stylesheet' type='text/css' href='" + cssFile + "' />" + txt;
            return html;
        }

        return null;
    }
}
