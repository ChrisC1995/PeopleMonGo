package com.campbellapps.christiancampbell.peoplemonv1.Network;

import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christiancampbell on 10/31/16.
 */

public class RestClient {
    private ApiService apiService;

    public RestClient(){
        GsonBuilder builder = new GsonBuilder(); // want to take a string and turn it into data
        builder.setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z"); // sets date format from string
        Gson gson = builder.create();

        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY); // want the interceptor to print to the log so we can see the info

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS) // http client that works as a mini web browser. if it takes longer than 10 seconds stop.
                .addInterceptor(new SessionRequestInterceptor())
                .addInterceptor(log)
                .build();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(PeoplemonApplication.API_BASE_URL) // every url you are calling starts with a base url
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)) // uses GSON above to convert json to classes.
                .build(); //

        apiService = restAdapter.create(ApiService.class); //all of our gets and posts are in ApirService class. Get them there.


    }

    public ApiService getApiService() {
        return apiService;
    }
}




//Exam:
//        manifest, resource, gradle, java,
//
//
//        Steps to make a recyclerview, layout, adapter(getItemCount, viewHolder, bindExpense) ,
//
//
//        Lifecycle to make a view (onFinishInflate-when view is inflated, onDetachedFromWindow when page is finished being used)
//
//
//        Different kinds of calls: ( get-relieves information, post- pusts information onto server, update- updates pieces of an object, delete- deletes info from server, put- replaces an entire object) HTTP METHOD TYPES
//
//
//        Passing into body with post-@POST(Body), passing in path with get-@GET(Path), query with key value
//
//
//        Order Callbacks: when certain code runs at what time. On success, on failure.
//
//
//        Different ways of passing data: intents, eventBus, dependency injection-info transport.
//
//
//        Animation processes, changing alpha values-opacity, flipping values.
//
//
//        Way files work together in network folder. APIservice, restClient(ties all three together), interceptor.
//
//
//        Order that we create with screenplay, create view, layout, stage, android: stage, layout, class.....creation: class, layout, xml
//
//
//        Relativelayout(orderable), linearlayout(goes up to down or left to right in a line) , frame layout(stacks, bottom on top).
//
//
//        Dialog will not fill up entire screen

//        Screnplay is built on top of flow, it couldnt do anything without it. Flow is handling the backstack, I was here now I am here. Screnplay handles the UI portion, animations, dependancy injection using mortar (passing budget list adapter into expense stage).
//        FLow works with the history, screenplay works with the stage.
//        Stage - which layout am I using and connecting to which class..

//        Session Request Intercepter - httpClient(restClient) sends on request to the sever, intercepter checks for token before sending and sticks auth header if token is found. Intercepts a request and inserts itself in.





//    better than findViewById()
//
//
//    butterknife dependency, which comes from the jcenter repo (add it to the app gradle file) is better than findViewById. it uses annotations and is much less RAM-heavy.






//open resources
//different types of files manifests, java, res, gradle scripts. knowing what goes in what.
//knowing what's an anim, an animator, a drawable, values, etc.
//look at RecyclerViews, had to set up adapter, linear layout manager, set the two adapters. on CalendarListView.
//expenseAdapter = new ExpenseAdapter(new ArrayList<Expense>(), context);
//LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//recyclerView.setLayoutManager(linearLayoutManager);
//recyclerView.setAdapter(expenseAdapter);
//life cycle of a View. onFinishInflate, onDetachedFromWindow
// different kinds of calls, http methods, GET, POST, UPDATE, DELETE, PUT
// when each parts of codes run when, CallBacks. onSuccess onFailure.
//different ways of passing data Intents, event Bus, Dependency Injection
//process of animation sets, alpha values, flipping them around.
//understand way files work together in the Networking folder. API Service, Rest Client, etc. Rest Client ties all 3 together.
//what is that process, the order that we create with screenplay View, Layout, Stage. Android reads Stage for Layout, for View.
//butterknife must be binded to this to work. Butterknife.bind(this);
// dependably injection, if a view depends on info, it lets you pass it directly in instead of using intent. Passes data directly from one object to another. Setters are part of that process.



//   Gradle Important dependencies


//compile 'com.android.support:recyclerview-v7:25.0.0'
//        compile 'com.android.support:design:25.0.0'
//        compile 'com.jakewharton:butterknife:7.0.1'
//        compile 'com.davidstemmer.screenplay:screenplay:1.0.2'
//        compile 'com.davidstemmer.screenplay:flow-plugin:0.12'
//        compile 'com.squareup.flow:flow:0.12'
//        compile 'com.squareup.mortar:mortar:0.20'
//        compile 'com.google.code.gson:gson:2.6.2'
//        compile 'com.squareup.retrofit2:retrofit:2.0.2'
//        compile 'com.squareup.retrofit2:converter-gson:2.0.2'
//        compile 'com.squareup.okhttp3:logging-interceptor:3.2.0'
// things we need to add to our gradle before we start.


//repositories that we use in gradle
// {
//        mavenCentral()
//        jcenter()
//        }

